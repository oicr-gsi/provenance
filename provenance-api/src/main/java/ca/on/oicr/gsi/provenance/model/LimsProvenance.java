package ca.on.oicr.gsi.provenance.model;

import java.time.ZonedDateTime;

/** @author mlaszloffy */
public interface LimsProvenance {

  public String getProvenanceId();

  public String getVersion();

  public ZonedDateTime getLastModified();
}
