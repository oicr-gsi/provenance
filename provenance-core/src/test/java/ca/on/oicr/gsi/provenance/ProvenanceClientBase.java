package ca.on.oicr.gsi.provenance;

import ca.on.oicr.gsi.provenance.model.AnalysisProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenance;
import ca.on.oicr.gsi.provenance.model.FileProvenance.Status;
import ca.on.oicr.gsi.provenance.model.IusLimsKey;
import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.gsi.provenance.model.LimsKey;
import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.joda.time.DateTime;
import static org.mockito.Matchers.anyMap;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author mlaszloffy
 */
public abstract class ProvenanceClientBase {

    private ProvenanceClient provenanceClient;
    private AnalysisProvenanceProvider app;
    private SampleProvenanceProvider spp;
    private LaneProvenanceProvider lpp;

    private SampleProvenance sp1;
    private SampleProvenance sp2;
    private LaneProvenance lp;

    private AnalysisProvenance ap1;
    private AnalysisProvenance ap2;
    private AnalysisProvenance ap3;
    private AnalysisProvenance ap4;

    private int expectedFpsSize;

    public abstract ProvenanceClient getProvenanceClient(
            Map<String, AnalysisProvenanceProvider> analysisProvenanceProviders,
            Map<String, SampleProvenanceProvider> sampleProvenanceProviders,
            Map<String, LaneProvenanceProvider> laneProvenanceProviders);

    @BeforeClass
    public void setup() {

        Map<String, AnalysisProvenanceProvider> apps = new HashMap<>();
        app = Mockito.mock(AnalysisProvenanceProvider.class);
        apps.put("test", app);

        Map<String, SampleProvenanceProvider> spps = new HashMap<>();
        spp = Mockito.mock(SampleProvenanceProvider.class);
        spps.put("test", spp);

        Map<String, LaneProvenanceProvider> lpps = new HashMap<>();
        lpp = Mockito.mock(LaneProvenanceProvider.class);
        lpps.put("test", lpp);

        provenanceClient = getProvenanceClient(apps, spps, lpps);

    }

    @BeforeMethod
    public void data() {
        String limsProvider = "test";
        DateTime limsLastModified = DateTime.now();

        sp1 = Mockito.mock(SampleProvenance.class);
        when(sp1.getProvenanceId()).thenReturn("1");
        when(sp1.getVersion()).thenReturn("v1");
        when(sp1.getLastModified()).thenReturn(limsLastModified);
        LimsKey limsKey1 = Mockito.mock(LimsKey.class);
        when(limsKey1.getId()).thenReturn("1");
        when(limsKey1.getVersion()).thenReturn("v1");
        when(limsKey1.getLastModified()).thenReturn(limsLastModified);
        when(limsKey1.getProvider()).thenReturn(limsProvider);
        IusLimsKey iusLimsKey1 = Mockito.mock(IusLimsKey.class);
        when(iusLimsKey1.getIusSWID()).thenReturn(1);
        when(iusLimsKey1.getLimsKey()).thenReturn(limsKey1);

        sp2 = Mockito.mock(SampleProvenance.class);
        when(sp2.getProvenanceId()).thenReturn("2");
        when(sp2.getVersion()).thenReturn("v1");
        when(sp2.getLastModified()).thenReturn(limsLastModified);
        LimsKey limsKey2 = Mockito.mock(LimsKey.class);
        when(limsKey2.getId()).thenReturn("2");
        when(limsKey2.getVersion()).thenReturn("v1");
        when(limsKey2.getLastModified()).thenReturn(limsLastModified);
        when(limsKey2.getProvider()).thenReturn(limsProvider);
        IusLimsKey iusLimsKey2 = Mockito.mock(IusLimsKey.class);
        when(iusLimsKey2.getIusSWID()).thenReturn(2);
        when(iusLimsKey2.getLimsKey()).thenReturn(limsKey2);

        lp = Mockito.mock(LaneProvenance.class);
        when(lp.getProvenanceId()).thenReturn("3");
        when(lp.getVersion()).thenReturn("v1");
        when(lp.getLastModified()).thenReturn(limsLastModified);
        LimsKey limsKey3 = Mockito.mock(LimsKey.class);
        when(limsKey3.getId()).thenReturn("3");
        when(limsKey3.getVersion()).thenReturn("v1");
        when(limsKey3.getLastModified()).thenReturn(limsLastModified);
        when(limsKey3.getProvider()).thenReturn(limsProvider);
        IusLimsKey iusLimsKey3 = Mockito.mock(IusLimsKey.class);
        when(iusLimsKey3.getIusSWID()).thenReturn(3);
        when(iusLimsKey3.getLimsKey()).thenReturn(limsKey3);

        ap1 = Mockito.mock(AnalysisProvenance.class);
        when(ap1.getIusLimsKeys()).thenReturn(ImmutableSet.of(iusLimsKey1));

        ap2 = Mockito.mock(AnalysisProvenance.class);
        when(ap2.getIusLimsKeys()).thenReturn(ImmutableSet.of(iusLimsKey2));

        ap3 = Mockito.mock(AnalysisProvenance.class);
        when(ap3.getIusLimsKeys()).thenReturn(ImmutableSet.of(iusLimsKey3));

        ap4 = Mockito.mock(AnalysisProvenance.class);
        when(ap4.getIusLimsKeys()).thenReturn(ImmutableSet.of(iusLimsKey1, iusLimsKey2, iusLimsKey3));

        expectedFpsSize = 6;

        when(app.getAnalysisProvenance(anyMap())).thenReturn(Arrays.asList(ap1, ap2, ap3, ap4));
        when(app.getAnalysisProvenance()).thenReturn(Arrays.asList(ap1, ap2, ap3, ap4));
        when(spp.getSampleProvenance()).thenReturn(Arrays.asList(sp1, sp2));
        when(lpp.getLaneProvenance()).thenReturn(Arrays.asList(lp));
    }

