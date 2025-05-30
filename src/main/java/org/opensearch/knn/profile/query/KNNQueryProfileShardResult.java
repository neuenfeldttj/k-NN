/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.core.common.io.stream.StreamInput;
import org.opensearch.core.common.io.stream.StreamOutput;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.search.profile.AbstractProfileShardResult;
import org.opensearch.search.profile.TimingProfileResult;

import java.io.IOException;
import java.util.List;

public class KNNQueryProfileShardResult extends AbstractProfileShardResult<KNNQueryProfileResult> {
    public static final String QUERY_ARRAY = "knn-query";

    public KNNQueryProfileShardResult(List<KNNQueryProfileResult> profileResults) {
        super(profileResults);
    }

    public KNNQueryProfileShardResult(StreamInput in) throws IOException {
        super(in);
    }

    @Override
    public KNNQueryProfileResult createProfileResult(StreamInput in) throws IOException {
        return new KNNQueryProfileResult(in);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        out.writeVInt(profileResults.size());
        for (KNNQueryProfileResult p : profileResults) {
            p.writeTo(out);
        }
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject();
        builder.startArray(QUERY_ARRAY);
        for (KNNQueryProfileResult p : profileResults) {
            p.toXContent(builder, params);
        }
        builder.endArray();
        builder.endObject();
        return builder;
    }
}
