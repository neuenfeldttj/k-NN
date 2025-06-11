/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.index.query.lucene;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.KnnFloatVectorQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.knn.KnnCollectorManager;
import org.apache.lucene.util.Bits;

import java.io.IOException;

public class ProfileKnnFloatVectoryQuery extends KnnFloatVectorQuery {

    public ProfileKnnFloatVectoryQuery(String field, float[] target, int k, Query filter) {
        super(field, target, k, filter);
    }

    @Override
    protected TopDocs approximateSearch(
            LeafReaderContext context,
            Bits acceptDocs,
            int visitedLimit,
            KnnCollectorManager knnCollectorManager)
            throws IOException {

        return super.approximateSearch(context, acceptDocs, visitedLimit, knnCollectorManager);
    }


//    protected TopDocs exactSearch(
//            LeafReaderContext context,
//            Bits acceptDocs,
//            int visitedLimit,
//            KnnCollectorManager knnCollectorManager) {
//        return super.exactSearch(context, acceptDocs, visitedLimit, knnCollectorManager);
//    }

}
