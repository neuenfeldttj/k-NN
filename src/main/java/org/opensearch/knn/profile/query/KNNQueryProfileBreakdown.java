/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.search.profile.AbstractTimingProfileBreakdown;
import org.opensearch.search.profile.Timer;

public class KNNQueryProfileBreakdown extends AbstractTimingProfileBreakdown<KNNQueryTimingType> implements KNNProfileContext {

    public KNNQueryProfileBreakdown() {
        for (KNNQueryTimingType type : KNNQueryTimingType.values()) {
            timers.put(type, new Timer());
        }
    }

    @Override
    public AbstractTimingProfileBreakdown<KNNQueryTimingType> context(Object context) {
        return this;
    }
}
