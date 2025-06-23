#!/bin/bash
# OpenSearch Cluster End point url with hostname and port
export ENDPOINT="http://localhost:9200"
# Absolute file path of Workload param file
export PARAMS_FILE="/home/tjneu/.osb/benchmarks/workloads/default/vectorsearch/params/faiss-sift-128-l2.json"

opensearch-benchmark execute-test \
    --target-hosts $ENDPOINT \
    --workload vectorsearch \
    --workload-params ${PARAMS_FILE} \
    --pipeline benchmark-only \
    --kill-running-processes
