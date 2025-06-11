/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.search.profile.AbstractTimingProfileBreakdown;
import org.opensearch.search.profile.Timer;

import java.util.HashMap;
import java.util.Map;

public class KNNQueryProfileBreakdown extends AbstractTimingProfileBreakdown {
    protected static final String CARDINALITY = "cardinality";

    protected int filter_cardinality;

    public KNNQueryProfileBreakdown() {
        for (KNNQueryTimingType type : KNNQueryTimingType.values()) {
            timers.put(type.toString(), new Timer());
        }
    }

    @Override
    public Map<String, Long> toImportantMetricsMap() {
        return Map.of(CARDINALITY, (long) filter_cardinality);
    }

    public void addCardinality(int cardinality) {
        filter_cardinality += cardinality;
    }

    @Override
    public Map<String, Long> toBreakdownMap() {
        Map<String, Long> breakdownMap = super.toBreakdownMap();
        breakdownMap.put(CARDINALITY, (long) filter_cardinality);
        return breakdownMap;
    }
}
