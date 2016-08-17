package ca.on.oicr.gsi.provenance.model;

import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
public interface LimsProvenance {

    public String getProvenanceId();

    public String getVersion();

    public DateTime getLastModified();
}
