/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.search.profile.AbstractTimingProfileBreakdown;
import org.opensearch.search.profile.Timer;
import org.opensearch.search.profile.query.TimingProfileContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class KNNQueryProfileBreakdown extends AbstractTimingProfileBreakdown implements TimingProfileContext {
    protected static final String CARDINALITY = "cardinality";

    protected int filter_cardinality;

    public KNNQueryProfileBreakdown() {
        for (KNNQueryTimingType type : KNNQueryTimingType.values()) {
            timers.put(type.toString(), new Timer());
        }
    }

    public void addCardinality(int cardinality) {
        filter_cardinality += cardinality;
    }

    @Override
    public Map<String, Long> toBreakdownMap() {
//        Map<String, Long> breakdownMap = super.toBreakdownMap();
        Map<String, Long> breakdownMap = new HashMap<>();
//        for (KNNQueryTimingType type : KNNQueryTimingType.values()) {
//            breakdownMap.remove(type + TIMING_TYPE_START_TIME_SUFFIX); // don't want start time in output
//        }
        breakdownMap.put(CARDINALITY, (long) filter_cardinality);
        return breakdownMap;
    }

    @Override
    public AbstractTimingProfileBreakdown context(Object context) {
        return this;
    }

    @Override
    public AbstractTimingProfileBreakdown getPluginBreakdown(Object context) {
        return null;
    }

    @Override
    public BiFunction<String, Long, Long> handleConcurrentPluginMetric() {
        return (key, value) -> {
            if(key.equals(CARDINALITY)) {
                if(value == null) return (long) filter_cardinality;
                return filter_cardinality + value;
            }
            return value;
        };
    }
}
