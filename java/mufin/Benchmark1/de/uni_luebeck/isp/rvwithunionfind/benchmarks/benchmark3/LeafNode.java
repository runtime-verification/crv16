package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark3;

/**
 * A leaf node of the message tree.
 */
public interface LeafNode {
	/**
	 * Process a received message sent from an ancestor of this node. Clears
	 * this node's buffer.
	 * 
	 * Precondition: This node's message buffer is filled.
	 * 
	 * Postcondition: This node's message buffer is empty.
	 */
	public void process();

}
