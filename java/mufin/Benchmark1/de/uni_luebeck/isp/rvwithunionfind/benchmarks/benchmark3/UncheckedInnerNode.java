package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark3;

import java.util.ArrayList;

/**
 * Implementation of InnerNode without any extra verification checks.
 */
public class UncheckedInnerNode implements InnerNode {
	protected ArrayList<LeafNode> leafNodes;
	protected ArrayList<InnerNode> innerNodes;

	public UncheckedInnerNode() {
		leafNodes = new ArrayList<>();
		innerNodes = new ArrayList<>();
	}

	protected LeafNode createLeafNode() {
		return new UncheckedLeafNode();
	}

	protected InnerNode createInnerNode() {
		return new UncheckedInnerNode();
	}

	@Override
	public void send() {
	}

	@Override
	public void sendCritical() {
	}

	@Override
	public void reset() {
	}

	@Override
	public LeafNode addLeafNode(int index) {
		LeafNode node = createLeafNode();
		leafNodes.add(index, node);
		return node;
	}

	@Override
	public InnerNode addInnerNode(int index) {
		InnerNode node = createInnerNode();
		innerNodes.add(index, node);
		return node;
	}

	@Override
	public void removeLeafNode(int index) {
		leafNodes.remove(index);
	}

	@Override
	public void removeInnerNode(int index) {
		innerNodes.remove(index);
	}

	@Override
	public int leafNodeCount() {
		return leafNodes.size();
	}

	@Override
	public int innerNodeCount() {
		return innerNodes.size();
	}

	@Override
	public LeafNode getLeafNode(int index) {
		return leafNodes.get(index);
	}

	@Override
	public InnerNode getInnerNode(int index) {
		return innerNodes.get(index);
	}

}
