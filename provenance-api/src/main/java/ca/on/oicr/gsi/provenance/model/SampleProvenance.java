package ca.on.oicr.gsi.provenance.model;

import java.util.Map;
import java.util.Set;
import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
public interface SampleProvenance {

    public String getStudyTitle();

    public Map<String, Set<String>> getStudyAttributes();

    public String getRootSampleName();

    public String getParentSampleName();

    public String getSampleName();

    public Map<String, Set<String>> getSampleAttributes();

    public String getSequencerRunName();

    public Map<String, Set<String>> getSequencerRunAttributes();

    public String getSequencerRunPlatformModel();

    public String getLaneNumber();

    public Map<String, Set<String>> getLaneAttributes();
    
    public String getIusTag();
    
    public String getSampleProvenanceId();
    
    public String getVersion();
    
    public DateTime getLastModified();

}
