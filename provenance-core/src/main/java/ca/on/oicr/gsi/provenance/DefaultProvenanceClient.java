package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.AnalysisProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenance;
import ca.on.oicr.gsi.provenance.model.IusLimsKey;
import ca.on.oicr.gsi.provenance.model.LimsKey;
import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.gsi.provenance.model.DefaultFileProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenanceParam;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;

/**
 *
 * @author mlaszloffy
 */
public class DefaultProvenanceClient implements ProvenanceClient {

    private final AnalysisProvenanceProvider analysisProvenanceProvider;
    private final SampleProvenanceProvider sampleProvenanceProvider;

    public DefaultProvenanceClient(AnalysisProvenanceProvider analyisProvenanceProvider, SampleProvenanceProvider sampleProvenanceProvider) {
        this.analysisProvenanceProvider = analyisProvenanceProvider;
        this.sampleProvenanceProvider = sampleProvenanceProvider;
    }

    @Override
    public Collection<SampleProvenance> getSampleProvenance() {
        return sampleProvenanceProvider.getSampleProvenance();
    }

    @Override
    public Collection<SampleProvenance> getSampleProvenance(Map<String, Set<String>> filters) {
        return sampleProvenanceProvider.getSampleProvenance(filters);
    }

    @Override
    public Collection<FileProvenance> getFileProvenance() {

        //build sample provenance lookup table
        Map<String, SampleProvenance> sps = new HashMap<>();
        for (SampleProvenance sp : this.getSampleProvenance()) {
            if (sps.put(sp.getSampleProvenanceId(), sp) != null) {
                throw new RuntimeException("Duplicate sample provenance ID = [" + sp.getSampleProvenanceId() + "]");
            }
        }

        //build file provenance
        List<FileProvenance> fps = new ArrayList<>();
        for (AnalysisProvenance ap : analysisProvenanceProvider.getAnalysisProvenance()) {
            //if ("success".equals(ap.getProcessingStatus()) && "completed".equals(ap.getWorkflowRunStatus())) {
            List<DefaultFileProvenance.DefaultFileProvenanceBuilder> builders = new ArrayList<>();
            boolean skip = false;
            String status = "OKAY";

            for (IusLimsKey ik : ap.getIusLimsKeys()) {

                LimsKey lk = ik.getLimsKey();
                SampleProvenance sp = null;
                if (lk == null) {
                    status = "ERROR";
                } else {
                    sp = sps.get(ik.getLimsKey().getId());
                }

                DefaultFileProvenance.DefaultFileProvenanceBuilder b = DefaultFileProvenance.builder();

                if (sp == null) {
                    status = "ERROR";
                } else if (!skip) {
                    b.sampleProvenance(sp);

                    if (ap.getSkip().equals("true")) {
                        skip = true;
                    } else if (sp.getLaneAttributes().containsKey("skip")
                            || sp.getSequencerRunAttributes().containsKey("skip")
                            || sp.getSampleAttributes().containsKey("skip")
                            || sp.getStudyAttributes().containsKey("skip")) {
                        skip = true;
                    }
                }

                b.analysisProvenance(ap);
                builders.add(b);
            }

            for (DefaultFileProvenance.DefaultFileProvenanceBuilder b : builders) {
                if (skip) {
                    b.skip(true);
                }

                fps.add(b.build());
            }
        }

        return fps;
    }

    @Override
    public Collection<FileProvenance> getFileProvenance(Map<String, Set<String>> filters) {
        Collection<FileProvenance> fps = new ArrayList<>();
        for (FileProvenance f : getFileProvenance()) {
            if (filters.containsKey(FileProvenanceParam.processing_status.toString())) {
                if (!filters.get(FileProvenanceParam.processing_status.toString()).contains(f.getProcessingStatus())) {
                    continue;
                }
            }
            if (filters.containsKey(FileProvenanceParam.workflow_run_status.toString())) {
                if (!filters.get(FileProvenanceParam.workflow_run_status.toString()).contains(f.getWorkflowRunStatus())) {
                    continue;
                }
            }
            if (filters.containsKey(FileProvenanceParam.study.toString())) {
                if (!CollectionUtils.containsAny(filters.get(FileProvenanceParam.study.toString()), f.getStudyTitles())) {
                    continue;
                }
            }
            if (filters.containsKey(FileProvenanceParam.experiment.toString())) {
                if (!CollectionUtils.containsAny(filters.get(FileProvenanceParam.experiment.toString()), f.getExperimentNames())) {
                    continue;
                }
            }
            if (filters.containsKey(FileProvenanceParam.sample.toString())) {
                if (!CollectionUtils.containsAny(filters.get(FileProvenanceParam.sample.toString()), f.getSampleNames())) {
                    continue;
                }
            }
            if (filters.containsKey(FileProvenanceParam.root_sample.toString())) {
                if (!CollectionUtils.containsAny(filters.get(FileProvenanceParam.root_sample.toString()), f.getRootSampleNames())) {
                    continue;
                }
            }
            if (filters.containsKey(FileProvenanceParam.lane.toString())) {
                if (!CollectionUtils.containsAny(filters.get(FileProvenanceParam.lane.toString()), f.getLaneNames())) {
                    continue;
                }
            }
            if (filters.containsKey(FileProvenanceParam.sequencer_run.toString())) {
                if (!CollectionUtils.containsAny(filters.get(FileProvenanceParam.sequencer_run.toString()), f.getSequencerRunNames())) {
                    continue;
                }
            }
            if (filters.containsKey(FileProvenanceParam.workflow.toString())) {
                if (!filters.get(FileProvenanceParam.workflow.toString()).contains(f.getWorkflowSWID().toString())) {
                    continue;
                }
            }
            if (filters.containsKey(FileProvenanceParam.workflow_run.toString())) {
                if (!filters.get(FileProvenanceParam.workflow_run.toString()).contains(f.getWorkflowRunSWID().toString())) {
                    continue;
                }
            }
            if (filters.containsKey(FileProvenanceParam.skip.toString())) {
                if (!filters.get(FileProvenanceParam.skip.toString()).contains(f.getSkip())) {
                    continue;
                }
            }
            fps.add(f);
        }
        return fps;
    }

}
