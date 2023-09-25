package ca.on.oicr.gsi.provenance.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;

/** @author mlaszloffy */
public interface SampleProvenance extends LimsProvenance {

  String getStudyTitle();

  SortedMap<String, SortedSet<String>> getStudyAttributes();

  String getRootSampleName();

  String getParentSampleName();

  String getSampleName();

  SortedMap<String, SortedSet<String>> getSampleAttributes();

  String getSequencerRunName();

  SortedMap<String, SortedSet<String>> getSequencerRunAttributes();

  String getSequencerRunPlatformModel();

  String getLaneNumber();

  SortedMap<String, SortedSet<String>> getLaneAttributes();

  String getIusTag();

  Boolean getSkip();

  String getSampleProvenanceId();

  ZonedDateTime getCreatedDate();

  List<String> getBatchIds();
}
