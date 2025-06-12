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
import java.util.List;

public class KNNMetrics {

    public static final String NUM_NESTED_DOCS = "num_nested_docs";
    public static final String CARDINALITY = "cardinality";

    public static List<Metric> getKNNQueryMetrics() {
        List<Metric> metrics = new ArrayList<>();
        for(KNNQueryTimingType type : KNNQueryTimingType.values()) {
            metrics.add(new Timer(type.toString()));
        }

        metrics.add(new LongMetric(CARDINALITY));

        return metrics;
    }

    public static List<Metric> getNativeMetrics() {
        List<Metric> metrics = getKNNQueryMetrics();
        for(NativeEngineKnnTimingType type : NativeEngineKnnTimingType.values()) {
            metrics.add(new Timer(type.toString()));
        }

        metrics.add(new LongMetric(NUM_NESTED_DOCS));

        return metrics;
    }

    public static List<Metric> getLuceneMetrics() {
        List<Metric> metrics = new ArrayList<>();
        for(LuceneEngineKnnTimingType type : LuceneEngineKnnTimingType.values()) {
            metrics.add(new Timer(type.toString()));
        }

        return metrics;
    }
}
