package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.AnalysisProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenance;
import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import com.google.common.base.Stopwatch;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author mlaszloffy
 */
public class MultiThreadedDefaultProvenanceClient extends DefaultProvenanceClient {

    private ExecutorService es;
    private CompletionService cs;
    private final Logger log = LogManager.getLogger(MultiThreadedDefaultProvenanceClient.class);

    private Future<Map<String, Map<String, SampleProvenance>>> getSampleProvenanceFutureByProvider(final Map<String, Set<String>> filters) throws InterruptedException {
        final CompletionService<Map<String, Map<String, SampleProvenance>>> compService = new ExecutorCompletionService(es);

        final List<Future<Map<String, Map<String, SampleProvenance>>>> tasks = new ArrayList<>();
        for (Map.Entry<String, SampleProvenanceProvider> e : sampleProvenanceProviders.entrySet()) {
            final String provider = e.getKey();
            final SampleProvenanceProvider spp = e.getValue();

            Callable<Map<String, Map<String, SampleProvenance>>> c = new Callable<Map<String, Map<String, SampleProvenance>>>() {
                @Override
                public Map<String, Map<String, SampleProvenance>> call() {
                    Stopwatch sw = Stopwatch.createStarted();
                    log.info("Provider = [{}] start getSampleProvenance()", provider);
                    Collection<SampleProvenance> sps;
                    if (filters == null || filters.isEmpty()) {
                        sps = spp.getSampleProvenance();
                    } else {
                        sps = spp.getSampleProvenance(filters);
                    }
                    log.info("Provider = [{}] completed getSampleProvenance() in {}", provider, sw.toString());

                    Map<String, SampleProvenance> spsById = new HashMap<>();
                    for (SampleProvenance sp : sps) {
                        if (spsById.put(sp.getSampleProvenanceId(), sp) != null) {
                            throw new RuntimeException("Duplicate sample provenance ID = [" + sp.getSampleProvenanceId() + "] from provider = [" + provider + "]");
                        }
                    }

                    Map<String, Map<String, SampleProvenance>> m = new HashMap<>();
                    m.put(provider, spsById);
                    return m;
                }
            };
            tasks.add(compService.submit(c));
        }

        Callable<Map<String, Map<String, SampleProvenance>>> collect = new Callable<Map<String, Map<String, SampleProvenance>>>() {
            @Override
            public Map<String, Map<String, SampleProvenance>> call() throws InterruptedException, ExecutionException {
                Map<String, Map<String, SampleProvenance>> spsByProvider = new HashMap<>();

                while (tasks.size() > 0) {
                    Future<Map<String, Map<String, SampleProvenance>>> completedTask = compService.take();
                    tasks.remove(completedTask);
                    Map<String, Map<String, SampleProvenance>> m = completedTask.get();
                    spsByProvider.putAll(m);
                }

                return spsByProvider;
            }
        };

        return cs.submit(collect);
    }

    private Future<Map<String, Map<String, LaneProvenance>>> getLaneProvenanceFutureByProvider(final Map<String, Set<String>> filters) throws InterruptedException {
        final CompletionService<Map<String, Map<String, LaneProvenance>>> compService = new ExecutorCompletionService(es);

        final List<Future<Map<String, Map<String, LaneProvenance>>>> tasks = new ArrayList<>();
        for (Map.Entry<String, LaneProvenanceProvider> e : laneProvenanceProviders.entrySet()) {
            final String provider = e.getKey();
            final LaneProvenanceProvider lpp = e.getValue();

            Callable<Map<String, Map<String, LaneProvenance>>> c = new Callable<Map<String, Map<String, LaneProvenance>>>() {
                @Override
                public Map<String, Map<String, LaneProvenance>> call() throws Exception {
                    Stopwatch sw = Stopwatch.createStarted();
                    log.info("Provider = [{}] start getLaneProvenance()", provider);
                    Collection<LaneProvenance> lps;
                    if (filters == null || filters.isEmpty()) {
                        lps = lpp.getLaneProvenance();
                    } else {
                        lps = lpp.getLaneProvenance(filters);
                    }
                    log.info("Provider = [{}] completed getLaneProvenance() in {}", provider, sw.toString());

                    Map<String, LaneProvenance> lpsById = new HashMap<>();
                    for (LaneProvenance lp : lps) {
                        if (lpsById.put(lp.getLaneProvenanceId(), lp) != null) {
                            throw new RuntimeException("Duplicate lane provenance ID = [" + lp.getLaneProvenanceId() + "] from provider = [" + provider + "]");
                        }
                    }

                    Map<String, Map<String, LaneProvenance>> m = new HashMap<>();
                    m.put(provider, lpsById);
                    return m;
                }
            };
            tasks.add(compService.submit(c));
        }

        Callable<Map<String, Map<String, LaneProvenance>>> collect = new Callable<Map<String, Map<String, LaneProvenance>>>() {
            @Override
            public Map<String, Map<String, LaneProvenance>> call() throws InterruptedException, ExecutionException {
                Map<String, Map<String, LaneProvenance>> lpsByProvider = new HashMap<>();

                while (tasks.size() > 0) {
                    Future<Map<String, Map<String, LaneProvenance>>> completedTask = compService.take();
                    tasks.remove(completedTask);
                    Map<String, Map<String, LaneProvenance>> m = completedTask.get();
                    lpsByProvider.putAll(m);
                }

                return lpsByProvider;
            }
        };

        return cs.submit(collect);
    }

