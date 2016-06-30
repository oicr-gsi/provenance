package ca.on.oicr.gsi.provenance.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
public interface FileProvenance {

    public enum Status {

        OKAY, ERROR, STALE;
    }

    public Collection<String> getStudyTitles();

    public Map<String, Set<String>> getStudyAttributes();

    public Collection<String> getExperimentNames();

    public Map<String, Set<String>> getExperimentAttributes();

    public Collection<String> getRootSampleNames();

    public Collection<String> getParentSampleNames();

    public Collection<String> getParentSampleOrganismIDs();

    public Map<String, Set<String>> getParentSampleAttributes();

    public Collection<String> getSampleNames();

    public Collection<String> getSampleOrganismIDs();

    public Collection<String> getSampleOrganismCodes();

    public Map<String, Set<String>> getSampleAttributes();

    public Collection<String> getSequencerRunNames();

    public Map<String, Set<String>> getSequencerRunAttributes();

    public Collection<String> getSequencerRunPlatformIDs();

    public Collection<String> getSequencerRunPlatformNames();

    public Collection<String> getLaneNames();

    public Collection<String> getLaneNumbers();

    public Map<String, Set<String>> getLaneAttributes();

    public String getWorkflowName();

    public String getWorkflowVersion();

    public Integer getWorkflowSWID();

    public Map<String, Set<String>> getWorkflowAttributes();

    public String getWorkflowRunName();

    public String getWorkflowRunStatus();

    public Integer getWorkflowRunSWID();

    public Map<String, Set<String>> getWorkflowRunAttributes();

    public Set<Integer> getWorkflowRunInputFileSWIDs();

    public String getProcessingAlgorithm();

    public Integer getProcessingSWID();

    public Map<String, Set<String>> getProcessingAttributes();

    public String getProcessingStatus();

    public String getFileMetaType();

    public Integer getFileSWID();

    public Map<String, Set<String>> getFileAttributes();

    public String getFilePath();

    public String getFileMd5sum();

    public String getFileSize();

    public String getFileDescription();

    public String getSkip();

    public DateTime getLastModified();

    public Collection<IusLimsKey> getIusLimsKeys();

    public Collection<String> getIusSWIDs();
    
    public String getStatus();

}
