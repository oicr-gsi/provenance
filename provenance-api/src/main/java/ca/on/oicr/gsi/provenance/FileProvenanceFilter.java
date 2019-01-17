package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.FileProvenance;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public enum FileProvenanceFilter {
  study {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return !Collections.disjoint(vals, f.getStudyTitles());
    }
  },
  sample {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return !Collections.disjoint(vals, f.getSampleNames());
    }
  },
  root_sample("root-sample") {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return !Collections.disjoint(vals, f.getRootSampleNames());
    }
  },
  processing {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return vals.contains(Objects.toString(f.getProcessingSWID()));
    }
  },
  processing_status("processing-status") {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return vals.contains(f.getProcessingStatus());
    }
  },
  sequencer_run("sequencer-run") {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return !Collections.disjoint(vals, f.getSequencerRunNames());
    }
  },
  sequencer_run_platform_model("sequencer-run-platform-model") {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return !Collections.disjoint(vals, f.getSequencerRunPlatformNames());
    }
  },
  lane {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return !Collections.disjoint(vals, f.getLaneNames());
    }
  },
  ius {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return !Collections.disjoint(vals, f.getIusSWIDs());
    }
  },
  workflow {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return vals.contains(Objects.toString(f.getWorkflowSWID()));
    }
  },
  workflow_run("workflow-run") {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return vals.contains(Objects.toString(f.getWorkflowRunSWID()));
    }
  },
  workflow_run_status("workflow-run-status") {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return vals.contains(f.getWorkflowRunStatus());
    }
  },
  file {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return vals.contains(Objects.toString(f.getFileSWID()));
    }
  },
  file_meta_type("file-meta-type") {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return vals.contains(f.getFileMetaType());
    }
  },
  skip {
    @Override
    public boolean checkFilter(FileProvenance f, Set<String> vals) {
      return vals.contains(f.getSkip());
    }
  };

  private final String str;

  private FileProvenanceFilter() {
    this.str = null;
  }

  private FileProvenanceFilter(String str) {
    this.str = str;
  }

  public abstract boolean checkFilter(FileProvenance f, Set<String> vals);

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
