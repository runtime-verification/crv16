package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark3;

/**
 * An inner node of the message tree.
 */
public interface InnerNode {
	/**
	 * Send a message to all leaf nodes below this node.
	 * 
	 * Postcondition: The buffer of all leaf nodes below this node is filled.
	 */
	public void send();

	/**
	 * Send a critical message to all leaf nodes below this node.
	 * 
	 * Precondition: The buffer of all leaf nodes below this node is empty.
	 * 
	 * Postcondition: The buffer of all leaf nodes below this node is filled.
	 */
	public void sendCritical();

	/**
	 * Clear the message buffer of all leaf nodes below this node.
	 * 
	 * Postcondition: The buffer of all leaf nodes below this node is empty.
	 */
	public void reset();

	/**
	 * Add a leaf node below this node.
	 * 
	 * @param index
	 *            The insert position in [0, leafNodeCount()].
	 * @return The created leaf node.
	 */
	public LeafNode addLeafNode(int index);

	/**
	 * Add an inner node below this node.
	 * 
	 * @param index
	 *            The insert position in [0, innerNodeCount()].
	 * @return The created inner node.
	 */
	public InnerNode addInnerNode(int index);

	/**
	 * Remove a leaf node below this node.
	 * 
	 * Invalidates the removed leaf node.
	 * 
	 * @param index
	 *            The index of the leaf node to remove, in [0, leafNodeCount()).
	 */
	public void removeLeafNode(int index);

	/**
	 * Remove an inner node below this node.
	 * 
	 * Recursively invalidates the removed node and all its children.
	 * 
	 * @param index
	 *            The index of the inner node to remove, in [0,
	 *            innerNodeCount()).
	 */
	public void removeInnerNode(int index);

	/**
	 * Get the number of this node's leaf node children.
	 * 
	 * @return The number of leaf node children.
	 */
	public int leafNodeCount();

	/**
	 * Get the number of this node's inner node children.
	 * 
	 * @return The number of inner node children.
	 */
	public int innerNodeCount();

	/**
	 * Get a leaf node below this node.
	 * 
	 * @param index
	 *            The position in [0, leafNodeCount()).
	 * @return The leaf node at the given position.
	 */
	public LeafNode getLeafNode(int index);

	/**
	 * Get an inner node below this node.
	 * 
	 * @param index
	 *            The position in [0, innerNodeCount()).
	 * @return The inner node at the given position.
	 */
	public InnerNode getInnerNode(int index);
}
