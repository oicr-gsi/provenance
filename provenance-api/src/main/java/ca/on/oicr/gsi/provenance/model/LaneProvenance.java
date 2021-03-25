package ca.on.oicr.gsi.provenance.model;

import java.time.ZonedDateTime;
import java.util.SortedMap;
import java.util.SortedSet;

/** @author mlaszloffy */
public interface LaneProvenance extends LimsProvenance {

  String getSequencerRunName();

  SortedMap<String, SortedSet<String>> getSequencerRunAttributes();

  String getSequencerRunPlatformModel();

  String getLaneNumber();

  SortedMap<String, SortedSet<String>> getLaneAttributes();

  Boolean getSkip();

  String getLaneProvenanceId();

  ZonedDateTime getCreatedDate();
}
