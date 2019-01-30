package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark3;

import java.util.Random;

/**
 * Run the benchmark performing an invalid operation.
 */
public class RunFullSendCritical {

	public static void main(String[] args) {
		Benchmark b = new Benchmark(new UncheckedInnerNode(), new Random(6));
		b.growExampleTree();
		b.performFullSendCritical(800);
	}
}
