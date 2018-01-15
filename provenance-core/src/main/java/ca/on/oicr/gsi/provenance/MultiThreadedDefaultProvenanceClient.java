package ca.on.oicr.gsi.provenance;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
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

/**
 *
 * @author mlaszloffy
 */
public class MultiThreadedDefaultProvenanceClient extends DefaultProvenanceClient {

	private ExecutorService es;
	private final Logger log = LogManager.getLogger(MultiThreadedDefaultProvenanceClient.class);

	private <T, TP> void getByProvider(final Map<String, TP> providers,
			final Map<FileProvenanceFilter, Set<String>> filters, Executor executor, Map<String, Map<String, T>> output,
			Function<TP, Collection<T>> getProvenance,
			BiFunction<TP, Map<FileProvenanceFilter, Set<String>>, Collection<T>> getProvenanceWithFilter,
			Function<T, String> getId) throws InterruptedException {

		for (Map.Entry<String, TP> e : providers.entrySet()) {
			final String provider = e.getKey();
			final TP pp = e.getValue();

			executor.execute(() -> {
				Stopwatch sw = Stopwatch.createStarted();
				log.info("Provider = [{}] started get provenance", provider);
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
			});
		}
	}

	private void getSampleProvenanceFutureByProvider(final Map<FileProvenanceFilter, Set<String>> filters,
			Executor executor, Map<String, Map<String, SampleProvenance>> output) throws InterruptedException {
		getByProvider(sampleProvenanceProviders, filters, executor, output,
				SampleProvenanceProvider::getSampleProvenance, SampleProvenanceProvider::getSampleProvenance,
				SampleProvenance::getProvenanceId);
	}

	private void getLaneProvenanceFutureByProvider(final Map<FileProvenanceFilter, Set<String>> filters,
			Executor executor, Map<String, Map<String, LaneProvenance>> output) throws InterruptedException {
		getByProvider(laneProvenanceProviders, filters, executor, output,
				LaneProvenanceProvider::getLaneProvenance, LaneProvenanceProvider::getLaneProvenance,
				LaneProvenance::getProvenanceId);
	}

	private void getAnalysisProvenanceFutureByProvider(final Map<FileProvenanceFilter, Set<String>> filters,
			Executor executor, Map<String, Collection<AnalysisProvenance>> output) throws InterruptedException {
		for (Map.Entry<String, AnalysisProvenanceProvider> e : analysisProvenanceProviders.entrySet()) {
			final String provider = e.getKey();
			final AnalysisProvenanceProvider app = e.getValue();

			executor.execute(() -> {
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
			});
		}
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
			// getSampleProvenanceByProviderAndId(filters),
			// getLaneProvenanceByProviderAndId(filters),
			// Future<Map<String, Map<String, SampleProvenance>>> spsByProvider =
			// getSampleProvenanceFutureByProvider(filters);
			// Future<Map<String, Map<String, LaneProvenance>>> lpsByProvider =
			// getLaneProvenanceFutureByProvider(filters);
			Map<String, Map<String, SampleProvenance>> spsByProvider = new HashMap<>();
			getSampleProvenanceFutureByProvider(Collections.<FileProvenanceFilter, Set<String>>emptyMap(), es,
					spsByProvider);
			Map<String, Map<String, LaneProvenance>> lpsByProvider = new HashMap<>();
			getLaneProvenanceFutureByProvider(Collections.<FileProvenanceFilter, Set<String>>emptyMap(), es,
					lpsByProvider);
			Map<String, Collection<AnalysisProvenance>> apsByProvider = new HashMap<>();
			getAnalysisProvenanceFutureByProvider(filters, es, apsByProvider);

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
