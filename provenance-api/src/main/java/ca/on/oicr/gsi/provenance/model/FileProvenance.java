package ca.on.oicr.gsi.provenance.model;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.SortedMap;
import java.util.SortedSet;

/**
 *
 * @author mlaszloffy
 */
public interface FileProvenance {

    public enum Status {

        OKAY, ERROR, STALE;
    }

    public Collection<String> getStudyTitles();

    public SortedMap<String, SortedSet<String>> getStudyAttributes();

    public Collection<String> getRootSampleNames();

    public Collection<String> getParentSampleNames();

    public Collection<String> getParentSampleOrganismIDs();

    public SortedMap<String, SortedSet<String>> getParentSampleAttributes();

    public Collection<String> getSampleNames();

    public Collection<String> getSampleOrganismIDs();

    public Collection<String> getSampleOrganismCodes();

    public SortedMap<String, SortedSet<String>> getSampleAttributes();

    public Collection<String> getSequencerRunNames();

    public SortedMap<String, SortedSet<String>> getSequencerRunAttributes();

    public Collection<String> getSequencerRunPlatformIDs();

    public Collection<String> getSequencerRunPlatformNames();

    public Collection<String> getLaneNames();

    public Collection<String> getLaneNumbers();

    public SortedMap<String, SortedSet<String>> getLaneAttributes();

    public String getWorkflowName();

    public String getWorkflowVersion();

    public Integer getWorkflowSWID();

    public SortedMap<String, SortedSet<String>> getWorkflowAttributes();

    public String getWorkflowRunName();

    public String getWorkflowRunStatus();

    public Integer getWorkflowRunSWID();

    public SortedMap<String, SortedSet<String>> getWorkflowRunAttributes();

    public SortedSet<Integer> getWorkflowRunInputFileSWIDs();

    public String getProcessingAlgorithm();

    public Integer getProcessingSWID();

    public SortedMap<String, SortedSet<String>> getProcessingAttributes();

    public String getProcessingStatus();

    public String getFileMetaType();

    public Integer getFileSWID();

    public SortedMap<String, SortedSet<String>> getFileAttributes();

    public String getFilePath();

    public String getFileMd5sum();

    public String getFileSize();

    public String getFileDescription();

    public String getSkip();

    public ZonedDateTime getLastModified();

    public Collection<IusLimsKey> getIusLimsKeys();

    public Collection<String> getIusSWIDs();

    public Collection<String> getIusTags();

    public SortedMap<String, SortedSet<String>> getIusAttributes();

    public Status getStatus();

    public String getStatusReason();

}
