package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.AnalysisProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenance.Status;
import ca.on.oicr.gsi.provenance.model.FileProvenanceFromAnalysisProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenanceFromLaneProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenanceFromSampleProvenance;
import ca.on.oicr.gsi.provenance.model.IusLimsKey;
import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.gsi.provenance.model.LimsKey;
import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author mlaszloffy
 */
public class DefaultProvenanceClient implements ExtendedProvenanceClient {

    protected final HashMap<String, AnalysisProvenanceProvider> analysisProvenanceProviders;
    protected final HashMap<String, SampleProvenanceProvider> sampleProvenanceProviders;
    protected final HashMap<String, LaneProvenanceProvider> laneProvenanceProviders;
    private final Logger log = LogManager.getLogger(DefaultProvenanceClient.class);

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

    @Override
    public Map<String, Collection<SampleProvenance>> getSampleProvenanceByProvider(Map<FileProvenanceFilter, Set<String>> filters) {
        Map<String, Collection<SampleProvenance>> spsByProvider = new HashMap<>();
        for (Entry<String, SampleProvenanceProvider> e : sampleProvenanceProviders.entrySet()) {
            String provider = e.getKey();
            SampleProvenanceProvider spp = e.getValue();

            Stopwatch sw = Stopwatch.createStarted();
            log.info("Provider = [{}] started getSampleProvenance()", provider);
            Collection<SampleProvenance> sps;
            if (filters == null || filters.isEmpty()) {
                sps = spp.getSampleProvenance();
            } else {
                sps = spp.getSampleProvenance(filters);
            }
            log.info("Provider = [{}] completed getSampleProvenance() in {}", provider, sw.toString());

            spsByProvider.put(provider, sps);
        }
        return spsByProvider;
    }

    @Override
    public Map<String, Map<String, SampleProvenance>> getSampleProvenanceByProviderAndId(Map<FileProvenanceFilter, Set<String>> filters) {
        Map<String, Map<String, SampleProvenance>> spsByProviderAndId = new HashMap<>();

        for (Entry<String, Collection<SampleProvenance>> e : getSampleProvenanceByProvider(filters).entrySet()) {
            String provider = e.getKey();
            Map<String, SampleProvenance> spsById = new HashMap<>();
            for (SampleProvenance sp : e.getValue()) {
                if (spsById.put(sp.getProvenanceId(), sp) != null) {
                    throw new RuntimeException("Duplicate sample provenance ID = [" + sp.getProvenanceId() + "] from provider = [" + provider + "]");
                }
            }

            spsByProviderAndId.put(provider, spsById);
        }

        return spsByProviderAndId;
    }

    @Override
    public Map<String, Collection<LaneProvenance>> getLaneProvenanceByProvider(Map<FileProvenanceFilter, Set<String>> filters) {
        Map<String, Collection<LaneProvenance>> lpsByProvider = new HashMap<>();
        for (Entry<String, LaneProvenanceProvider> e : laneProvenanceProviders.entrySet()) {
            String provider = e.getKey();
            LaneProvenanceProvider lpp = e.getValue();

            Stopwatch sw = Stopwatch.createStarted();
            log.info("Provider = [{}] started getLaneProvenance()", provider);
            Collection<LaneProvenance> lps;
            if (filters == null || filters.isEmpty()) {
                lps = lpp.getLaneProvenance();
            } else {
                //not supported currently, lps will be filtered upstream
                //lps = lpp.getLaneProvenance(filters);
                lps = lpp.getLaneProvenance();
            }
            log.info("Provider = [{}] completed getLaneProvenance() in {}", provider, sw.toString());

            lpsByProvider.put(provider, lps);
        }

        return lpsByProvider;
    }

    @Override
    public Map<String, Map<String, LaneProvenance>> getLaneProvenanceByProviderAndId(Map<FileProvenanceFilter, Set<String>> filters) {
        Map<String, Map<String, LaneProvenance>> lpsByProviderAndId = new HashMap<>();
        for (Entry<String, Collection<LaneProvenance>> e : getLaneProvenanceByProvider(filters).entrySet()) {
            String provider = e.getKey();

            Map<String, LaneProvenance> lpsById = new HashMap<>();
            for (LaneProvenance lp : e.getValue()) {
                if (lpsById.put(lp.getProvenanceId(), lp) != null) {
                    throw new RuntimeException("Duplicate lane provenance ID = [" + lp.getProvenanceId() + "] from provider = [" + provider + "]");
                }
            }

            lpsByProviderAndId.put(provider, lpsById);
        }
        return lpsByProviderAndId;
    }

