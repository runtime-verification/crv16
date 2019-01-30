#!/bin/sh
set -e
javac -source 1.8 -target 1.8 \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/Benchmark.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/InnerNode.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/LeafNode.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunEmptyProcess.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunFullSendCritical.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunGood.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunProcessAfterRemove.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunResetAfterRemove.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunSendAfterRemove.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunSendCriticalAfterRemove.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/UncheckedInnerNode.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/UncheckedLeafNode.java \

jar cfm benchmark3-tree.jar Manifest.txt \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/Benchmark*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/InnerNode*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/LeafNode*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunEmptyProcess*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunFullSendCritical*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunGood*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunProcessAfterRemove*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunResetAfterRemove*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunSendAfterRemove*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunSendCriticalAfterRemove*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/UncheckedInnerNode*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/UncheckedLeafNode*.class \

rm -f \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/Benchmark*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/InnerNode*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/LeafNode*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunEmptyProcess*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunFullSendCritical*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunGood*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunProcessAfterRemove*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunResetAfterRemove*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunSendAfterRemove*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/RunSendCriticalAfterRemove*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/UncheckedInnerNode*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/UncheckedLeafNode*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark3/InstrumentationAspect*.class \

