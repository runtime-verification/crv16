package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark3;

import java.util.Random;

/**
 * Run the benchmark performing an invalid operation.
 */
public class RunEmptyProcess {

	public static void main(String[] args) {
		Benchmark b = new Benchmark(new UncheckedInnerNode(), new Random(5));
		b.growExampleTree();
		b.performEmptyProcess(800);
	}
}
