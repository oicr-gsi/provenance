package ca.on.oicr.gsi.provenance;

import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author mlaszloffy
 */
public class DefaultProvenanceClientTest extends ProvenanceClientBase {

    @Override
    public ProvenanceClient getProvenanceClient(
            Map<String, AnalysisProvenanceProvider> analysisProvenanceProviders,
            Map<String, SampleProvenanceProvider> sampleProvenanceProviders,
            Map<String, LaneProvenanceProvider> laneProvenanceProviders) {

        DefaultProvenanceClient client = new DefaultProvenanceClient();
        for (Entry<String, AnalysisProvenanceProvider> e : analysisProvenanceProviders.entrySet()) {
            client.registerAnalysisProvenanceProvider(e.getKey(), e.getValue());
        }
        for (Entry<String, SampleProvenanceProvider> e : sampleProvenanceProviders.entrySet()) {
            client.registerSampleProvenanceProvider(e.getKey(), e.getValue());
        }
        for (Entry<String, LaneProvenanceProvider> e : laneProvenanceProviders.entrySet()) {
            client.registerLaneProvenanceProvider(e.getKey(), e.getValue());
        }
        return client;
    }

}
