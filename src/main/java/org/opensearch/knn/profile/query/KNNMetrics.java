/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.core.ParseField;
import org.opensearch.knn.profile.LongMetric;
import org.opensearch.search.profile.Metric;
import org.opensearch.search.profile.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KNNMetrics {

    public static final String NUM_NESTED_DOCS = "num_nested_docs";
    public static final String CARDINALITY = "cardinality";

    public static Map<String, Class<? extends Metric>> getKNNQueryMetrics() {
        Map<String, Class<? extends Metric>> metrics = new HashMap<>();
        for(KNNQueryTimingType type : KNNQueryTimingType.values()) {
            metrics.put(type.toString(), Timer.class);
        }

        metrics.put(CARDINALITY, LongMetric.class);

        return metrics;
    }

    public static Map<String, Class<? extends Metric>> getNativeMetrics() {
        Map<String, Class<? extends Metric>> metrics = getKNNQueryMetrics();
        for(NativeEngineKnnTimingType type : NativeEngineKnnTimingType.values()) {
            metrics.put(type.toString(), Timer.class);
        }

        metrics.put(NUM_NESTED_DOCS, LongMetric.class);

        return metrics;
    }

    public static Map<String, Class<? extends Metric>> getLuceneMetrics() {
        Map<String, Class<? extends Metric>> metrics = new HashMap<>();
        for(LuceneEngineKnnTimingType type : LuceneEngineKnnTimingType.values()) {
            metrics.put(type.toString(), Timer.class);
        }

        return metrics;
    }
}
