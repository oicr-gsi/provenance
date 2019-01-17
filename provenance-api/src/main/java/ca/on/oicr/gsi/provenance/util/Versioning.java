package ca.on.oicr.gsi.provenance.util;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/** @author mlaszloffy */
public class Versioning {

  public static String getSha256(SampleProvenance sp) {
    StringBuilder sb = new StringBuilder();
    sb.append(sp.getStudyTitle());
    sb.append(sp.getStudyAttributes());
    sb.append(sp.getRootSampleName());
    sb.append(sp.getParentSampleName());
    sb.append(sp.getSampleName());
    sb.append(sp.getSampleAttributes());
    sb.append(sp.getSequencerRunName());
    sb.append(sp.getSequencerRunAttributes());
    sb.append(sp.getSequencerRunPlatformModel());
    sb.append(sp.getLaneNumber());
    sb.append(sp.getLaneAttributes());
    sb.append(sp.getIusTag());
    String s = sb.toString();
    return Hashing.sha256().hashString(s, Charsets.UTF_8).toString();
  }

  public static String getSha256(LaneProvenance lp) {
    StringBuilder sb = new StringBuilder();
    sb.append(lp.getSequencerRunName());
    sb.append(lp.getSequencerRunAttributes());
    sb.append(lp.getSequencerRunPlatformModel());
    sb.append(lp.getLaneNumber());
    sb.append(lp.getLaneAttributes());
    String s = sb.toString();
    return Hashing.sha256().hashString(s, Charsets.UTF_8).toString();
  }
}
