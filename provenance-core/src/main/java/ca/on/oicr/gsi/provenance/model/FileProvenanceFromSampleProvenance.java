package ca.on.oicr.gsi.provenance.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
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
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getStudyTitle() != null) {
                s.add(sp.getStudyTitle());
            }
        }
        return s;
    }

    @Override
    public SortedMap<String, SortedSet<String>> getStudyAttributes() {
        SortedMap<String, SortedSet<String>> attrs = new TreeMap<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSampleAttributes() != null) {
                attrs.putAll(sp.getStudyAttributes());
            }
        }
        return attrs;
    }

    @Override
    public Collection<String> getRootSampleNames() {
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getRootSampleName() != null) {
                s.add(sp.getRootSampleName());
            }
        }
        return s;
    }

    @Override
    public Collection<String> getParentSampleNames() {
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getParentSampleName() != null) {
                s.add(sp.getParentSampleName());
            }
        }
        return s;
    }

    @Override
    public Collection<String> getParentSampleOrganismIDs() {
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            //s.addAll(sp.getParentSampleOrganismIDs());
        }
        return s;
    }

    @Override
    @Deprecated
    public SortedMap<String, SortedSet<String>> getParentSampleAttributes() {
        SortedMap<String, SortedSet<String>> attrs = new TreeMap<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSampleAttributes() != null) {
                attrs.putAll(sp.getSampleAttributes());
            }
        }
        return attrs;
    }

    @Override
    public Collection<String> getSampleNames() {
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSampleName() != null) {
                s.add(sp.getSampleName());
            }
        }
        return s;
    }

    @Override
    public Collection<String> getSampleOrganismIDs() {
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            //s.addAll(sp.getSampleOrganismIDs());
        }
        return s;
    }

    @Override
    public Collection<String> getSampleOrganismCodes() {
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            //s.addAll(sp.getSampleOrganismCodes());
        }
        return s;
    }

    @Override
    public SortedMap<String, SortedSet<String>> getSampleAttributes() {
        SortedMap<String, SortedSet<String>> attrs = new TreeMap<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSampleAttributes() != null) {
                attrs.putAll(sp.getSampleAttributes());
            }
        }
        return attrs;
    }

    @Override
    public Collection<String> getSequencerRunNames() {
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSequencerRunName() != null) {
                s.add(sp.getSequencerRunName());
            }
        }
        return s;
    }

    @Override
    public SortedMap<String, SortedSet<String>> getSequencerRunAttributes() {
        SortedMap<String, SortedSet<String>> attrs = new TreeMap<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSequencerRunAttributes() != null) {
                attrs.putAll(sp.getSequencerRunAttributes());
            }
        }
        return attrs;
    }

    @Override
    public Collection<String> getSequencerRunPlatformIDs() {
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            //s.addAll(sp.getSequencerRunPlatformIDs());
        }
        return s;
    }

    @Override
    public Collection<String> getSequencerRunPlatformNames() {
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSequencerRunPlatformModel() != null) {
                s.add(sp.getSequencerRunPlatformModel());
            }
        }
        return s;
    }

    @Override
    public Collection<String> getLaneNames() {
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getSequencerRunName() != null && sp.getLaneNumber() != null) {
                s.add(sp.getSequencerRunName() + "_lane_" + sp.getLaneNumber());
            }
        }
        return s;
    }

    @Override
    public Collection<String> getLaneNumbers() {
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getLaneNumber() != null) {
                s.add(sp.getLaneNumber());
            }
        }
        return s;
    }

    @Override
    public SortedMap<String, SortedSet<String>> getLaneAttributes() {
        SortedMap<String, SortedSet<String>> attrs = new TreeMap<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getLaneAttributes() != null) {
                attrs.putAll(sp.getLaneAttributes());
            }
        }
        return attrs;
    }

    @Override
    public Collection<String> getIusTags() {
        SortedSet<String> s = new TreeSet<>();
        for (SampleProvenance sp : sampleProvenances) {
            if (sp.getIusTag() != null) {
                if (sp.getIusTag().isEmpty()) {
                    //added for backwards compatibility
                    s.add("NoIndex");
                } else {
                    s.add(sp.getIusTag());
                }
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
