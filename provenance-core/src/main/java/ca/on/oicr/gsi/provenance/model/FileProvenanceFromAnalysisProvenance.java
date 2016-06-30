package ca.on.oicr.gsi.provenance.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author mlaszloffy
 */
//@AllArgsConstructor
public class FileProvenanceFromAnalysisProvenance implements FileProvenance {

    protected AnalysisProvenance analysisProvenance;

    protected boolean skip;

    protected String status;

    public void setAnalysisProvenance(AnalysisProvenance analysisProvenance) {
        this.analysisProvenance = analysisProvenance;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Collection<String> getStudyTitles() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Map<String, Set<String>> getStudyAttributes() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public Collection<String> getExperimentNames() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Map<String, Set<String>> getExperimentAttributes() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public Collection<String> getRootSampleNames() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Collection<String> getParentSampleNames() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Collection<String> getParentSampleOrganismIDs() {
        return Collections.EMPTY_SET;
    }

    @Override
    @Deprecated
    public Map<String, Set<String>> getParentSampleAttributes() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public Collection<String> getSampleNames() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Collection<String> getSampleOrganismIDs() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Collection<String> getSampleOrganismCodes() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Map<String, Set<String>> getSampleAttributes() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public Collection<String> getSequencerRunNames() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Map<String, Set<String>> getSequencerRunAttributes() {
        return Collections.EMPTY_MAP;

    }

    @Override
    public Collection<String> getSequencerRunPlatformIDs() {
        return Collections.EMPTY_SET;

    }

    @Override
    public Collection<String> getSequencerRunPlatformNames() {
        return Collections.EMPTY_SET;

    }

    @Override
    public Collection<String> getLaneNames() {
        return Collections.EMPTY_SET;

    }

    @Override
    public Collection<String> getLaneNumbers() {
        return Collections.EMPTY_SET;

    }

    @Override
    public Map<String, Set<String>> getLaneAttributes() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public String getWorkflowName() {
        return analysisProvenance.getWorkflowName();

    }

    @Override
    public String getWorkflowVersion() {
        return analysisProvenance.getWorkflowVersion();
    }

    @Override
    public Integer getWorkflowSWID() {
        return analysisProvenance.getWorkflowId();
    }

    @Override
    public Map<String, Set<String>> getWorkflowAttributes() {
        return analysisProvenance.getWorkflowAttributes();
    }

    @Override
    public String getWorkflowRunName() {
        return analysisProvenance.getWorkflowRunName();
    }

    @Override
    public String getWorkflowRunStatus() {
        return analysisProvenance.getWorkflowRunStatus();
    }

    @Override
    public Integer getWorkflowRunSWID() {
        return analysisProvenance.getWorkflowRunId();
    }

    @Override
    public Map<String, Set<String>> getWorkflowRunAttributes() {
        return analysisProvenance.getWorkflowRunAttributes();
    }

    @Override
    public Set<Integer> getWorkflowRunInputFileSWIDs() {
        return analysisProvenance.getWorkflowRunInputFileIds();
    }

    @Override
    public String getProcessingAlgorithm() {
        return analysisProvenance.getProcessingAlgorithm();
    }

    @Override
    public Integer getProcessingSWID() {
        return analysisProvenance.getProcessingId();
    }

    @Override
    public Map<String, Set<String>> getProcessingAttributes() {
        return analysisProvenance.getProcessingAttributes();
    }

    @Override
    public String getProcessingStatus() {
        return analysisProvenance.getProcessingStatus();
    }

    @Override
    public String getFileMetaType() {
        return analysisProvenance.getFileMetaType();
    }

    @Override
    public Integer getFileSWID() {
        return analysisProvenance.getFileId();
    }

    @Override
    public Map<String, Set<String>> getFileAttributes() {
        return analysisProvenance.getFileAttributes();
    }

    @Override
    public String getFilePath() {
        return analysisProvenance.getFilePath();
    }

    @Override
    public String getFileMd5sum() {
        return analysisProvenance.getFileMd5sum();
    }

    @Override
    public String getFileSize() {
        return analysisProvenance.getFileSize();
    }

    @Override
    public String getFileDescription() {
        return analysisProvenance.getFileDescription();
    }

    @Override
    public String getSkip() {
        return Boolean.toString(skip);
    }

    @Override
    public Collection<IusLimsKey> getIusLimsKeys() {
        return analysisProvenance.getIusLimsKeys();
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public DateTime getLastModified() {
        DateTime lastModified = null;

        lastModified = ObjectUtils.max(lastModified, analysisProvenance.getLastModified());

        if (lastModified == null) {
            return null;
        } else {
            return lastModified.toDateTime(DateTimeZone.UTC);
        }
    }

    @Override
    public Collection<String> getIusSWIDs() {
        Set<String> iusSWIDs = new HashSet<>();
        for (IusLimsKey iusKey : analysisProvenance.getIusLimsKeys()) {
            iusSWIDs.add(iusKey.getIusSWID().toString());
        }
        return iusSWIDs;
    }

    @Override
    public Collection<String> getIusTags() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Map<String, Set<String>> getIusAttributes() {
        return analysisProvenance.getIusAttributes();
    }

    @Override
    public String toString() {
        return "FileProvenance{"
                + "studyTitles=" + getStudyTitles() + ", "
                + "studyAttributes=" + getStudyAttributes() + ", "
                + "experimentNames=" + getExperimentNames() + ", "
                + "experimentAttributes=" + getExperimentAttributes() + ", "
                + "rootSampleNames=" + getRootSampleNames() + ", "
                + "parentSampleNames=" + getParentSampleNames() + ", "
                + "parentSampleOrganismIDs=" + getParentSampleOrganismIDs() + ", "
                + "parentSampleAttributes=" + getParentSampleAttributes() + ", "
                + "sampleNames=" + getSampleNames() + ", "
                + "sampleOrganismIDs=" + getSampleOrganismIDs() + ", "
                + "sampleOrganismCodes=" + getSampleOrganismCodes() + ", "
                + "sampleAttributes=" + getSampleAttributes() + ", "
                + "sequencerRunNames=" + getSequencerRunNames() + ", "
                + "sequencerRunAttributes=" + getSequencerRunAttributes() + ", "
                + "sequencerRunPlatformIDs=" + getSequencerRunPlatformIDs() + ", "
                + "sequencerRunPlatformNames=" + getSequencerRunPlatformNames() + ", "
                + "laneNames=" + getLaneNames() + ", "
                + "laneNumbers=" + getLaneNumbers() + ", "
                + "laneAttributes=" + getLaneAttributes() + ", "
                + "workflowName=" + getWorkflowName() + ", "
                + "workflowVersion=" + getWorkflowVersion() + ", "
                + "workflowSWID=" + getWorkflowSWID() + ", "
                + "workflowAttributes=" + getWorkflowAttributes() + ", "
                + "workflowRunName=" + getWorkflowRunName() + ", "
                + "workflowRunStatus=" + getWorkflowRunStatus() + ", "
                + "workflowRunSWID=" + getWorkflowRunSWID() + ", "
                + "workflowRunAttributes=" + getWorkflowRunAttributes() + ", "
                + "workflowRunInputFileSWIDs=" + getWorkflowRunInputFileSWIDs() + ", "
                + "processingAlgorithm=" + getProcessingAlgorithm() + ", "
                + "processingSWID=" + getProcessingSWID() + ", "
                + "processingAttributes=" + getProcessingAttributes() + ", "
                + "processingStatus=" + getProcessingStatus() + ", "
                + "fileMetaType=" + getFileMetaType() + ", "
                + "fileSWID=" + getFileSWID() + ", "
                + "fileAttributes=" + getFileAttributes() + ", "
                + "filePath=" + getFilePath() + ", "
                + "fileMd5sum=" + getFileMd5sum() + ", "
                + "fileSize=" + getFileSize() + ", "
                + "fileDescription=" + getFileDescription() + ", "
                + "skip=" + getSkip() + ", "
                + "lastModified=" + getLastModified() + ", "
                + "iusLimsKeys=" + getIusLimsKeys() + ", "
                + "iusSWIDs=" + getIusSWIDs() + ", "
                + "status=" + getStatus()
                + '}';
    }

}
