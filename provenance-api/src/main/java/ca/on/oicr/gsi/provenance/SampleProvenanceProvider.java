package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mlaszloffy
 */
public interface SampleProvenanceProvider extends AutoCloseable {

    public Collection<? extends SampleProvenance> getSampleProvenance();

    public Collection<? extends SampleProvenance> getSampleProvenance(Map<FileProvenanceFilter, Set<String>> filters);

}
