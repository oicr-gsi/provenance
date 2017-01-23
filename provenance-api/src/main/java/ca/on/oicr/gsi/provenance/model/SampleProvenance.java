package ca.on.oicr.gsi.provenance.model;

import java.util.SortedMap;
import java.util.SortedSet;
import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
public interface SampleProvenance extends LimsProvenance {

    public String getStudyTitle();

    public SortedMap<String, SortedSet<String>> getStudyAttributes();

    public String getRootSampleName();

    public String getParentSampleName();

    public String getSampleName();

    public SortedMap<String, SortedSet<String>> getSampleAttributes();

    public String getSequencerRunName();

    public SortedMap<String, SortedSet<String>> getSequencerRunAttributes();

    public String getSequencerRunPlatformModel();

    public String getLaneNumber();

    public SortedMap<String, SortedSet<String>> getLaneAttributes();

    public String getIusTag();

    public Boolean getSkip();

    public String getSampleProvenanceId();

    public DateTime getCreatedDate();

}
