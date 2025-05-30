/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.Collector;
import org.opensearch.OpenSearchException;
import org.opensearch.search.profile.AbstractTimingProfileBreakdown;
import org.opensearch.search.profile.Timer;
import org.opensearch.search.profile.query.QueryTimingProfileBreakdown;
import org.opensearch.search.profile.query.QueryTimingType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link AbstractTimingProfileBreakdown} for concurrent query timings.
 */
public class KNNConcurrentQueryProfileBreakdown extends KNNQueryProfileBreakdown {

    // keep track of all breakdown timings per segment. package-private for testing
    private final Map<Object, KNNQueryProfileBreakdown> contexts = new ConcurrentHashMap<>();


    @Override
    public KNNQueryProfileBreakdown context(Object context) {
        // See please https://bugs.openjdk.java.net/browse/JDK-8161372
        final KNNQueryProfileBreakdown profile = contexts.get(context);

        if (profile != null) {
            return profile;
        }

        return contexts.computeIfAbsent(context, ctx -> new KNNQueryProfileBreakdown());
    }

    @Override
    public long toNodeTime() {
        long nodeTime = 0;
        for (KNNQueryProfileBreakdown breakdown : contexts.values()) {
            nodeTime += breakdown.toNodeTime();
        }
        return nodeTime;
    }

    @Override
    public Map<String, Long> toBreakdownMap() {
        if (contexts.isEmpty()) {
            return buildDefaultKNNBreakdownMap();
        }

        Map<String, Long> aggregatedBreakdown = new HashMap<>();

        // Aggregate timing information from all contexts
        for (AbstractTimingProfileBreakdown<KNNQueryTimingType> breakdown : contexts.values()) {
            Map<String, Long> sliceBreakdown = breakdown.toBreakdownMap();
            // Merge timing information, keeping track of max/min/total for stats
            sliceBreakdown.forEach((key, value) ->
                    aggregatedBreakdown.merge(key, value, Long::max));
        }

        return aggregatedBreakdown;
    }

    /**
     * @return default breakdown map for concurrent query which includes the create weight time and all other timing type stats in the
     * breakdown has default value of 0. For concurrent search case, the max/min/avg stats for each timing type will also be 0 in this
     * default breakdown map.
     */
    private Map<String, Long> buildDefaultKNNBreakdownMap() {
        final Map<String, Long> concurrentQueryBreakdownMap = new HashMap<>();
        concurrentQueryBreakdownMap.put(CARDINALITY, (long) filter_cardinality);
        for (KNNQueryTimingType timingType : KNNQueryTimingType.values()) {
            final String timingTypeKey = timingType.toString();
            final String timingTypeCountKey = timingTypeKey + TIMING_TYPE_COUNT_SUFFIX;

            // add time related stats
            concurrentQueryBreakdownMap.put(timingTypeKey, 0L);
            // add count related stats
            concurrentQueryBreakdownMap.put(timingTypeCountKey, 0L);
        }
        return concurrentQueryBreakdownMap;
    }
}

