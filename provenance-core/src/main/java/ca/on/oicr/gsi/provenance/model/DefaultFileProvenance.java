package ca.on.oicr.gsi.provenance.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import lombok.Builder;
import lombok.Singular;
import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author mlaszloffy
 */
@Builder
public class DefaultFileProvenance implements FileProvenance {

    @Singular
    private final Collection<SampleProvenance> sampleProvenances;

    private final AnalysisProvenance analysisProvenance;

    private boolean skip;

    private final String status;

    @Override
    public Collection<String> getStudyTitles() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getStudyTitle() != null) {
                s.add(sp.getStudyTitle());
            }
        }
        return s;
    }

    @Override
    public Map<String, Set<String>> getStudyAttributes() {
        Map<String, Set<String>> attrs = new HashMap<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSampleAttributes() != null) {
                attrs.putAll(sp.getStudyAttributes());
            }
        }
        return attrs;
    }

    @Override
    public Collection<String> getExperimentNames() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            //s.add(sp.getExperimentName());
        }
        return s;
    }

    @Override
    public Map<String, Set<String>> getExperimentAttributes() {
        Map<String, Set<String>> attrs = new HashMap<>();
        for (SampleProvenance sp : sampleProvenances) {
            //s.addAll(sp.getExperimentAttributes());
        }
        return attrs;
    }

    @Override
    public Collection<String> getRootSampleNames() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getRootSampleName() != null) {
                s.add(sp.getRootSampleName());
            }
        }
        return s;
    }

    @Override
    public Collection<String> getParentSampleNames() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getParentSampleName() != null) {
                s.add(sp.getParentSampleName());
            }
        }
        return s;
    }

    @Override
    public Collection<String> getParentSampleOrganismIDs() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            //s.addAll(sp.getParentSampleOrganismIDs());
        }
        return s;
    }

    @Override
    @Deprecated
    public Map<String, Set<String>> getParentSampleAttributes() {
        Map<String, Set<String>> attrs = new HashMap<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSampleAttributes() != null) {
                attrs.putAll(sp.getSampleAttributes());
            }
        }
        return attrs;
    }

    @Override
    public Collection<String> getSampleNames() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSampleName() != null) {
                s.add(sp.getSampleName());
            }
        }
        return s;
    }

    @Override
    public Collection<String> getSampleOrganismIDs() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            //s.addAll(sp.getSampleOrganismIDs());
        }
        return s;
    }

    @Override
    public Collection<String> getSampleOrganismCodes() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            //s.addAll(sp.getSampleOrganismCodes());
        }
        return s;
    }

    @Override
    public Map<String, Set<String>> getSampleAttributes() {
        Map<String, Set<String>> attrs = new HashMap<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSampleAttributes() != null) {
                attrs.putAll(sp.getSampleAttributes());
            }
        }
        return attrs;
    }

    @Override
    public Collection<String> getSequencerRunNames() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSequencerRunName() != null) {
                s.add(sp.getSequencerRunName());
            }
        }
        return s;
    }

    @Override
    public Map<String, Set<String>> getSequencerRunAttributes() {
        Map<String, Set<String>> attrs = new HashMap<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSampleAttributes() != null) {
                attrs.putAll(sp.getSequencerRunAttributes());
            }
        }
        return attrs;
    }

    @Override
    public Collection<String> getSequencerRunPlatformIDs() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            //s.addAll(sp.getSequencerRunPlatformIDs());
        }
        return s;
    }

    @Override
    public Collection<String> getSequencerRunPlatformNames() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSequencerRunPlatformModel() != null) {
                s.add(sp.getSequencerRunPlatformModel());
            }
        }
        return s;
    }

    @Override
    public Collection<String> getLaneNames() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            //s.addAll(sp.getLaneNames());
        }
        return s;
    }

    @Override
    public Collection<String> getLaneNumbers() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getLaneNumber() != null) {
                s.add(sp.getLaneNumber());
            }
        }
        return s;
    }

    @Override
    public Map<String, Set<String>> getLaneAttributes() {
        Map<String, Set<String>> attrs = new HashMap<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getLaneAttributes() != null) {
                attrs.putAll(sp.getLaneAttributes());
            }
        }
        return attrs;
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
    public String getLastModified() {
        DateTime lastModified = null;

        for (SampleProvenance sampleProvenance : sampleProvenances) {
            lastModified = ObjectUtils.max(lastModified, sampleProvenance.getLastModified());
        }

        lastModified = ObjectUtils.max(lastModified, analysisProvenance.getLastModified());

        if (lastModified == null) {
            return null;
        } else {
            return lastModified.toDateTime(DateTimeZone.UTC).toString();
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
