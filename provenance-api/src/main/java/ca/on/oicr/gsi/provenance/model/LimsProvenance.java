package ca.on.oicr.gsi.provenance.model;

import java.time.ZonedDateTime;

/** @author mlaszloffy */
public interface LimsProvenance {

  String getProvenanceId();

  String getVersion();

  ZonedDateTime getLastModified();
}
