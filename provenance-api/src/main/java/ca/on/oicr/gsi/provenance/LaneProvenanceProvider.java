package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mlaszloffy
 */
public interface LaneProvenanceProvider extends AutoCloseable {

    public Collection<? extends LaneProvenance> getLaneProvenance();

    public Collection<? extends LaneProvenance> getLaneProvenance(Map<FileProvenanceFilter, Set<String>> filters);

}