    @Override
    public Map<String, Collection<AnalysisProvenance>> getAnalysisProvenanceByProvider(Map<FileProvenanceFilter, Set<String>> filters) {
        Map<String, Collection<AnalysisProvenance>> apsByProvider = new HashMap<>();
        for (Entry<String, AnalysisProvenanceProvider> e : analysisProvenanceProviders.entrySet()) {
            String provider = e.getKey();
            AnalysisProvenanceProvider app = e.getValue();

            Stopwatch sw = Stopwatch.createStarted();
            log.info("Provider = [{}] started getAnalysisProvenance()", provider);
            Collection<AnalysisProvenance> aps;
            if (filters == null || filters.isEmpty()) {
                aps = app.getAnalysisProvenance();
            } else {
                aps = app.getAnalysisProvenance(filters);
            }
            log.info("Provider = [{}] completed getAnalysisProvenance() in {}", provider, sw.toString());

            apsByProvider.put(provider, aps);
        }
        return apsByProvider;
    }

    @Override
    public Collection<SampleProvenance> getSampleProvenance() {
        List<SampleProvenance> sps = new ArrayList<>();
        for (Map<String, SampleProvenance> e : getSampleProvenanceByProviderAndId(null).values()) {
            sps.addAll(e.values());
        }
        return sps;
    }

    @Override
    public Collection<SampleProvenance> getSampleProvenance(Map<FileProvenanceFilter, Set<String>> filters) {
        List<SampleProvenance> sps = new ArrayList<>();
        for (Map<String, SampleProvenance> e : getSampleProvenanceByProviderAndId(filters).values()) {
            sps.addAll(e.values());
        }
        return sps;
    }

    @Override
    public Collection<LaneProvenance> getLaneProvenance() {
        List<LaneProvenance> lps = new ArrayList<>();
        for (Map<String, LaneProvenance> e : getLaneProvenanceByProviderAndId(null).values()) {
            lps.addAll(e.values());
        }
        return lps;
    }

