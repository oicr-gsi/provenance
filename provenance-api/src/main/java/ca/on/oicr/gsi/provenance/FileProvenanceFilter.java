package ca.on.oicr.gsi.provenance;

public enum FileProvenanceFilter {
    study,
    sample,
    root_sample("root-sample"),
    processing,
    processing_status("processing-status"),
    sequencer_run("sequencer-run"),
    sequencer_run_platform_model("sequencer-run-platform-model"),
    lane,
    ius,
    workflow,
    workflow_run("workflow-run"),
    workflow_run_status("workflow-run-status"),
    file,
    file_meta_type("file-meta-type"),
    skip;

    private final String str;

    private FileProvenanceFilter() {
        this.str = null;
    }

    private FileProvenanceFilter(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        if (str == null) {
            return super.toString();
        }
        return str;
    }

    public static FileProvenanceFilter fromString(String s) {
        if (s != null) {
            for (FileProvenanceFilter e : FileProvenanceFilter.values()) {
                if (s.equalsIgnoreCase(e.toString()) || s.equalsIgnoreCase(e.name())) {
                    return e;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant for: " + s);
    }
}
