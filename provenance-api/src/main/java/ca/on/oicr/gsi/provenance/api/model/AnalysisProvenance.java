package ca.on.oicr.gsi.provenance.api.model;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author mlaszloffy
 */
public interface AnalysisProvenance {

    public String getWorkflowName();

    public String getWorkflowVersion();

    public Integer getWorkflowId();

    public Map<String, Set<String>> getWorkflowAttributes();

    public String getWorkflowRunName();

    public String getWorkflowRunStatus();

    public Integer getWorkflowRunId();

    public Map<String, Set<String>> getWorkflowRunAttributes();

    public Set<Integer> getWorkflowRunInputFileIds();

    public String getProcessingAlgorithm();

    public Integer getProcessingId();

    public Map<String, Set<String>> getProcessingAttributes();

    public String getProcessingStatus();

    public String getFileMetaType();

    public Integer getFileId();

    public String getFilePath();

    public String getFileMd5sum();

    public String getFileSize();

    public String getFileDescription();

    public Map<String, Set<String>> getFileAttributes();

    public String getSkip();

    public String getLastModified();

    public Set<IusLimsKey> getIusLimsKeys();

}
