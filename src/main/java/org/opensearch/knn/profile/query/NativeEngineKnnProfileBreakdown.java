/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import lombok.Setter;
import org.opensearch.core.ParseField;
import org.opensearch.search.profile.AbstractTimingProfileBreakdown;
import org.opensearch.search.profile.Timer;

import java.util.Map;

public class NativeEngineKnnProfileBreakdown extends AbstractTimingProfileBreakdown {
    public static final ParseField NUM_NESTED_DOCS = new ParseField("num_nested_docs");

    private final KNNQueryProfileBreakdown knnQueryBreakdown;

    @Setter
    private long numNestedDocs;

    public NativeEngineKnnProfileBreakdown() {
        for (NativeEngineKnnTimingType type : NativeEngineKnnTimingType.values()) {
            timers.put(type.toString(), new Timer());
        }
        knnQueryBreakdown = new KNNQueryProfileBreakdown();
    }

    @Override
    public Timer getTimer(String type) {
        if(timers.get(type) == null) {
            return knnQueryBreakdown.getTimer(type);
        }
        return timers.get(type);
    }

    @Override
    public Map<String, Long> toImportantMetricsMap() {
        return knnQueryBreakdown.toImportantMetricsMap();
    }

    public void addCardinality(int cardinality) {
        knnQueryBreakdown.addCardinality(cardinality);
    }

    @Override
    public Map<String, Long> toBreakdownMap() {
        Map<String, Long> map = knnQueryBreakdown.toBreakdownMap();
        map.put(NUM_NESTED_DOCS.getPreferredName(), numNestedDocs);
        return map;
    }
}