    @Test
    public void testSomeMethod() {
        Collection<FileProvenance> fps = provenanceClient.getFileProvenance();
        assertEquals(fps.size(), expectedFpsSize);
        Map<Status, Integer> s = getStatusCount(fps);
        assertEquals(s.get(Status.OKAY), Integer.valueOf(6));
        assertEquals(s.get(Status.STALE), Integer.valueOf(0));
        assertEquals(s.get(Status.ERROR), Integer.valueOf(0));
    }

    @Test
    public void sampleProvenanceVersionChangeCheckStatusIsStale() {
        when(sp1.getVersion()).thenReturn("modified");
        Collection<FileProvenance> fps = provenanceClient.getFileProvenance();
        assertEquals(fps.size(), expectedFpsSize);
        Map<Status, Integer> s = getStatusCount(fps);
        assertEquals(s.get(Status.OKAY), Integer.valueOf(2));
        assertEquals(s.get(Status.STALE), Integer.valueOf(4));
        assertEquals(s.get(Status.ERROR), Integer.valueOf(0));
    }

    @Test
    public void sampleProvenanceLastModifiedChangeCheckStatusIsStale() {
        when(sp1.getLastModified()).thenReturn(DateTime.parse("2000-01-01T00:00:00Z"));
        Collection<FileProvenance> fps = provenanceClient.getFileProvenance();
        assertEquals(fps.size(), expectedFpsSize);
        Map<Status, Integer> s = getStatusCount(fps);
        assertEquals(s.get(Status.OKAY), Integer.valueOf(2));
        assertEquals(s.get(Status.STALE), Integer.valueOf(4));
        assertEquals(s.get(Status.ERROR), Integer.valueOf(0));
    }

    @Test
    public void laneProvenanceVersionChangeCheckStatusIsStale() {
        when(lp.getVersion()).thenReturn("modified");
        Collection<FileProvenance> fps = provenanceClient.getFileProvenance();
        assertEquals(fps.size(), expectedFpsSize);
        Map<Status, Integer> s = getStatusCount(fps);
        assertEquals(s.get(Status.OKAY), Integer.valueOf(2));
        assertEquals(s.get(Status.STALE), Integer.valueOf(4));
        assertEquals(s.get(Status.ERROR), Integer.valueOf(0));
    }

    @Test
    public void laneProvenanceLastModifiedChangeCheckStatusIsStale() {
        when(lp.getLastModified()).thenReturn(DateTime.parse("2000-01-01T00:00:00Z"));
        Collection<FileProvenance> fps = provenanceClient.getFileProvenance();
        assertEquals(fps.size(), expectedFpsSize);
        Map<Status, Integer> s = getStatusCount(fps);
        assertEquals(s.get(Status.OKAY), Integer.valueOf(2));
        assertEquals(s.get(Status.STALE), Integer.valueOf(4));
        assertEquals(s.get(Status.ERROR), Integer.valueOf(0));
    }

