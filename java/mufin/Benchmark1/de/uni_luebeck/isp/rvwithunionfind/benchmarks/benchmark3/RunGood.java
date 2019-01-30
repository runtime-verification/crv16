package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark3;

import java.util.Random;

/**
 * Run the benchmark performing only valid operations.
 */
public class RunGood {
	public static void main(String[] args) {
		Benchmark b = new Benchmark(new UncheckedInnerNode(), new Random(1));
		b.growExampleTree();
		b.performGoodOperations(800);
	}
}
