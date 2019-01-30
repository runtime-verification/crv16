#!/bin/sh
set -e
javac -source 1.8 -target 1.8 \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/Device.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/Main.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/MainBad.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/MainGood.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/Piece.java \

jar cfm benchmark5-simple_toggle.jar Manifest.txt \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/Device*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/Main*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/MainBad*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/MainGood*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/Piece*.class \

rm -f \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/Device*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/Main*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/MainBad*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/MainGood*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/Piece*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark5/InstrumentationAspect*.class \

