/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.knn.index.query.KNNQuery;
import org.opensearch.search.profile.AbstractProfiler;
import org.opensearch.search.profile.AbstractTimingProfileBreakdown;

public class KNNQueryProfiler extends AbstractProfiler<
    AbstractTimingProfileBreakdown<KNNQueryTimingType>,
    KNNQueryTimingType,
    KNNQuery,
    KNNQueryProfileResult,
    KNNQueryProfileShardResult> {

    public KNNQueryProfiler(KNNQueryProfileTree profileTree) {
        super(profileTree);
    }

    @Override
    public KNNQueryProfileShardResult createProfileShardResult() {
        return new KNNQueryProfileShardResult(getTree());
    }
}
