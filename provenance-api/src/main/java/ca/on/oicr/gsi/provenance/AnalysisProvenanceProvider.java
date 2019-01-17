package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.AnalysisProvenance;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/** @author mlaszloffy */
public interface AnalysisProvenanceProvider extends AutoCloseable {

  public Collection<AnalysisProvenance> getAnalysisProvenance();

  public Collection<AnalysisProvenance> getAnalysisProvenance(
      Map<FileProvenanceFilter, Set<String>> filters);
}
