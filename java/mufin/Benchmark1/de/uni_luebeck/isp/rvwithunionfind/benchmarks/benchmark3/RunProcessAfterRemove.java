package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark3;

import java.util.Random;

/**
 * Run the benchmark performing an invalid operation.
 */
public class RunProcessAfterRemove {

	public static void main(String[] args) {
		Benchmark b = new Benchmark(new UncheckedInnerNode(), new Random(1));
		b.growExampleTree();
		b.performInvalidUse(800, 0);
	}
}
