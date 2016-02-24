package ca.on.oicr.gsi.provenance.api;

import ca.on.oicr.gsi.provenance.api.model.SampleProvenance;
import ca.on.oicr.gsi.provenance.api.model.FileProvenance;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mlaszloffy
 */
public interface ProvenanceClient {

    public Collection<FileProvenance> getFileProvenance();

    public Collection<FileProvenance> getFileProvenance(Map<String, Set<String>> filters);

    public Collection<SampleProvenance> getSampleProvenance();

    public Collection<SampleProvenance> getSampleProvenance(Map<String, Set<String>> filters);

}
