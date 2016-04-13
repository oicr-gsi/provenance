package ca.on.oicr.gsi.provenance.api;

import ca.on.oicr.gsi.provenance.api.model.AnalysisProvenance;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mlaszloffy
 */
public interface AnalysisProvenanceProvider {

    public Collection<AnalysisProvenance> getAnalysisProvenance();

    public Collection<AnalysisProvenance> getAnalysisProvenance(Map<String, Set<String>> filters);

}
