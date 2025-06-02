/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.core.common.io.stream.StreamInput;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.search.profile.AbstractProfileResult;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KNNQueryProfileResult extends AbstractProfileResult<KNNQueryProfileResult> {

    public KNNQueryProfileResult(
        String type,
        String description,
        Map<String, Long> breakdown,
        Map<String, Object> debug,
        List<KNNQueryProfileResult> children
    ) {
        super(type, description, breakdown, debug, children);
    }

    public KNNQueryProfileResult(StreamInput in) throws IOException {
        super(in);
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject();
        builder.field(TYPE.getPreferredName(), getQueryName());
        builder.field(DESCRIPTION.getPreferredName(), getLuceneDescription());
        Map<String, Long> modifiedBreakdown = new LinkedHashMap<>(getTimeBreakdown());
        builder.field(BREAKDOWN.getPreferredName(), modifiedBreakdown);
        if (false == getDebugInfo().isEmpty()) {
            builder.field(DEBUG.getPreferredName(), getDebugInfo());
        }

        if (false == children.isEmpty()) {
            builder.startArray(CHILDREN.getPreferredName());
            for (KNNQueryProfileResult child : children) {
                builder = child.toXContent(builder, params);
            }
            builder.endArray();
        }

        return builder.endObject();
    }
}