    private Future<Map<String, Collection<AnalysisProvenance>>> getAnalysisProvenanceFutureByProvider(final Map<String, Set<String>> filters) throws InterruptedException {
        final CompletionService<Map<String, Collection<AnalysisProvenance>>> compService = new ExecutorCompletionService(es);

        final List<Future<Map<String, Collection<AnalysisProvenance>>>> tasks = new ArrayList<>();
        for (Map.Entry<String, AnalysisProvenanceProvider> e : analysisProvenanceProviders.entrySet()) {
            final String provider = e.getKey();
            final AnalysisProvenanceProvider app = e.getValue();

            Callable<Map<String, Collection<AnalysisProvenance>>> c = new Callable<Map<String, Collection<AnalysisProvenance>>>() {
                @Override
                public Map<String, Collection<AnalysisProvenance>> call() {
                    Stopwatch sw = Stopwatch.createStarted();
                    log.info("Provider = [{}] start getAnalysisProvenance()", provider);
                    Collection<AnalysisProvenance> aps;
                    if (filters == null || filters.isEmpty()) {
                        aps = app.getAnalysisProvenance();
                    } else {
                        aps = app.getAnalysisProvenance(filters);
                    }
                    log.info("Provider = [{}] completed getAnalysisProvenance() in {}", provider, sw.toString());

                    Map<String, Collection<AnalysisProvenance>> m = new HashMap<>();
                    m.put(provider, aps);
                    return m;
                }
            };
            tasks.add(compService.submit(c));
        }

        Callable<Map<String, Collection<AnalysisProvenance>>> collect = new Callable<Map<String, Collection<AnalysisProvenance>>>() {
            @Override
            public Map<String, Collection<AnalysisProvenance>> call() throws InterruptedException, ExecutionException {
                Map<String, Collection<AnalysisProvenance>> apsByProvider = new HashMap<>();

                while (tasks.size() > 0) {
                    Future<Map<String, Collection<AnalysisProvenance>>> completedTask = compService.take();
                    tasks.remove(completedTask);
                    Map<String, Collection<AnalysisProvenance>> m = completedTask.get();
                    apsByProvider.putAll(m);
                }

                return apsByProvider;
            }

        };

        return cs.submit(collect);
    }

    @Override
    public Collection<FileProvenance> getFileProvenance() {
        es = Executors.newFixedThreadPool(8);
        cs = new ExecutorCompletionService(es);
        try {
            Future<Map<String, Map<String, SampleProvenance>>> spsByProvider = getSampleProvenanceFutureByProvider(Collections.EMPTY_MAP);
            Future<Map<String, Map<String, LaneProvenance>>> lpsByProvider = getLaneProvenanceFutureByProvider(Collections.EMPTY_MAP);
            Future<Map<String, Collection<AnalysisProvenance>>> apsByProvider = getAnalysisProvenanceFutureByProvider(Collections.EMPTY_MAP);
            int aggregationTasksRemaining = 3;

            while (aggregationTasksRemaining > 0) {
                Future f = cs.take();
                aggregationTasksRemaining--;
                f.get();
            }

            return getFileProvenance(spsByProvider.get(), lpsByProvider.get(), apsByProvider.get());
        } catch (ExecutionException | InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            es.shutdownNow();
        }
    }

}
