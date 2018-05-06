package graph;

import java.io.PrintWriter;

/** A priority queue: implemented using a min heap.
 *  You may not use any Java built-in classes, you should implement
 *  PriorityQueue yourself. You may use/modify the MinHeap code posted
 *  by the instructor under Examples, as long as you understand it. */
public class PriorityQueue {

	private Nodes[] queue;
	private int count;
	private int[] positions;


	public PriorityQueue() {
		this.queue = new Nodes[21];
		this.count = 0;
		this.positions = new int[20];
		initialize();
	}

	/**
	 * Initializing the positions array
	 */
	public void initialize() {
		for (int i = 0; i < this.queue.length - 1; i++) {
			this.positions[i] = -1;
		}
		Nodes dummy = new Nodes(- 10, Integer.MIN_VALUE);
		this.queue[0] = dummy;
	}

	/**
	 * Checks to for the parent
	 * @param position
	 * @return
	 */
	private int parent(int position) {
		return position / 2;
	}

	/** Insert a new element (nodeId, priority) into the heap.
     *  For this project, the priority is the current "distance"
     *  for this nodeId in Dikstra's algorithm. */
	public void insert(int nodeId, int priority) {

		Nodes newNode = new Nodes(nodeId, priority);

		if (this.positions[nodeId] == -1) {
			this.queue[++this.count] = newNode;
			this.positions[nodeId] = this.count;

			int current = this.count;
			while (this.queue[current].getPriority() < this.queue[parent(current)].getPriority()) {
				swap(current, parent(current));
				current = parent(current);
			}
		} else {
			reduceKey(nodeId, priority);
		}
	}

	/**
	 * Swaps two values in the min heap array and
	 * updates the positions array to its respective new position
	 * @param child
	 * @param parent
	 */
	private void swap(int child, int parent) {

		Nodes temp = this.queue[child];
		this.queue[child] = this.queue[parent];
		this.queue[parent] = temp;

		this.positions[this.queue[parent].getId()] = child;
		this.positions[this.queue[child].getId()] = parent; // this will refer to the dummy node
	}

    /**
     * Remove the element with the minimum priority
     * from the min heap and return its nodeId.
     * @return nodeId of the element with the smallest priority
     */
	public Nodes removeMin() {

		swap(1, this.count);
		this.count--;

		if (this.count > 1) {
			buildMinHeap(1);
		}
		return this.queue[this.count + 1];
	}
	/**
	 * Checks if the node at the given index is a leaf
	 */
	private boolean isLeaf(int index) {
		return index > ((this.count)/2) && index <= this.count;
	}

	/**
	 * Build up the queue into a min heap
	 * @param i
	 */
	private void buildMinHeap(int i) {

		int parent = i;
		int smallestChild;

		while(!isLeaf(parent)) {
			smallestChild = parent *2;

			if((smallestChild < this.count) && (this.queue[smallestChild].getPriority() > this.queue[smallestChild + 1].getPriority())) {
				smallestChild += 1;
			}

			if (this.queue[parent].getPriority() <= this.queue[smallestChild].getPriority()) {
				return;
			}

			swap(smallestChild, parent);
			parent = smallestChild;
		}
	}


    /**
     * Reduce the priority of the element with the given nodeId to newPriority.
     * You may assume newPriority is less or equal to the current priority for this node.
     * @param nodeId id of the node
     * @param newPriority new value of priority
     */
	public void reduceKey(int nodeId, int newPriority) {

		int position = this.positions[nodeId];

		Nodes node = this.queue[position];
		node.setPriority(newPriority);

		int current = position;
		while (queue[current].getPriority() < queue[parent(current)].getPriority()) {
			swap(current, parent(current));
			current = parent(current);
		}

	}

	/**
	 * Checks to see if there are any elements in the queue (Min Heap)
	 * @return
	 */
	public boolean hasElement() {
		return this.count != 0;
	}

}

