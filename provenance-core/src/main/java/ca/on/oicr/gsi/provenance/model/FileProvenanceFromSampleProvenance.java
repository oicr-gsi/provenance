package ca.on.oicr.gsi.provenance.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author mlaszloffy
 */
public class FileProvenanceFromSampleProvenance extends FileProvenanceFromAnalysisProvenance {

    private Collection<SampleProvenance> sampleProvenances;

    public FileProvenanceFromSampleProvenance() {
        sampleProvenances = new HashSet<>();
    }

    public void sampleProvenance(SampleProvenance sampleProvenance) {
        this.sampleProvenances.add(sampleProvenance);
    }

    public void setSampleProvenances(Collection<SampleProvenance> sampleProvenances) {
        this.sampleProvenances = sampleProvenances;
    }

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
            if (sp.getSequencerRunAttributes() != null) {
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
            if (sp.getSequencerRunName() != null && sp.getLaneNumber() != null) {
                s.add(sp.getSequencerRunName() + "_lane_" + sp.getLaneNumber());
            }
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
    public Collection<String> getIusTags() {
        Set<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getIusTag() != null) {
                s.add(sp.getIusTag());
            }
        }

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
//        for (SampleProvenance sampleProvenance : sampleProvenances) {
//            lastModified = ObjectUtils.max(lastModified, sampleProvenance.getLastModified());
//        }
//
//        lastModified = ObjectUtils.max(lastModified, analysisProvenance.getLastModified());
//
//        if (lastModified == null) {
//            return null;
//        } else {
//            return lastModified.toDateTime(DateTimeZone.UTC).toString();
//        }
//    }
}
