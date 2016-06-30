package ca.on.oicr.gsi.provenance.model;

import java.util.Map;
import java.util.Set;
import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
public interface LaneProvenance {

    public String getSequencerRunName();

    public Map<String, Set<String>> getSequencerRunAttributes();

    public String getSequencerRunPlatformModel();

    public String getLaneNumber();

    public Map<String, Set<String>> getLaneAttributes();
    
    public Boolean getSkip();

    public String getLaneProvenanceId();

    public String getVersion();

    public DateTime getLastModified();

}
