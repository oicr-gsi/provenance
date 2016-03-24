package ca.on.oicr.gsi.provenance.api.model;

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

    public Map<String, Set<String>> getParentSampleAttributes();

    public String getSampleName();

    public String getSampleOrganismCode();

    public Map<String, Set<String>> getSampleAttributes();

    public String getSequencerRunName();

    public Map<String, Set<String>> getSequencerRunAttributes();

    public String getSequencerRunPlatformName();

    public String getLaneNumber();

    public Map<String, Set<String>> getLaneAttributes();
    
    public String getIusTag();
    
    public String getSampleProvenanceId();
    
    public String getVersion();
    
    public DateTime getLastModified();

}
