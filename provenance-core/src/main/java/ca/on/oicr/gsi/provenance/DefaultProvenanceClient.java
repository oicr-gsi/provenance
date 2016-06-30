package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.AnalysisProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenanceFromAnalysisProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenanceFromLaneProvenance;
import ca.on.oicr.gsi.provenance.model.IusLimsKey;
import ca.on.oicr.gsi.provenance.model.LimsKey;
import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenanceFromSampleProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenanceParam;
import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
public class DefaultProvenanceClient implements ProvenanceClient {

    private final AnalysisProvenanceProvider analysisProvenanceProvider;
    private final SampleProvenanceProvider sampleProvenanceProvider;
    private final LaneProvenanceProvider laneProvenanceProvider;

    public DefaultProvenanceClient(AnalysisProvenanceProvider analyisProvenanceProvider,
            SampleProvenanceProvider sampleProvenanceProvider,
            LaneProvenanceProvider laneProvenanceProvider) {
        this.analysisProvenanceProvider = analyisProvenanceProvider;
        this.sampleProvenanceProvider = sampleProvenanceProvider;
        this.laneProvenanceProvider = laneProvenanceProvider;
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
    public Collection<LaneProvenance> getLaneProvenance() {
        return laneProvenanceProvider.getLaneProvenance();
    }

    @Override
    public Collection<LaneProvenance> getLaneProvenance(Map<String, Set<String>> filters) {
        return laneProvenanceProvider.getLaneProvenance(filters);
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

        //build lane provenance lookup table
        Map<String, LaneProvenance> lps = new HashMap<>();
        for (LaneProvenance lp : this.getLaneProvenance()) {
            if (lps.put(lp.getLaneProvenanceId(), lp) != null) {
                throw new RuntimeException("Duplicate lane provenance ID = [" + lp.getLaneProvenanceId() + "]");
            }
        }

        //build file provenance
        List<FileProvenance> fps = new ArrayList<>();
        Collection<AnalysisProvenance> aps = analysisProvenanceProvider.getAnalysisProvenance();
        for (AnalysisProvenance ap : aps) {
            List<FileProvenanceFromAnalysisProvenance> tmp = new ArrayList<>();
            boolean skip = false;
            String status = "OKAY";

            for (IusLimsKey ik : ap.getIusLimsKeys()) {

                //get lims key
                LimsKey limsKey = ik.getLimsKey();
                String limsKeyId = null;
                String limsKeyProvider = null;
                String limsKeyVersion = null;
                DateTime limsKeyLastModified = null;
                if (limsKey == null) {
                    status = "ERROR";
                } else {
                    limsKeyId = limsKey.getId();
                    limsKeyProvider = limsKey.getProvider();
                    limsKeyVersion = limsKey.getVersion();
                    limsKeyLastModified = limsKey.getLastModified();
                }

                //get provenance objects
                SampleProvenance sp = sps.get(limsKeyId);
                LaneProvenance lp = lps.get(limsKeyId);
                if ((sp == null && lp == null) || (sp != null && lp != null)) {
                    status = "ERROR";
                }

                //check that the lims key version and last modified matches provenance object's version and last modified
                if (status.equals("ERROR")) {
                    //
                } else if (sp != null) {
                    if (sp.getVersion() == null || !sp.getVersion().equals(limsKeyVersion)) {
                        status = "ERROR";
                    }
                    if (sp.getLastModified() == null || !sp.getLastModified().equals(limsKeyLastModified)) {
                        status = "ERROR";
                    }
                } else if (lp != null) {
                    if (lp.getVersion() == null || !lp.getVersion().equals(limsKeyVersion)) {
                        status = "ERROR";
                    }
                    if (lp.getLastModified() == null || !lp.getLastModified().equals(limsKeyLastModified)) {
                        status = "ERROR";
                    }
                } else {
                    //
                }

                //instantiate file provenance for each lims key associated with the current analysis provenance object
                FileProvenanceFromAnalysisProvenance b;
                if ((sp == null && lp == null) || (sp != null && lp != null)) {
                    //unable to build fp
                    b = new FileProvenanceFromAnalysisProvenance();
                    b.setAnalysisProvenance(ap);
                } else if (sp != null) {
                    FileProvenanceFromSampleProvenance fpSp = new FileProvenanceFromSampleProvenance();
                    fpSp.sampleProvenance(sp);
                    fpSp.setAnalysisProvenance(ap);
                    b = fpSp;

                    if (Boolean.TRUE.equals(ap.getSkip())) {
                        skip = true;
                    } else if (Boolean.TRUE.equals(sp.getSkip())) {
                        skip = true;
                    } else if (ap.getFileAttributes().containsKey("skip")
                            || ap.getWorkflowRunAttributes().containsKey("skip")) {
                        skip = true;
                    } else if (sp.getLaneAttributes().containsKey("skip")
                            || sp.getSequencerRunAttributes().containsKey("skip")
                            || sp.getSampleAttributes().containsKey("skip")
                            || sp.getStudyAttributes().containsKey("skip")) {
                        skip = true;
                    } else {
                        //
                    }
                } else if (lp != null) {
                    FileProvenanceFromLaneProvenance fpLp = new FileProvenanceFromLaneProvenance();
                    fpLp.laneProvenance(lp);
                    fpLp.setAnalysisProvenance(ap);
                    b = fpLp;

                    if (Boolean.TRUE.equals(ap.getSkip())) {
                        skip = true;
                    } else if (ap.getFileAttributes().containsKey("skip")
                            || ap.getWorkflowRunAttributes().containsKey("skip")) {
                        skip = true;
                    } else if (Boolean.TRUE.equals(lp.getSkip())) {
                        skip = true;
                    } else if (lp.getLaneAttributes().containsKey("skip")
                            || lp.getSequencerRunAttributes().containsKey("skip")) {
                        skip = true;
                    } else {
                        //
                    }
                } else {
                    //catch all
                    status = "ERROR";
                    b = new FileProvenanceFromAnalysisProvenance();
                    b.setAnalysisProvenance(ap);
                }

                b.setStatus(status);

                tmp.add(b);
            }

            for (FileProvenanceFromAnalysisProvenance b : tmp) {
                if (skip) {
                    b.setSkip(true);
                }

                fps.add(b);
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
