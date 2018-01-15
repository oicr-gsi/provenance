package ca.on.oicr.gsi.provenance.model;

import java.time.ZonedDateTime;

/**
 *
 * @author mlaszloffy
 */
public interface LimsKey {

    public String getProvider();

    public String getId();

    public String getVersion();

    public ZonedDateTime getLastModified();

}
