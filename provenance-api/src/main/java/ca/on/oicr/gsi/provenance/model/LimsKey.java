package ca.on.oicr.gsi.provenance.model;

import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
public interface LimsKey {

    public String getProvider();

    public String getId();

    public String getVersion();

    public DateTime getLastModified();

}
