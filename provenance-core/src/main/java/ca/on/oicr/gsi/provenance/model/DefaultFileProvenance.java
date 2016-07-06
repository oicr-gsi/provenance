package ca.on.oicr.gsi.provenance.model;

import java.util.Collection;
import java.util.SortedMap;
import java.util.SortedSet;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
@Builder(builderClassName = "Builder")
@Value
public class DefaultFileProvenance implements FileProvenance {

    //sample info
    @Singular
    private final Collection<String> studyTitles;

    private final SortedMap<String, SortedSet<String>> studyAttributes;

    @Singular
    private final Collection<String> experimentNames;

    private final SortedMap<String, SortedSet<String>> experimentAttributes;

    @Singular
    private final Collection<String> rootSampleNames;

    @Singular
    private final Collection<String> parentSampleNames;
    private final Collection<String> parentSampleOrganismIDs;
    private final SortedMap<String, SortedSet<String>> parentSampleAttributes;

    @Singular
    private final Collection<String> sampleNames;
    private final Collection<String> sampleOrganismIDs;

    @Singular
    private final Collection<String> sampleOrganismCodes;
    private final SortedMap<String, SortedSet<String>> sampleAttributes;

    @Singular
    private final Collection<String> sequencerRunNames;

    private final SortedMap<String, SortedSet<String>> sequencerRunAttributes;
    private final Collection<String> sequencerRunPlatformIDs;

    @Singular
    private final Collection<String> sequencerRunPlatformNames;
    private final Collection<String> laneNames;

    @Singular
    private final Collection<String> laneNumbers;

    private final SortedMap<String, SortedSet<String>> laneAttributes;

    //analysis info
    private final String workflowName;
    private final String workflowVersion;
    private final Integer workflowSWID;
    private final SortedMap<String, SortedSet<String>> workflowAttributes;
    private final String workflowRunName;
    private final String workflowRunStatus;
    private final Integer workflowRunSWID;
    private final SortedMap<String, SortedSet<String>> workflowRunAttributes;
    private final SortedSet<Integer> workflowRunInputFileSWIDs;
    private final String processingAlgorithm;
    private final Integer processingSWID;
    private final SortedMap<String, SortedSet<String>> processingAttributes;
    private final String processingStatus;
    private final String fileMetaType;
    private final Integer fileSWID;
    private final SortedMap<String, SortedSet<String>> fileAttributes;
    private final String filePath;
    private final String fileMd5sum;
    private final String fileSize;
    private final String fileDescription;
    private final String skip;

    //record status
    private final Status status;
    private final DateTime lastModified;

    @Singular
    private final Collection<IusLimsKey> iusLimsKeys;

    @Singular
    private final Collection<String> iusSWIDs;

    private final Collection<String> iusTags;

    private final SortedMap<String, SortedSet<String>> iusAttributes;
    
    public static class Builder {
        
        public Builder copyFrom(FileProvenance fp) {
            studyTitles(fp.getStudyTitles());
            studyAttributes(fp.getStudyAttributes());
            experimentNames(fp.getExperimentNames());
            experimentAttributes(fp.getExperimentAttributes());
            rootSampleNames(fp.getRootSampleNames());
            parentSampleNames(fp.getParentSampleNames());
            parentSampleOrganismIDs(fp.getParentSampleOrganismIDs());
            parentSampleAttributes(fp.getParentSampleAttributes());
            sampleNames(fp.getSampleNames());
            sampleOrganismIDs(fp.getSampleOrganismIDs());
            sampleOrganismCodes(fp.getSampleOrganismCodes());
            sampleAttributes(fp.getSampleAttributes());
            sequencerRunNames(fp.getSequencerRunNames());
            sequencerRunAttributes(fp.getSequencerRunAttributes());
            sequencerRunPlatformIDs(fp.getSequencerRunPlatformIDs());
            sequencerRunPlatformNames(fp.getSequencerRunPlatformNames());
            laneNames(fp.getLaneNames());
            laneNumbers(fp.getLaneNumbers());
            laneAttributes(fp.getLaneAttributes());
            workflowName(fp.getWorkflowName());
            workflowVersion(fp.getWorkflowVersion());
            workflowSWID(fp.getWorkflowSWID());
            workflowAttributes(fp.getWorkflowAttributes());
            workflowRunName(fp.getWorkflowRunName());
            workflowRunStatus(fp.getWorkflowRunStatus());
            workflowRunSWID(fp.getWorkflowSWID());
            workflowRunAttributes(fp.getWorkflowRunAttributes());
            workflowRunInputFileSWIDs(fp.getWorkflowRunInputFileSWIDs());
            processingAlgorithm(fp.getProcessingAlgorithm());
            processingSWID(fp.getProcessingSWID());
            processingAttributes(fp.getProcessingAttributes());
            processingStatus(fp.getProcessingStatus());
            fileMetaType(fp.getFileMetaType());
            fileSWID(fp.getFileSWID());
            fileAttributes(fp.getFileAttributes());
            filePath(fp.getFilePath());
            fileMd5sum(fp.getFileMd5sum());
            fileDescription(fp.getFileDescription());
            skip(fp.getSkip());
            
            return this;
        }
        
    }
}
