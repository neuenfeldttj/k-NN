/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.knn.index.query.KNNQuery;
import org.opensearch.search.profile.AbstractTimingProfileBreakdown;

import java.util.*;

/**
 * This class acts as a thread-local storage for profiling a query with concurrent execution
 *
 * @opensearch.internal
 */
public final class KNNConcurrentQueryProfiler extends KNNQueryProfiler {

    private final Map<Long, KNNConcurrentQueryProfileTree> threadToProfileTree;

    public KNNConcurrentQueryProfiler(KNNConcurrentQueryProfileTree profileTree) {
        super(profileTree);
        long threadId = getCurrentThreadId();
        // We utilize LinkedHashMap to preserve the insertion order of the profiled queries
        threadToProfileTree = Collections.synchronizedMap(new LinkedHashMap<>());
        threadToProfileTree.put(threadId, profileTree);
    }

    @Override
    public AbstractTimingProfileBreakdown<KNNQueryTimingType> getQueryBreakdown(KNNQuery query) {
        KNNConcurrentQueryProfileTree profileTree = threadToProfileTree.computeIfAbsent(
                getCurrentThreadId(),
                k -> new KNNConcurrentQueryProfileTree()
        );
        return profileTree.getProfileBreakdown(query);
    }

    /**
     * Removes the last (e.g. most recent) element on ConcurrentQueryProfileTree stack.
     */
    @Override
    public void pollLastElement() {
        KNNConcurrentQueryProfileTree concurrentProfileTree = threadToProfileTree.get(getCurrentThreadId());
        if (concurrentProfileTree != null) {
            concurrentProfileTree.pollLast();
        }
    }

    /**
     * @return a hierarchical representation of the profiled tree
     */
    @Override
    public List<KNNQueryProfileResult> getTree() {
        List<KNNQueryProfileResult> profileResults = new ArrayList<>();
        for (Map.Entry<Long, KNNConcurrentQueryProfileTree> profile : threadToProfileTree.entrySet()) {
            profileResults.addAll(profile.getValue().getTree());
        }
        return profileResults;
    }

    private long getCurrentThreadId() {
        return Thread.currentThread().threadId();
    }
}
