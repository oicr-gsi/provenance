package ca.on.oicr.gsi.provenance.model;

import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
public interface AnalysisProvenance {

    public String getWorkflowName();

    public String getWorkflowVersion();

    public Integer getWorkflowId();

    public SortedMap<String, SortedSet<String>> getWorkflowAttributes();

    public String getWorkflowRunName();

    public String getWorkflowRunStatus();

    public Integer getWorkflowRunId();

    public SortedMap<String, SortedSet<String>> getWorkflowRunAttributes();

    public SortedSet<Integer> getWorkflowRunInputFileIds();

    public String getProcessingAlgorithm();

    public Integer getProcessingId();

    public SortedMap<String, SortedSet<String>> getProcessingAttributes();

    public String getProcessingStatus();

    public String getFileMetaType();

    public Integer getFileId();

    public String getFilePath();

    public String getFileMd5sum();

    public String getFileSize();

    public String getFileDescription();

    public SortedMap<String, SortedSet<String>> getFileAttributes();

    public Boolean getSkip();

    public DateTime getLastModified();

    public Set<IusLimsKey> getIusLimsKeys();

    public SortedMap<String, SortedSet<String>> getIusAttributes();

}
