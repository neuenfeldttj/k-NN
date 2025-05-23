/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile.query;

import org.opensearch.common.unit.TimeValue;
import org.opensearch.core.ParseField;
import org.opensearch.core.common.io.stream.StreamInput;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.search.profile.AbstractProfileResult;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class KNNQueryProfileResult extends AbstractProfileResult<KNNQueryProfileResult> {
    static final ParseField SEARCH_LEAF_TIME = new ParseField("leaf_time");
    static final ParseField SEARCH_LEAF_TIME_RAW = new ParseField("leaf_time_in_nanos");

    private final long searchLeafTime;

    public KNNQueryProfileResult(
        String type,
        String description,
        Map<String, Long> breakdown,
        Map<String, Object> debug,
        long searchLeafTime,
        List<KNNQueryProfileResult> children
    ) {
        super(type, description, breakdown, debug, children);
        this.searchLeafTime = searchLeafTime;
    }

    public KNNQueryProfileResult(StreamInput in) throws IOException {
        super(in);
        this.searchLeafTime = in.readLong();
    }

    public long getSearchLeafTime() {
        return searchLeafTime;
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject();
        builder.field(TYPE.getPreferredName(), getQueryName());
        builder.field(DESCRIPTION.getPreferredName(), getLuceneDescription());
        if (builder.humanReadable()) {
            builder.field(SEARCH_LEAF_TIME.getPreferredName(), new TimeValue(getSearchLeafTime(), TimeUnit.NANOSECONDS).toString());
        }
        builder.field(SEARCH_LEAF_TIME_RAW.getPreferredName(), getSearchLeafTime());
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
