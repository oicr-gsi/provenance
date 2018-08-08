package ca.on.oicr.gsi.provenance.model;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author mlaszloffy
 */
//@AllArgsConstructor
public class FileProvenanceFromAnalysisProvenance implements FileProvenance {

    protected AnalysisProvenance analysisProvenance;

    protected boolean skip;

    protected Status status;
    protected String statusReason;

    private Collection<IusLimsKey> iusLimsKeys;

    public void setAnalysisProvenance(AnalysisProvenance analysisProvenance) {
        this.analysisProvenance = analysisProvenance;
    }

    public void setIusLimsKeys(Collection<IusLimsKey> iusLimsKeys) {
        this.iusLimsKeys = iusLimsKeys;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    @Override
    public Collection<String> getStudyTitles() {
        return Collections.emptySet();
    }

    @Override
    public SortedMap<String, SortedSet<String>> getStudyAttributes() {
        return Collections.emptySortedMap();
    }

    @Override
    public Collection<String> getRootSampleNames() {
        return Collections.emptySet();
    }

    @Override
    public Collection<String> getParentSampleNames() {
        return Collections.emptySet();
    }

    @Override
    public Collection<String> getParentSampleOrganismIDs() {
        return Collections.emptySet();
    }

    @Override
    @Deprecated
    public SortedMap<String, SortedSet<String>> getParentSampleAttributes() {
        return Collections.emptySortedMap();
    }

    @Override
    public Collection<String> getSampleNames() {
        return Collections.emptySet();
    }

    @Override
    public Collection<String> getSampleOrganismIDs() {
        return Collections.emptySet();
    }

    @Override
    public Collection<String> getSampleOrganismCodes() {
        return Collections.emptySet();
    }

    @Override
    public SortedMap<String, SortedSet<String>> getSampleAttributes() {
        return Collections.emptySortedMap();
    }

    @Override
    public Collection<String> getSequencerRunNames() {
        return Collections.emptySet();
    }

    @Override
    public SortedMap<String, SortedSet<String>> getSequencerRunAttributes() {
        return Collections.emptySortedMap();

    }

    @Override
    public Collection<String> getSequencerRunPlatformIDs() {
        return Collections.emptySet();

    }

    @Override
    public Collection<String> getSequencerRunPlatformNames() {
        return Collections.emptySet();

    }

    @Override
    public Collection<String> getLaneNames() {
        return Collections.emptySet();

    }

    @Override
    public Collection<String> getLaneNumbers() {
        return Collections.emptySet();

    }

    @Override
    public SortedMap<String, SortedSet<String>> getLaneAttributes() {
        return Collections.emptySortedMap();
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
    public SortedMap<String, SortedSet<String>> getWorkflowAttributes() {
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
    public SortedMap<String, SortedSet<String>> getWorkflowRunAttributes() {
        return analysisProvenance.getWorkflowRunAttributes();
    }

    @Override
    public SortedSet<Integer> getWorkflowRunInputFileSWIDs() {
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
    public SortedMap<String, SortedSet<String>> getProcessingAttributes() {
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
    public SortedMap<String, SortedSet<String>> getFileAttributes() {
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
        if (iusLimsKeys != null) {
            return iusLimsKeys;
        } else {
            return analysisProvenance.getIusLimsKeys();
        }
    }

    public Collection<IusLimsKey> getRelatedIusLimsKeys() {
        return analysisProvenance.getIusLimsKeys();
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getStatusReason() {
        return statusReason;
    }

    @Override
    public ZonedDateTime getLastModified() {
        ZonedDateTime lastModified = analysisProvenance.getLastModified();

        if (lastModified == null) {
            return null;
        } else {
            return lastModified.withZoneSameInstant(ZoneId.of("Z"));
        }
    }

    @Override
    public Collection<String> getIusSWIDs() {
        SortedSet<String> iusSWIDs = new TreeSet<>();
        for (IusLimsKey iusKey : getIusLimsKeys()) {
            iusSWIDs.add(iusKey.getIusSWID().toString());
        }
        return iusSWIDs;
    }

    public Collection<String> getReleatedIusSWIDs() {
        SortedSet<String> iusSWIDs = new TreeSet<>();
        for (IusLimsKey iusKey : getRelatedIusLimsKeys()) {
            iusSWIDs.add(iusKey.getIusSWID().toString());
        }
        return iusSWIDs;
    }

    @Override
    public Collection<String> getIusTags() {
        return Collections.emptySet();
    }

    @Override
    public SortedMap<String, SortedSet<String>> getIusAttributes() {
        return analysisProvenance.getIusAttributes();
    }

    @Override
    public String toString() {
        return "FileProvenance{"
                + "studyTitles=" + getStudyTitles() + ", "
                + "studyAttributes=" + getStudyAttributes() + ", "
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
