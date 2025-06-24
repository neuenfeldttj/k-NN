/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.knn.profile.LongMetric;
import org.opensearch.search.profile.ProfileMetric;
import org.opensearch.search.profile.Timer;

import java.util.HashMap;
import java.util.Map;

public class KNNMetrics {

    public static final String NUM_NESTED_DOCS = "num_nested_docs";
    public static final String CARDINALITY = "cardinality";

    public static Map<String, Class<? extends ProfileMetric>> getKNNQueryMetrics() {
        Map<String, Class<? extends ProfileMetric>> metrics = new HashMap<>();
        for(KNNQueryTimingType type : KNNQueryTimingType.values()) {
            metrics.put(type.toString(), Timer.class);
        }

        metrics.put(CARDINALITY, LongMetric.class);

        return metrics;
    }

    public static Map<String, Class<? extends ProfileMetric>> getNativeMetrics() {
        Map<String, Class<? extends ProfileMetric>> metrics = getKNNQueryMetrics();
        for(NativeEngineKnnTimingType type : NativeEngineKnnTimingType.values()) {
            metrics.put(type.toString(), Timer.class);
        }

        metrics.put(NUM_NESTED_DOCS, LongMetric.class);

        return metrics;
    }

    public static Map<String, Class<? extends ProfileMetric>> getLuceneMetrics() {
        Map<String, Class<? extends ProfileMetric>> metrics = new HashMap<>();
        for(LuceneEngineKnnTimingType type : LuceneEngineKnnTimingType.values()) {
            metrics.put(type.toString(), Timer.class);
        }

        return metrics;
    }
}
