package ca.on.oicr.gsi.provenance.model;

import java.util.SortedMap;
import java.util.SortedSet;
import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
public interface LaneProvenance extends LimsProvenance {

    public String getSequencerRunName();

    public SortedMap<String, SortedSet<String>> getSequencerRunAttributes();

    public String getSequencerRunPlatformModel();

    public String getLaneNumber();

    public SortedMap<String, SortedSet<String>> getLaneAttributes();
    
    public Boolean getSkip();

    public String getLaneProvenanceId();

    public DateTime getCreatedDate();

}
