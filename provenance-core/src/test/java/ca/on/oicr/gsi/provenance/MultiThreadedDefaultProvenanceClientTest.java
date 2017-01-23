package ca.on.oicr.gsi.provenance;

import java.util.Map;

/**
 *
 * @author mlaszloffy
 */
public class MultiThreadedDefaultProvenanceClientTest extends ProvenanceClientBase {

    @Override
    public ProvenanceClient getProvenanceClient(
            Map<String, AnalysisProvenanceProvider> analysisProvenanceProviders,
            Map<String, SampleProvenanceProvider> sampleProvenanceProviders,
            Map<String, LaneProvenanceProvider> laneProvenanceProviders) {

        MultiThreadedDefaultProvenanceClient client = new MultiThreadedDefaultProvenanceClient();
        for (Map.Entry<String, AnalysisProvenanceProvider> e : analysisProvenanceProviders.entrySet()) {
            client.registerAnalysisProvenanceProvider(e.getKey(), e.getValue());
        }
        for (Map.Entry<String, SampleProvenanceProvider> e : sampleProvenanceProviders.entrySet()) {
            client.registerSampleProvenanceProvider(e.getKey(), e.getValue());
        }
        for (Map.Entry<String, LaneProvenanceProvider> e : laneProvenanceProviders.entrySet()) {
            client.registerLaneProvenanceProvider(e.getKey(), e.getValue());
        }
        return client;
    }

}
