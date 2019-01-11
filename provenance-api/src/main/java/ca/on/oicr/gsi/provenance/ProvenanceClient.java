package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenance;
import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mlaszloffy
 */
public interface ProvenanceClient {

    public Collection<? extends FileProvenance> getFileProvenance();

    public Collection<? extends FileProvenance> getFileProvenance(Map<FileProvenanceFilter, Set<String>> filters);

    public Collection<? extends FileProvenance> getFileProvenance(Map<FileProvenanceFilter, Set<String>> includeFilters, Map<FileProvenanceFilter, Set<String>> excludeFilters);

    public Collection<? extends SampleProvenance> getSampleProvenance();

    public Collection<? extends SampleProvenance> getSampleProvenance(Map<FileProvenanceFilter, Set<String>> filters);

    public Collection<? extends LaneProvenance> getLaneProvenance();

    public Collection<? extends LaneProvenance> getLaneProvenance(Map<FileProvenanceFilter, Set<String>> filters);

}
