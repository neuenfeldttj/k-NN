/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.core.common.io.stream.StreamInput;
import org.opensearch.core.common.io.stream.StreamOutput;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.search.profile.AbstractProfileShardResult;
import org.opensearch.search.profile.ProfileResult;

import java.io.IOException;
import java.util.List;

public class KNNQueryProfileShardResult extends AbstractProfileShardResult {
    public static final String QUERY_ARRAY = "knn-query";

    public KNNQueryProfileShardResult(List<ProfileResult> profileResults) {
        super(profileResults);
    }

    public KNNQueryProfileShardResult(StreamInput in) throws IOException {
        super(in);
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject();
        builder.startArray(QUERY_ARRAY);
        for (ProfileResult p : profileResults) {
            p.toXContent(builder, params);
        }
        builder.endArray();
        builder.endObject();
        return builder;
    }
}
