/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.knn.profile;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.opensearch.search.profile.ProfileMetric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VectorStatsMetric extends ProfileMetric {

    @Setter
    private List<SummaryStatistics> dimensionsStats = new ArrayList<>();

    public VectorStatsMetric(String name) {
        super(name);
    }

    @Override
    public Map<String, Long> toBreakdownMap() {
        Map<String, Long> map = new HashMap<>();
        // TODO: would be nice if core side supported double types...
        for(int i = 0; i < dimensionsStats.size(); ++i) {
            map.put(getName() + "_dim_" + i + "_min", Math.round(dimensionsStats.get(i).getMin()));
            map.put(getName() + "_dim_" + i + "_max", Math.round(dimensionsStats.get(i).getMax()));
            map.put(getName() + "_dim_" + i + "_avg", Math.round(dimensionsStats.get(i).getMean()));
            map.put(getName() + "_dim_" + i + "_stdev", Math.round(dimensionsStats.get(i).getStandardDeviation()));
        }
        return map;
    }
}
