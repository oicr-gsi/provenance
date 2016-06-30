package ca.on.oicr.gsi.provenance.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author mlaszloffy
 */
public class FileProvenanceFromLaneProvenance extends FileProvenanceFromAnalysisProvenance {

    private Collection<LaneProvenance> laneProvenances;

    public FileProvenanceFromLaneProvenance() {
        laneProvenances = new HashSet<>();
    }

    public void laneProvenance(LaneProvenance laneProvenance) {
        this.laneProvenances.add(laneProvenance);
    }

    public void setLaneProvenances(Collection<LaneProvenance> laneProvenances) {
        this.laneProvenances = laneProvenances;
    }

    @Override
    public Collection<String> getStudyTitles() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Map<String, Set<String>> getStudyAttributes() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public Collection<String> getExperimentNames() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Map<String, Set<String>> getExperimentAttributes() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public Collection<String> getRootSampleNames() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Collection<String> getParentSampleNames() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Collection<String> getParentSampleOrganismIDs() {
        return Collections.EMPTY_SET;
    }

    @Override
    @Deprecated
    public Map<String, Set<String>> getParentSampleAttributes() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public Collection<String> getSampleNames() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Collection<String> getSampleOrganismIDs() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Collection<String> getSampleOrganismCodes() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Map<String, Set<String>> getSampleAttributes() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public Collection<String> getSequencerRunNames() {
        Set<String> s = new TreeSet<>();
        for (LaneProvenance lp : laneProvenances) {
            if (lp.getSequencerRunName() != null) {
                s.add(lp.getSequencerRunName());
            }
        }
        return s;
    }

    @Override
    public Map<String, Set<String>> getSequencerRunAttributes() {
        Map<String, Set<String>> attrs = new HashMap<>();
        for (LaneProvenance lp : laneProvenances) {
            if (lp.getSequencerRunAttributes() != null) {
                attrs.putAll(lp.getSequencerRunAttributes());
            }
        }
        return attrs;
    }

    @Override
    public Collection<String> getSequencerRunPlatformIDs() {
        Set<String> s = new TreeSet<>();
        for (LaneProvenance lp : laneProvenances) {
            //s.addAll(lp.getSequencerRunPlatformIDs());
        }
        return s;
    }

    @Override
    public Collection<String> getSequencerRunPlatformNames() {
        Set<String> s = new TreeSet<>();
        for (LaneProvenance lp : laneProvenances) {
            if (lp.getSequencerRunPlatformModel() != null) {
                s.add(lp.getSequencerRunPlatformModel());
            }
        }
        return s;
    }

    @Override
    public Collection<String> getLaneNames() {
        Set<String> s = new TreeSet<>();
        for (LaneProvenance lp : laneProvenances) {
            if (lp.getSequencerRunName() != null && lp.getLaneNumber() != null) {
                s.add(lp.getSequencerRunName() + "_lane_" + lp.getLaneNumber());
            }
        }
        return s;
    }

    @Override
    public Collection<String> getLaneNumbers() {
        Set<String> s = new TreeSet<>();
        for (LaneProvenance lp : laneProvenances) {
            if (lp.getLaneNumber() != null) {
                s.add(lp.getLaneNumber());
            }
        }
        return s;
    }

    @Override
    public Map<String, Set<String>> getLaneAttributes() {
        Map<String, Set<String>> attrs = new HashMap<>();
        for (LaneProvenance lp : laneProvenances) {
            if (lp.getLaneAttributes() != null) {
                attrs.putAll(lp.getLaneAttributes());
            }
        }
        return attrs;
    }

    @Override
    public Collection<String> getIusTags() {
        Set<String> s = new TreeSet<>();

        //added for backwards compatibility
        if (s.isEmpty()) {
            s.add("NoIndex");
        }

        return s;
    }

//    @Override
//    public String getLastModified() {
//        DateTime lastModified = null;
//
//        for (LaneProvenance laneProvenance : laneProvenances) {
//            lastModified = ObjectUtils.max(lastModified, laneProvenance.getLastModified());
//        }
//
//        lastModified = ObjectUtils.max(lastModified, super.getLastModified());
//
//        if (lastModified == null) {
//            return null;
//        } else {
//            return lastModified.toDateTime(DateTimeZone.UTC).toString();
//        }
//    }
}
