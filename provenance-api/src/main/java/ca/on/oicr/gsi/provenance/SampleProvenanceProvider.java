package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
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