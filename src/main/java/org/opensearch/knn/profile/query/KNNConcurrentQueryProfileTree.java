/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.search.profile.AbstractTimingProfileBreakdown;
import org.opensearch.search.profile.ProfileResult;
import org.opensearch.search.profile.query.ConcurrentQueryTimingProfileBreakdown;

import java.util.List;
import java.util.Map;

public class KNNConcurrentQueryProfileTree extends KNNQueryProfileTree {
    @Override
    protected AbstractTimingProfileBreakdown<KNNQueryTimingType> createProfileBreakdown() {
        return new KNNConcurrentQueryProfileBreakdown();
    }

    /**
     * For concurrent query case, when there are nested queries (with children), then the {@link ConcurrentQueryTimingProfileBreakdown} created
     * for the child queries weight doesn't have the association of collector to leaves. This is because child query weights are not
     * exposed by the {@link org.apache.lucene.search.Weight} interface. So after all the collection is happened and before the result
     * tree is created we need to pass the association from parent to the child breakdowns. This will be then used to create the
     * breakdown map at slice level for the child queries as well
     *
     * @return a hierarchical representation of the profiled query tree
     */
    @Override
    public List<ProfileResult> getTree() {
        for (Integer root : roots) {
            final AbstractTimingProfileBreakdown<KNNQueryTimingType> parentBreakdown = breakdowns.get(root);
            assert parentBreakdown instanceof KNNConcurrentQueryProfileBreakdown;
            // final Map<Collector, List<LeafReaderContext>> parentCollectorToLeaves = ((KNNConcurrentQueryProfileBreakdown)
            // parentBreakdown)
            // .getSliceCollectorsToLeaves();
            // // update all the children with the parent collectorToLeaves association
            // updateCollectorToLeavesForChildBreakdowns(root, parentCollectorToLeaves);
        }
        // once the collector to leaves mapping is updated, get the result
        return super.getTree();
    }

}
