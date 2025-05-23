/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.knn.index.query.KNNQuery;
import org.opensearch.search.profile.AbstractInternalProfileTree;
import org.opensearch.search.profile.AbstractTimingProfileBreakdown;

import java.util.List;

public class KNNQueryProfileTree extends AbstractInternalProfileTree<
    AbstractTimingProfileBreakdown<KNNQueryTimingType>,
    KNNQueryTimingType,
    KNNQuery,
    KNNQueryProfileResult> {
    @Override
    protected AbstractTimingProfileBreakdown<KNNQueryTimingType> createProfileBreakdown() {
        return new KNNQueryProfileBreakdown();
    }

    @Override
    protected KNNQueryProfileResult createProfileResult(
        String type,
        String description,
        AbstractTimingProfileBreakdown<KNNQueryTimingType> breakdown,
        List<KNNQueryProfileResult> childrenProfileResults
    ) {
        return new KNNQueryProfileResult(
            type,
            description,
            breakdown.toBreakdownMap(),
            breakdown.toDebugMap(),
            breakdown.toNodeTime(),
            childrenProfileResults
        );
    }

    @Override
    protected String getTypeFromElement(KNNQuery query) {
        return query.getClass().getSimpleName();
    }

    @Override
    protected String getDescriptionFromElement(KNNQuery query) {
        return query.toString();
    }
}
