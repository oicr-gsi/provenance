package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.AnalysisProvenance;
import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mlaszloffy
 */
public interface ExtendedProvenanceClient extends ProvenanceClient {

	public Map<String, Collection<? extends LaneProvenance>> getLaneProvenanceByProvider(
			Map<FileProvenanceFilter, Set<String>> filters);

	public Map<String, Map<String, ? extends LaneProvenance>> getLaneProvenanceByProviderAndId(
			Map<FileProvenanceFilter, Set<String>> filters);

	public Map<String, Collection<? extends SampleProvenance>> getSampleProvenanceByProvider(
			Map<FileProvenanceFilter, Set<String>> filters);

	public Map<String, Map<String, ? extends SampleProvenance>> getSampleProvenanceByProviderAndId(
			Map<FileProvenanceFilter, Set<String>> filters);

	public Map<String, Collection<AnalysisProvenance>> getAnalysisProvenanceByProvider(
			Map<FileProvenanceFilter, Set<String>> filters);

}
