/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.apache.lucene.search.Query;
import org.opensearch.knn.index.query.KNNQuery;
import org.opensearch.search.profile.AbstractInternalProfileTree;
import org.opensearch.search.profile.AbstractProfiler;
import org.opensearch.search.profile.query.QueryTimingType;

public class KNNQueryProfiler extends AbstractProfiler<AbstractTimingProfileBreakdown<QueryTimingType>, QueryTimingType, KNNQuery, TimingProfileResult> {
    public KNNQueryProfiler(AbstractInternalProfileTree<AbstractTimingProfileBreakdown<QueryTimingType>, QueryTimingType> profileTree) {
        super(profileTree);
    }
}
