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

    public Map<String, Collection<LaneProvenance>> getLaneProvenanceByProvider(Map<String, Set<String>> filters);

    public Map<String, Map<String, LaneProvenance>> getLaneProvenanceByProviderAndId(Map<String, Set<String>> filters);

    public Map<String, Collection<SampleProvenance>> getSampleProvenanceByProvider(Map<String, Set<String>> filters);

    public Map<String, Map<String, SampleProvenance>> getSampleProvenanceByProviderAndId(Map<String, Set<String>> filters);

    public Map<String, Collection<AnalysisProvenance>> getAnalysisProvenanceByProvider(Map<String, Set<String>> filters);

}
