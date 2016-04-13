package ca.on.oicr.gsi.provenance.api;

import ca.on.oicr.gsi.provenance.api.model.SampleProvenance;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mlaszloffy
 */
public interface SampleProvenanceProvider {

    public Collection<SampleProvenance> getSampleProvenance();

    public Collection<SampleProvenance> getSampleProvenance(Map<String, Set<String>> filters);

}
