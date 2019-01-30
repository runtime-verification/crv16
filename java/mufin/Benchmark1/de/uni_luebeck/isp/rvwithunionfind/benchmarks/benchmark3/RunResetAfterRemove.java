package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark3;

import java.util.Random;

/**
 * Run the benchmark performing an invalid operation.
 */
public class RunResetAfterRemove {

	public static void main(String[] args) {
		Benchmark b = new Benchmark(new UncheckedInnerNode(), new Random(4));
		b.growExampleTree();
		b.performInvalidUse(800, 3);
	}
}
