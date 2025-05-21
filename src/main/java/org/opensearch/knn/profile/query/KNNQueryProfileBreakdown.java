/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.search.profile.Timer;
import org.opensearch.search.profile.query.QueryTimingType;

public class KNNQueryProfileBreakdown extends AbstractTimingProfileBreakdown<KNNQueryTimingType> implements TimingProfileContext {

    public KNNQueryProfileBreakdown() {
        for(KNNQueryTimingType type : KNNQueryTimingType.values()) {
            timers.put(type, new Timer());
        }
    }

    @Override
    public AbstractTimingProfileBreakdown<KNNQueryTimingType> context(Object context) {
        return this;
    }
}
