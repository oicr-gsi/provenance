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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
public class DefaultProvenanceClient implements ProvenanceClient {

    private final HashMap<String, AnalysisProvenanceProvider> analysisProvenanceProviders;
    private final HashMap<String, SampleProvenanceProvider> sampleProvenanceProviders;
    private final HashMap<String, LaneProvenanceProvider> laneProvenanceProviders;

    public DefaultProvenanceClient() {
        analysisProvenanceProviders = new HashMap<>();
        sampleProvenanceProviders = new HashMap<>();
        laneProvenanceProviders = new HashMap<>();
    }

    public AnalysisProvenanceProvider registerAnalysisProvenanceProvider(String provider, AnalysisProvenanceProvider app) {
        return analysisProvenanceProviders.put(provider, app);
    }

    public LaneProvenanceProvider registerLaneProvenanceProvider(String provider, LaneProvenanceProvider lpp) {
        return laneProvenanceProviders.put(provider, lpp);
    }

    public SampleProvenanceProvider registerSampleProvenanceProvider(String provider, SampleProvenanceProvider spp) {
        return sampleProvenanceProviders.put(provider, spp);
    }

    public Map<String, Map<String, SampleProvenance>> getSampleProvenanceByProvider(Map<String, Set<String>> filters) {
        Map<String, Map<String, SampleProvenance>> spsByProvider = new HashMap<>();
        for (Entry<String, SampleProvenanceProvider> e : sampleProvenanceProviders.entrySet()) {
            String provider = e.getKey();
            SampleProvenanceProvider spp = e.getValue();

            Collection<SampleProvenance> sps;
            if (filters == null || filters.isEmpty()) {
                sps = spp.getSampleProvenance();
            } else {
                sps = spp.getSampleProvenance(filters);
            }

            Map<String, SampleProvenance> spsById = new HashMap<>();
            for (SampleProvenance sp : sps) {
                if (spsById.put(sp.getSampleProvenanceId(), sp) != null) {
                    throw new RuntimeException("Duplicate sample provenance ID = [" + sp.getSampleProvenanceId() + "] from provider = [" + provider + "]");
                }
            }

            spsByProvider.put(provider, spsById);
        }
        return spsByProvider;
    }

    public Map<String, Map<String, LaneProvenance>> getLaneProvenanceByProvider(Map<String, Set<String>> filters) {
        Map<String, Map<String, LaneProvenance>> lpsByProvider = new HashMap<>();
        for (Entry<String, LaneProvenanceProvider> e : laneProvenanceProviders.entrySet()) {
            String provider = e.getKey();
            LaneProvenanceProvider lpp = e.getValue();

            Collection<LaneProvenance> lps;
            if (filters == null || filters.isEmpty()) {
                lps = lpp.getLaneProvenance();
            } else {
                lps = lpp.getLaneProvenance(filters);
            }

            Map<String, LaneProvenance> lpsById = new HashMap<>();
            for (LaneProvenance lp : lps) {
                if (lpsById.put(lp.getLaneProvenanceId(), lp) != null) {
                    throw new RuntimeException("Duplicate lane provenance ID = [" + lp.getLaneProvenanceId() + "] from provider = [" + provider + "]");
                }
            }

            lpsByProvider.put(provider, lpsById);
        }
        return lpsByProvider;
    }

    public Map<String, Collection<AnalysisProvenance>> getAnalysisProvenanceByProvider(Map<String, Set<String>> filters) {
        Map<String, Collection<AnalysisProvenance>> apsByProvider = new HashMap<>();
        for (Entry<String, AnalysisProvenanceProvider> e : analysisProvenanceProviders.entrySet()) {
            String provider = e.getKey();
            AnalysisProvenanceProvider app = e.getValue();

            Collection<AnalysisProvenance> aps;
            if (filters == null || filters.isEmpty()) {
                aps = app.getAnalysisProvenance();
            } else {
                aps = app.getAnalysisProvenance(filters);
            }

            apsByProvider.put(provider, aps);
        }
        return apsByProvider;
    }

    @Override
    public Collection<SampleProvenance> getSampleProvenance() {
        List<SampleProvenance> sps = new ArrayList<>();
        for (Map<String, SampleProvenance> e : getSampleProvenanceByProvider(null).values()) {
            sps.addAll(e.values());
        }
        return sps;
    }

    @Override
    public Collection<SampleProvenance> getSampleProvenance(Map<String, Set<String>> filters) {
        List<SampleProvenance> sps = new ArrayList<>();
        for (Map<String, SampleProvenance> e : getSampleProvenanceByProvider(filters).values()) {
            sps.addAll(e.values());
        }
        return sps;
    }

    @Override
    public Collection<LaneProvenance> getLaneProvenance() {
        List<LaneProvenance> lps = new ArrayList<>();
        for (Map<String, LaneProvenance> e : getLaneProvenanceByProvider(null).values()) {
            lps.addAll(e.values());
        }
        return lps;
    }

    @Override
    public Collection<LaneProvenance> getLaneProvenance(Map<String, Set<String>> filters) {
        List<LaneProvenance> lps = new ArrayList<>();
        for (Map<String, LaneProvenance> e : getLaneProvenanceByProvider(filters).values()) {
            lps.addAll(e.values());
        }
        return lps;
    }

    public Collection<AnalysisProvenance> getAnalysisProvenance() {
        List<AnalysisProvenance> aps = new ArrayList<>();
        for (Collection<AnalysisProvenance> e : getAnalysisProvenanceByProvider(null).values()) {
            aps.addAll(e);
        }
        return aps;
    }

    public Collection<AnalysisProvenance> getAnalysisProvenance(Map<String, Set<String>> filters) {
        List<AnalysisProvenance> aps = new ArrayList<>();
        for (Collection<AnalysisProvenance> e : getAnalysisProvenanceByProvider(filters).values()) {
            aps.addAll(e);
        }
        return aps;
    }

    @Override
    public Collection<FileProvenance> getFileProvenance() {
        return getFileProvenance(
                getSampleProvenanceByProvider(Collections.EMPTY_MAP),
                getLaneProvenanceByProvider(Collections.EMPTY_MAP),
                getAnalysisProvenanceByProvider(Collections.EMPTY_MAP)
        );
    }

    public Collection<FileProvenance> getFileProvenance(
            Map<String, Map<String, SampleProvenance>> sampleProvenanceByProvider,
            Map<String, Map<String, LaneProvenance>> laneProvenanceByProvider,
            Map<String, Collection<AnalysisProvenance>> analysisProvenanceByProvider
    ) {

        //build file provenance
        List<FileProvenance> fps = new ArrayList<>();
        for (Entry<String, Collection<AnalysisProvenance>> e : analysisProvenanceByProvider.entrySet()) {

            for (AnalysisProvenance ap : e.getValue()) {
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

                    //get lims provenance objects
                    SampleProvenance sp = null;
                    LaneProvenance lp = null;
                    if (sampleProvenanceByProvider.containsKey(limsKeyProvider)) {
                        sp = sampleProvenanceByProvider.get(limsKeyProvider).get(limsKeyId);
                    }
                    if (laneProvenanceByProvider.containsKey(limsKeyProvider)) {
                        lp = laneProvenanceByProvider.get(limsKeyProvider).get(limsKeyId);
                    }
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

                //if any of the analysis provenance lims objects are marked "skip", then all should be marked as skipped
                for (FileProvenanceFromAnalysisProvenance b : tmp) {
                    if (skip) {
                        b.setSkip(true);
                    }

                    fps.add(b);
                }
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