    @Override
    public Collection<LaneProvenance> getLaneProvenance(Map<FileProvenanceFilter, Set<String>> filters) {
        List<LaneProvenance> lps = new ArrayList<>();
        for (Map<String, LaneProvenance> e : getLaneProvenanceByProviderAndId(filters).values()) {
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

    public Collection<AnalysisProvenance> getAnalysisProvenance(Map<FileProvenanceFilter, Set<String>> filters) {
        List<AnalysisProvenance> aps = new ArrayList<>();
        for (Collection<AnalysisProvenance> e : getAnalysisProvenanceByProvider(filters).values()) {
            aps.addAll(e);
        }
        return aps;
    }

    @Override
    public Collection<FileProvenance> getFileProvenance() {
        return getFileProvenance(
                getSampleProvenanceByProviderAndId(Collections.EMPTY_MAP),
                getLaneProvenanceByProviderAndId(Collections.EMPTY_MAP),
                getAnalysisProvenanceByProvider(Collections.EMPTY_MAP)
        );
    }

    public Collection<FileProvenance> getFileProvenance(
            Map<String, Map<String, SampleProvenance>> sampleProvenanceByProvider,
            Map<String, Map<String, LaneProvenance>> laneProvenanceByProvider,
            Map<String, Collection<AnalysisProvenance>> analysisProvenanceByProvider
    ) {

        Joiner j = Joiner.on(",");

        //build file provenance
        List<FileProvenance> fps = new ArrayList<>();
        for (Entry<String, Collection<AnalysisProvenance>> e : analysisProvenanceByProvider.entrySet()) {

            for (AnalysisProvenance ap : e.getValue()) {
                List<FileProvenanceFromAnalysisProvenance> tmp = new ArrayList<>();
                boolean isSkipped = false;
                Status status = Status.OKAY;
                Set<StatusReason> statusReasons = EnumSet.noneOf(StatusReason.class);

                for (IusLimsKey ik : ap.getIusLimsKeys()) {

                    //get lims key
                    LimsKey limsKey = ik.getLimsKey();
                    String limsKeyId = null;
                    String limsKeyProvider = null;
                    String limsKeyVersion = null;
                    DateTime limsKeyLastModified = null;
                    if (limsKey == null) {
                        status = Status.ERROR;
                        statusReasons.add(StatusReason.LIMS_KEY_MISSING);
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

                    FileProvenanceFromAnalysisProvenance b;
                    if ((sp == null && lp == null)) {
                        //check that AP object joins either one SP or one LP object
                        status = Status.ERROR;
                        statusReasons.add(StatusReason.PROVENANCE_MISSING);
                        b = new FileProvenanceFromAnalysisProvenance();
                        b.setAnalysisProvenance(ap);
                    } else if (sp != null && lp != null) {
                        status = Status.ERROR;
                        statusReasons.add(StatusReason.ID_CONFLICT);
                        b = new FileProvenanceFromAnalysisProvenance();
                        b.setAnalysisProvenance(ap);
                    } else if (sp != null) {
                        //check SP version and last modified matches what is set in LimsKey
                        if (sp.getVersion() == null || !sp.getVersion().equals(limsKeyVersion)) {
                            status = Status.STALE;
                            statusReasons.add(StatusReason.PROVENANCE_VERSION_MISMATCH);
                        }
                        if (sp.getLastModified() == null || !sp.getLastModified().equals(limsKeyLastModified)) {
                            status = Status.STALE;
                            statusReasons.add(StatusReason.PROVENANCE_LAST_MODIFIED_MISMATCH);
                        }
                        FileProvenanceFromSampleProvenance fpSp = new FileProvenanceFromSampleProvenance();
                        fpSp.sampleProvenance(sp);
                        fpSp.setAnalysisProvenance(ap);
                        b = fpSp;

                        if (Boolean.TRUE.equals(sp.getSkip())
                                || sp.getLaneAttributes().containsKey("skip")
                                || sp.getSequencerRunAttributes().containsKey("skip")
                                || sp.getSampleAttributes().containsKey("skip")
                                || sp.getStudyAttributes().containsKey("skip")) {
                            isSkipped = true;
                        }
                    } else if (lp != null) {
                        //check LP version and last modified matches what is set in LimsKey
                        if (lp.getVersion() == null || !lp.getVersion().equals(limsKeyVersion)) {
                            status = Status.STALE;
                            statusReasons.add(StatusReason.PROVENANCE_VERSION_MISMATCH);
                        }
                        if (lp.getLastModified() == null || !lp.getLastModified().equals(limsKeyLastModified)) {
                            status = Status.STALE;
                            statusReasons.add(StatusReason.PROVENANCE_LAST_MODIFIED_MISMATCH);
                        }

                        FileProvenanceFromLaneProvenance fpLp = new FileProvenanceFromLaneProvenance();
                        fpLp.laneProvenance(lp);
                        fpLp.setAnalysisProvenance(ap);
                        b = fpLp;

                        if (Boolean.TRUE.equals(lp.getSkip())
                                || lp.getLaneAttributes().containsKey("skip")
                                || lp.getSequencerRunAttributes().containsKey("skip")) {
                            isSkipped = true;
                        }
                    } else {
                        status = Status.ERROR;
                        statusReasons.add(StatusReason.PROVENANCE_CLIENT_INTERNAL_ERROR);
                        b = new FileProvenanceFromAnalysisProvenance();
                        b.setAnalysisProvenance(ap);
                    }

                    if (Boolean.TRUE.equals(ap.getSkip())
                            || ap.getFileAttributes().containsKey("skip")
                            || ap.getWorkflowRunAttributes().containsKey("skip")) {
                        isSkipped = true;
                    }

                    b.setStatusReason(j.join(statusReasons));
                    b.setIusLimsKeys(Arrays.asList(ik));
                    tmp.add(b);
                }

                //file provenance objects share the following attributes:
                // - Skip
                // - Status
                for (FileProvenanceFromAnalysisProvenance b : tmp) {
                    b.setSkip(isSkipped);
                    b.setStatus(status);
                    fps.add(b);
                }
            }
        }

        return fps;
    }

    @Override
    public Collection<FileProvenance> getFileProvenance(Map<FileProvenanceFilter, Set<String>> filters) {
        Collection<FileProvenance> fps = getFileProvenance(
                //get all sample and lane provenance and filter after joining with AP
                //if this is not done, there could be AP records that pass filters and do not have an associated SP or LP
                //for example: AP record linked to SP(A) and SP(B), filtering on A would result in FP(AP+SP(B))=ERROR
                //getSampleProvenanceByProviderAndId(filters),
                //getLaneProvenanceByProviderAndId(filters),
                getSampleProvenanceByProviderAndId(Collections.EMPTY_MAP),
                getLaneProvenanceByProviderAndId(Collections.EMPTY_MAP),
                getAnalysisProvenanceByProvider(filters)
        );

        return applyFileProvenanceFilters(fps, filters);
    }

    @Override
    public Collection<FileProvenance> getFileProvenance(Map<FileProvenanceFilter, Set<String>> includeFilters, Map<FileProvenanceFilter, Set<String>> excludeFilters) {
        Collection<FileProvenance> fps = getFileProvenance(includeFilters);
        return applyFileProvenanceFilters(Operation.EXCLUDE, fps, excludeFilters);
    }

    protected Collection<FileProvenance> applyFileProvenanceFilters(Collection<FileProvenance> fps, final Map<FileProvenanceFilter, Set<String>> filters) {
        return applyFileProvenanceFilters(Operation.INCLUDE, fps, filters);
    }

    public enum Operation {
        INCLUDE, EXCLUDE;
    }

    protected Collection<FileProvenance> applyFileProvenanceFilters(Operation op, Collection<FileProvenance> fps, final Map<FileProvenanceFilter, Set<String>> filters) {

        List<Predicate<FileProvenance>> filterPredicates = new ArrayList<>();
        for (FileProvenanceFilter fpf : FileProvenanceFilter.values()) {
            switch (fpf) {
                case file:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return vals.contains(Objects.toString(f.getFileSWID()));
                            }
                        });
                    }
                    break;
                case file_meta_type:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return vals.contains(f.getFileMetaType());
                            }
                        });
                    }
                    break;
                case ius:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return CollectionUtils.containsAny(vals, Sets.newHashSet(f.getIusSWIDs()));
                            }
                        });
                    }
                    break;
                case lane:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return CollectionUtils.containsAny(vals, f.getLaneNames());
                            }
                        });
                    }
                    break;
                case processing:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return vals.contains(Objects.toString(f.getProcessingSWID()));
                            }
                        });
                    }
                    break;
                case processing_status:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return vals.contains(f.getProcessingStatus());
                            }
                        });
                    }
                    break;
                case root_sample:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return CollectionUtils.containsAny(vals, f.getRootSampleNames());
                            }
                        });
                    }
                    break;
                case sample:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return CollectionUtils.containsAny(vals, f.getSampleNames());
                            }
                        });
                    }
                    break;
                case sequencer_run:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return CollectionUtils.containsAny(vals, f.getSequencerRunNames());
                            }
                        });
                    }
                    break;
                case sequencer_run_platform_model:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return CollectionUtils.containsAny(vals, f.getSequencerRunPlatformNames());
                            }
                        });
                    }
                    break;
                case skip:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return vals.contains(f.getSkip());
                            }
                        });
                    }
                    break;
                case study:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return CollectionUtils.containsAny(vals, f.getStudyTitles());
                            }
                        });
                    }
                    break;
                case workflow:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return vals.contains(Objects.toString(f.getWorkflowSWID()));
                            }
                        });
                    }
                    break;
                case workflow_run:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return vals.contains(Objects.toString(f.getWorkflowRunSWID()));
                            }
                        });
                    }
                    break;
                case workflow_run_status:
                    if (!CollectionUtils.isEmpty(filters.get(fpf))) {
                        final Set<String> vals = filters.get(fpf);
                        filterPredicates.add(new Predicate<FileProvenance>() {
                            @Override
                            public boolean apply(FileProvenance f) {
                                return vals.contains(f.getWorkflowRunStatus());
                            }
                        });
                    }
                    break;
                default:
                    throw new RuntimeException("Implement method for filter: " + fpf.name());
            }
        }

        if (null == op) {
            throw new RuntimeException("null filter operation");
        } else {
            switch (op) {
                case INCLUDE:
                    return Collections2.filter(fps, Predicates.and(filterPredicates));
                case EXCLUDE:
                    return Collections2.filter(fps, Predicates.not(Predicates.or(filterPredicates)));
                default:
                    throw new RuntimeException("Unsupported operation = " + op);
            }
        }
    }

    public enum StatusReason {
        LIMS_KEY_MISSING("Analysis provenance does not have a LimsKey reference"),
        PROVENANCE_MISSING("Unable to determine provenance record"),
        ID_CONFLICT("Multiple provenance records with the same id detected"),
        PROVENANCE_VERSION_MISMATCH("Provenance version mismatch"),
        PROVENANCE_LAST_MODIFIED_MISMATCH("Provenance last modified mismatch"),
        PROVENANCE_CLIENT_INTERNAL_ERROR("Internal error detected in provenance client");

        private final String description;
        private static final Map<String, StatusReason> descriptionToEnum = new HashMap<>();

        static {
            for (StatusReason sr : StatusReason.values()) {
                if (descriptionToEnum.put(sr.toString(), sr) != null) {
                    throw new RuntimeException("Duplicate StatusReason description");
                };
            }
        }

        StatusReason(String description) {
            this.description = description;
        }

        public static StatusReason fromDescription(String description) {
            return (descriptionToEnum.get(description));
        }

        @Override
        public String toString() {
            return description;
        }
    }

}
