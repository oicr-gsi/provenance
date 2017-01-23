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

    public Collection<FileProvenance> getFileProvenance();

    public Collection<FileProvenance> getFileProvenance(Map<FileProvenanceFilter, Set<String>> filters);

    public Collection<FileProvenance> getFileProvenance(Map<FileProvenanceFilter, Set<String>> includeFilters, Map<FileProvenanceFilter, Set<String>> excludeFilters);

    public Collection<SampleProvenance> getSampleProvenance();

    public Collection<SampleProvenance> getSampleProvenance(Map<FileProvenanceFilter, Set<String>> filters);

    public Collection<LaneProvenance> getLaneProvenance();

    public Collection<LaneProvenance> getLaneProvenance(Map<FileProvenanceFilter, Set<String>> filters);

}
