package ca.on.oicr.gsi.provenance;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Stopwatch;

import ca.on.oicr.gsi.provenance.model.AnalysisProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenance;
import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;

/**
 *
 * @author mlaszloffy
 */
public class MultiThreadedDefaultProvenanceClient extends DefaultProvenanceClient {

    private ExecutorService es;
    private final Logger log = LogManager.getLogger(MultiThreadedDefaultProvenanceClient.class);

    private <T, TP> List<Future<?>> getByProvider(final Map<String, TP> providers,
            final Map<FileProvenanceFilter, Set<String>> filters, ExecutorService executorService, Map<String, Map<String, T>> output,
            Function<TP, Collection<T>> getProvenance,
            BiFunction<TP, Map<FileProvenanceFilter, Set<String>>, Collection<T>> getProvenanceWithFilter,
            Function<T, String> getId) throws InterruptedException {
        List<Future<?>> futures = new ArrayList<>();
        for (Map.Entry<String, TP> e : providers.entrySet()) {
            final String provider = e.getKey();
            final TP pp = e.getValue();

            futures.add(executorService.submit(() -> {
                Stopwatch sw = Stopwatch.createStarted();
                log.info("Provider = [{}] started {}", provider, getProvenance.toString());
                Collection<T> ps;
                if (filters == null || filters.isEmpty()) {
                    ps = getProvenance.apply(pp);
                } else {
                    ps = getProvenanceWithFilter.apply(pp, filters);
                }
                log.info("Provider = [{}] completed get provenance in {}", provider, sw.toString());

                Map<String, T> psById = new HashMap<>();
                for (T p : ps) {
                    if (psById.put(getId.apply(p), p) != null) {
                        throw new RuntimeException("Duplicate provenance ID = [" + getId.apply(p)
                                + "] from provider = [" + provider + "]");
                    }
                }

                output.put(provider, psById);
            }));
        }
        return futures;
    }

    private List<Future<?>> getSampleProvenanceFutureByProvider(final Map<FileProvenanceFilter, Set<String>> filters,
            ExecutorService executorService, Map<String, Map<String, SampleProvenance>> output) throws InterruptedException {
        return getByProvider(sampleProvenanceProviders, filters, executorService, output,
                SampleProvenanceProvider::getSampleProvenance, SampleProvenanceProvider::getSampleProvenance,
                SampleProvenance::getProvenanceId);
    }

    private List<Future<?>> getLaneProvenanceFutureByProvider(final Map<FileProvenanceFilter, Set<String>> filters,
            ExecutorService executorService, Map<String, Map<String, LaneProvenance>> output) throws InterruptedException {
        return getByProvider(laneProvenanceProviders, filters, executorService, output,
                LaneProvenanceProvider::getLaneProvenance, LaneProvenanceProvider::getLaneProvenance,
                LaneProvenance::getProvenanceId);
    }

    private List<Future<?>> getAnalysisProvenanceFutureByProvider(final Map<FileProvenanceFilter, Set<String>> filters,
            ExecutorService executorService, Map<String, Collection<AnalysisProvenance>> output) throws InterruptedException {
        List<Future<?>> futures = new ArrayList<>();
        for (Map.Entry<String, AnalysisProvenanceProvider> e : analysisProvenanceProviders.entrySet()) {
            final String provider = e.getKey();
            final AnalysisProvenanceProvider app = e.getValue();

            futures.add(executorService.submit(() -> {
                Stopwatch sw = Stopwatch.createStarted();
                log.info("Provider = [{}] started getAnalysisProvenance()", provider);
                Collection<AnalysisProvenance> aps;
                if (filters == null || filters.isEmpty()) {
                    aps = app.getAnalysisProvenance();
                } else {
                    aps = app.getAnalysisProvenance(filters);
                }
                log.info("Provider = [{}] completed getAnalysisProvenance() in {}", provider, sw.toString());

                output.put(provider, aps);
            }));
        }
        return futures;
    }

    @Override
    public Collection<FileProvenance> getFileProvenance() {
        return getFileProvenance(Collections.<FileProvenanceFilter, Set<String>>emptyMap());
    }

    @Override
    public Collection<FileProvenance> getFileProvenance(Map<FileProvenanceFilter, Set<String>> filters) {
        es = Executors.newFixedThreadPool(8);
        try {
            // get all sample and lane provenance and filter after joining with AP
            // if this is not done, there could be AP records that pass filters and do not
            // have an associated SP or LP
            // for example: AP record linked to SP(A) and SP(B), filtering on A would result
            // in FP(AP+SP(B))=ERROR
            List<Future<?>> futures = new ArrayList<>();
            Map<String, Map<String, SampleProvenance>> spsByProvider = new HashMap<>();
            futures.addAll(getSampleProvenanceFutureByProvider(Collections.<FileProvenanceFilter, Set<String>>emptyMap(), es,
                    spsByProvider));
            Map<String, Map<String, LaneProvenance>> lpsByProvider = new HashMap<>();
            futures.addAll(getLaneProvenanceFutureByProvider(Collections.<FileProvenanceFilter, Set<String>>emptyMap(), es,
                    lpsByProvider));
            Map<String, Collection<AnalysisProvenance>> apsByProvider = new HashMap<>();
            futures.addAll(getAnalysisProvenanceFutureByProvider(filters, es, apsByProvider));

            //need to call get() on each future to check for task execution exceptions
            futures.parallelStream().forEach(f -> {
                try {
                    f.get();
                } catch (InterruptedException | ExecutionException ex) {
                    throw new RuntimeException(ex);
                }
            });

            es.shutdown();
            es.awaitTermination(1, TimeUnit.HOURS);

            return applyFileProvenanceFilters(getFileProvenance(spsByProvider, lpsByProvider, apsByProvider), filters);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            es.shutdownNow();
        }
    }

}
