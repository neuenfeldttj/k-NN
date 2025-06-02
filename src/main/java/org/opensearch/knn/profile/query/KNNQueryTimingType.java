/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import java.util.Locale;

public enum KNNQueryTimingType {
    SEARCH_LEAF,
    ANN_SEARCH,
    EXACT_SEARCH,
    BITSET_CREATION;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }
}
