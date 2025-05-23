/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.search.profile.AbstractTimingProfileBreakdown;
import org.opensearch.search.profile.query.ProfileContext;

/**
 * ProfileContext for KNNQuery
 */
public interface KNNProfileContext extends ProfileContext {
    @Override
    public AbstractTimingProfileBreakdown<KNNQueryTimingType> context(Object context);
}
