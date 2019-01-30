#!/bin/sh
set -e
javac -source 1.8 -target 1.8 \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/MainBadChannelInvalid.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/MainBadChannelNotActive.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/MainGood.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/multiplexer/ChannelInvalidException.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/multiplexer/ChannelNotActiveException.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/multiplexer/Client.java \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/multiplexer/Multiplexer.java \

jar cfm benchmark4-multiplexer.jar Manifest.txt \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/MainBadChannelInvalid*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/MainBadChannelNotActive*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/MainGood*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/multiplexer/ChannelInvalidException*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/multiplexer/ChannelNotActiveException*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/multiplexer/Client*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/multiplexer/Multiplexer*.class \

rm -f \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/MainBadChannelInvalid*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/MainBadChannelNotActive*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/MainGood*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/multiplexer/ChannelInvalidException*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/multiplexer/ChannelNotActiveException*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/multiplexer/Client*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/multiplexer/Multiplexer*.class \
	de/uni_luebeck/isp/rvwithunionfind/benchmarks/benchmark4/InstrumentationAspect*.class \

