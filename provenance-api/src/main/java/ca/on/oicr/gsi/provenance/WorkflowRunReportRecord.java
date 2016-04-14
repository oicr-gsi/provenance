package ca.on.oicr.gsi.provenance;

/**
 *
 * @author mlaszloffy
 */
public interface WorkflowRunReportRecord {

    public String getWorkflowName();

    public String getWorkflowRunSwid();

    public String getWorkflowRunStatus();

    public String getWorkflowRunCreateTime();

    public String getWorkflowRunHost();

    public String getWorkflowRunWorkingDir();

    public String getWorkflowRunEngineId();

    public String getInputFileMetaTypes();

    public String getInputFileSwids();

    public String getInputFilePaths();

    public String getImmediateInputFileMetaTypes();

    public String getImmediateInputFileSwids();

    public String getImmediateInputFilePaths();

    public String getOutputFileMetaTypes();

    public String getOutputFileSwids();

    public String getOutputFilePaths();

    public String getWorkflowRunTime();

}
