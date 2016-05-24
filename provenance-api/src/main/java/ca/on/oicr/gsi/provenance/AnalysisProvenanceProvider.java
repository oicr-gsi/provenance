package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.AnalysisProvenance;
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
