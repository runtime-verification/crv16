package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Benchmark {
	private InnerNode rootNode;
	private Random prng;

	public Benchmark(InnerNode rootNode, Random prng) {
		this.rootNode = rootNode;
		this.prng = prng;
	}

	public void goodUntilHere() {
		System.out.println("goodUntilHere()");
	}

	public void badBeforeHere() {
		System.out.println("badBeforeHere()");
	}

	public void growExampleTree() {
		growTree(Arrays.asList(5, 10, 20, 5, 3, 3, 4));
		growTree(Arrays.asList(10, 10, 1000, 5));
		growTree(Arrays.asList(3, 3, 3, 4, 20));
	}

	public void growTree(List<Integer> widths) {
		growTree(rootNode, widths);
	}

	protected void growTree(InnerNode node, List<Integer> widths) {
		if (widths.size() == 1) {
			int width = prng.nextInt(widths.get(0)) + 1;
			for (int i = 0; i < width; ++i) {
				node.addLeafNode(node.leafNodeCount());
			}

		} else {
			List<Integer> nextWidths = widths.subList(1, widths.size());
			int width = prng.nextInt(widths.get(0)) + widths.get(0);
			for (int i = 0; i < width; ++i) {
				InnerNode inner = node.addInnerNode(node.innerNodeCount());
				growTree(inner, nextWidths);
			}
			width = prng.nextInt(widths.get(0)) + widths.get(0);
			for (int i = 0; i < width; ++i) {
				node.addLeafNode(node.leafNodeCount());
			}
		}
	}

	public void performGoodOperations(int count) {
		for (int i = 0; i < count; i++) {
			sendAndProcessSome(rootNode);
			sendResetAndCritical(rootNode);
			mutateSome(rootNode);
		}
	}

	public void performInvalidUse(int count, int kind) {
		performGoodOperations(count / 2);
		InnerNode removed = removeSomeNode(rootNode);
		performGoodOperations(count / 2);
		goodUntilHere();
		switch (kind) {
		case 0: {
			getSomeLeafNode(removed).process();
			break;
		}
		case 1: {
			getSomeInnerNode(removed).send();
			break;
		}
		case 2: {
			getSomeInnerNode(removed).sendCritical();
			break;
		}
		case 3: {
			getSomeInnerNode(removed).reset();
			break;
		}
		default:
			throw new Error(
					"Unknown value for performInvalidUse kind parameter");
		}
		badBeforeHere();
	}

	public void performEmptyProcess(int count) {
		performGoodOperations(count);
		InnerNode i = getSomeInnerNode(rootNode);
		LeafNode l = getSomeLeafNode(i);
		i.reset();
		goodUntilHere();
		l.process();
		badBeforeHere();
	}

	public void performFullSendCritical(int count) {
		performGoodOperations(count);
		InnerNode i = getSomeInnerNode(rootNode);
		InnerNode i2 = getSomeInnerNode(i);
		if (i2 == i) {
			i = rootNode;
		}
		i.reset();
		i2.send();
		goodUntilHere();
		i.sendCritical();
		badBeforeHere();
	}

	private List<Integer> randomIndices(int max) {
		return randomIndices(max, max);
	}

	private List<Integer> randomIndices(int max, int selection) {
		if (selection > max) {
			selection = max;
		}
		List<Integer> indices = new ArrayList<>();
		for (int i = 0; i < max; i++) {
			indices.add(i);
		}
		Collections.shuffle(indices, prng);
		return indices.subList(0, selection);
	}

	private void sendAndProcessSome(InnerNode node) {
		if (prng.nextInt(6) == 0) {
			for (int i : randomIndices(node.innerNodeCount(), 5)) {
				sendAndProcessSome(node.getInnerNode(i));
			}
		} else {
			node.send();
			processSome(node);
		}
	}

	private void processSome(InnerNode node) {
		for (int i : randomIndices(node.leafNodeCount(), 3)) {
			node.getLeafNode(i).process();
		}
		for (int i : randomIndices(node.innerNodeCount(), 1 + prng.nextInt(2))) {
			processSome(node.getInnerNode(i));
		}
	}

	private void sendResetAndCritical(InnerNode node) {
		if (prng.nextInt(6) == 0) {
			for (int i : randomIndices(node.innerNodeCount(), 5)) {
				sendResetAndCritical(node.getInnerNode(i));
			}
		} else {
			node.send();
			List<Integer> indices = randomIndices(node.innerNodeCount());
			Set<Integer> subset = new HashSet<>(randomIndices(
					node.innerNodeCount(), node.innerNodeCount() / 2));
			for (int i : indices) {
				if (subset.contains(i)) {
					performReset(node.getInnerNode(i));
				} else {
					node.getInnerNode(i).send();
				}
			}
			indices = randomIndices(node.innerNodeCount());
			for (int i : indices) {
				if (subset.contains(i)) {
					node.getInnerNode(i).sendCritical();
				} else {
					node.getInnerNode(i).send();
				}
			}
		}
	}

	private void performReset(InnerNode node) {
		if (prng.nextInt(node.innerNodeCount() + 1) == 0) {
			node.send();
			for (int i : randomIndices(node.innerNodeCount())) {
				performReset(node.getInnerNode(i));
			}
			for (int i : randomIndices(node.leafNodeCount())) {
				node.getLeafNode(i).process();
			}
		} else {
			node.reset();
		}
	}

	private void mutateSome(InnerNode node) {
		if (prng.nextInt(6) == 0) {
			for (int i : randomIndices(node.innerNodeCount(), 5)) {
				mutateSome(node.getInnerNode(i));
			}
		} else {
			for (int i : randomIndices(node.innerNodeCount(), 1)) {
				node.removeInnerNode(i);
			}
			for (int i : randomIndices(node.leafNodeCount(), 1)) {
				node.removeLeafNode(i);
			}
			growTree(node, Arrays.asList(5, 20, 5));
		}
	}

	private InnerNode removeSomeNode(InnerNode node) {
		for (int i : randomIndices(node.innerNodeCount(), 1)) {
			InnerNode n = node.getInnerNode(i);
			node.removeInnerNode(i);
			return n;
		}
		return node;
	}

	private InnerNode getSomeInnerNode(InnerNode node) {
		for (int i : randomIndices(node.innerNodeCount(), 1)) {
			InnerNode n = node.getInnerNode(i);
			return n;
		}
		return node;
	}

	private LeafNode getSomeLeafNode(InnerNode node) {
		for (int i : randomIndices(node.leafNodeCount(), 1)) {
			LeafNode n = node.getLeafNode(i);
			return n;
		}
		return getSomeLeafNode(getSomeInnerNode(node));
	}

}
