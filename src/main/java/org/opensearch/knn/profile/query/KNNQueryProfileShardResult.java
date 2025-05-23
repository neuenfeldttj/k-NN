/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.core.common.io.stream.StreamInput;
import org.opensearch.core.common.io.stream.StreamOutput;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.search.profile.AbstractProfileShardResult;

import java.io.IOException;
import java.util.List;

public class KNNQueryProfileShardResult extends AbstractProfileShardResult<KNNQueryProfileResult> {
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
    public void writeTo(StreamOutput streamOutput) throws IOException {

    }

    @Override
    public XContentBuilder toXContent(XContentBuilder xContentBuilder, Params params) throws IOException {
        return null;
    }
}
