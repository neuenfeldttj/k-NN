/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.search.profile.AbstractTimingProfileBreakdown;
import org.opensearch.search.profile.Timer;

import java.util.Map;

public class LuceneEngineKnnProfileBreakdown extends AbstractTimingProfileBreakdown {
    public LuceneEngineKnnProfileBreakdown() {
        for(LuceneEngineKnnTimingType type : LuceneEngineKnnTimingType.values()) {
            timers.put(type.toString(), new Timer());
        }
    }

    @Override
    public Map<String, Long> toImportantMetricsMap() {
        return Map.of();
    }
}