    @Test
    public void sampleProvenanceIdChangeCheckStatusIsError() {
        when(sp1.getProvenanceId()).thenReturn("modified");
        Collection<FileProvenance> fps = provenanceClient.getFileProvenance();
        assertEquals(fps.size(), expectedFpsSize);
        Map<Status, Integer> s = getStatusCount(fps);
        assertEquals(s.get(Status.OKAY), Integer.valueOf(2));
        assertEquals(s.get(Status.STALE), Integer.valueOf(0));
        assertEquals(s.get(Status.ERROR), Integer.valueOf(4));
    }

    @Test
    public void laneProvenanceIdChangeCheckStatusIsError() {
        when(lp.getProvenanceId()).thenReturn("modified");
        Collection<FileProvenance> fps = provenanceClient.getFileProvenance();
        assertEquals(fps.size(), expectedFpsSize);
        Map<Status, Integer> s = getStatusCount(fps);
        assertEquals(s.get(Status.OKAY), Integer.valueOf(2));
        assertEquals(s.get(Status.STALE), Integer.valueOf(0));
        assertEquals(s.get(Status.ERROR), Integer.valueOf(4));
    }

    @Test
    public void testFileProvenanceFilters() {
        when(sp1.getLaneNumber()).thenReturn("1");
        when(sp1.getSequencerRunName()).thenReturn("RUN_1");
        when(sp1.getParentSampleName()).thenReturn("PARENT");
        when(sp1.getRootSampleName()).thenReturn("ROOT_0001");
        when(sp1.getSampleName()).thenReturn("ROOT_0001_R_P");
        when(sp1.getSkip()).thenReturn(true);
        when(sp1.getStudyTitle()).thenReturn("STUDY_TITLE");

        when(ap1.getFileId()).thenReturn(1000);
        when(ap1.getFileMetaType()).thenReturn("file/type");
        when(ap1.getProcessingId()).thenReturn(1001);
        when(ap1.getProcessingStatus()).thenReturn("success");
        when(ap1.getWorkflowRunStatus()).thenReturn("success");
        when(ap1.getWorkflowRunId()).thenReturn(1002);
        when(ap1.getWorkflowId()).thenReturn(1003);

        Map<FileProvenanceFilter, Set<String>> filters = new HashMap<>();
        filters.put(FileProvenanceFilter.file, ImmutableSet.of("1000"));
        filters.put(FileProvenanceFilter.file_meta_type, ImmutableSet.of("file/type"));
        filters.put(FileProvenanceFilter.ius, ImmutableSet.of("1"));
        filters.put(FileProvenanceFilter.lane, ImmutableSet.of("RUN_1_lane_1"));
        filters.put(FileProvenanceFilter.processing, ImmutableSet.of("1001"));
        filters.put(FileProvenanceFilter.processing_status, ImmutableSet.of("success"));
        filters.put(FileProvenanceFilter.root_sample, ImmutableSet.of("ROOT_0001"));
        filters.put(FileProvenanceFilter.sample, ImmutableSet.of("ROOT_0001_R_P"));
        filters.put(FileProvenanceFilter.sequencer_run, ImmutableSet.of("RUN_1"));
        filters.put(FileProvenanceFilter.skip, ImmutableSet.of("true"));
        filters.put(FileProvenanceFilter.study, ImmutableSet.of("STUDY_TITLE"));
        filters.put(FileProvenanceFilter.workflow, ImmutableSet.of("1003"));
        filters.put(FileProvenanceFilter.workflow_run, ImmutableSet.of("1002"));
        filters.put(FileProvenanceFilter.workflow_run_status, ImmutableSet.of("success"));

        Set<String> doesNotExist = Sets.newHashSet("does_not_exist");
        for (Entry<FileProvenanceFilter, Set<String>> e : filters.entrySet()) {
            assertFalse(provenanceClient.getFileProvenance(ImmutableMap.of(e.getKey(), e.getValue())).isEmpty(), "No results returned for filter [" + e.getKey() + "]. ");
            assertTrue(provenanceClient.getFileProvenance(ImmutableMap.of(e.getKey(), doesNotExist)).isEmpty(), "Results returned for filter [" + e.getKey() + "]. ");
        }

    }

    private Map<Status, Integer> getStatusCount(Collection<FileProvenance> fps) {
        Map<Status, Integer> counts = new HashMap<>();
        for (Status s : Status.values()) {
            counts.put(s, 0);
        }
        for (FileProvenance fp : fps) {
            counts.put(fp.getStatus(), counts.get(fp.getStatus()) + 1);
        }
        return counts;
    }

}
