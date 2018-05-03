package graph;

/** Class Dijkstra. Implementation of Dijkstra's
 *  algorithm on the graph for finding the shortest path.
 *  Fill in code. You may add additional helper methods or classes.
 */

import com.sun.tools.corba.se.idl.InterfaceGen;

import java.util.*;
import java.awt.Point;

public class Dijkstra {
	private Graph graph; // stores the graph of CityNode-s and edges connecting them
    private List<Integer> shortestPath = null; // nodes that are part of the shortest path
	boolean[] known;
	int[] distances;
	int[] previous;

	/** Constructor
	 *
	 * @param filename name of the file that contains info about nodes and edges
     * @param graph graph
	 */
	public Dijkstra(String filename, Graph graph) {
		this.graph = graph;

		graph.loadGraph(filename);
		this.known = new boolean[graph.numNodes()];
		this.distances = new int[graph.numNodes()];
		this.previous = new int[graph.numNodes()];
	}

	/**
	 * Returns the shortest path between the origin vertex and the destination vertex.
	 * The result is stored in shortestPathEdges.
	 * This function is called from GUIApp, when the user clicks on two cities.
	 * @param origin source node
	 * @param destination destination node
     * @return the ArrayList of nodeIds (of nodes on the shortest path)
	 */
	public List<Integer> computeShortestPath(CityNode origin, CityNode destination) {

		PriorityQueue queue = new PriorityQueue();
		Edge[] adjacencyList = graph.getAdjacencyList();

		initializeTable();
		int origId = graph.getId(origin);
		setOrigin(known, distances, previous, origId); // Set origin information in table

		Nodes currNode = new Nodes(origId, 0);
		queue.insert(currNode.getId(), currNode.getPriority());


		while (queue.hasElement()) {
			currNode = queue.removeMin();
			this.known[currNode.getId()] = true;
			Edge currEdge = adjacencyList[currNode.getId()];
//			System.out.println("At nodeId: " + currNode.getId());
			int currDist = currNode.getPriority(); // grab the distance from the table?? Instead
			while (currEdge != null) {
//				System.out.println("Current edge: " + currEdge.getNeighbor() + " " + currEdge.getCost());
				int neighbourId = currEdge.getNeighbor();

				if(known[neighbourId] == true) {
					currEdge = currEdge.getNext();
				} else {
					//Update distances table
					int newDistance = currDist + currEdge.getCost();
					if (newDistance < distances[neighbourId]) {
						distances[currEdge.getNeighbor()] = newDistance;
						//Update previous table
						previous[neighbourId] = currNode.getId();
					}
					//Insert elements into the queue
					queue.insert(neighbourId, currDist + currEdge.getCost());
					//Update Edge
					currEdge = currEdge.getNext();
				}
			}
			System.out.println();
		}

		for (int i = 0; i < known.length; i++) {
			System.out.print(i + " ");
			System.out.print(known[i] + " ");
		}
		System.out.println();

		for (int i = 0; i < distances.length; i++) {
			System.out.print(distances[i] + " ");
		}
		System.out.println();

		for (int i = 0; i < previous.length; i++) {
			System.out.print(previous[i] + " ");
		}
		System.out.println();

		return getShortestPath(origin, destination);
	}

	private List<Integer> getShortestPath(CityNode origin, CityNode destination) {

		shortestPath = new ArrayList<>();
		int destId = graph.getId(destination);

		while(destId != graph.getId(origin)) {
			shortestPath.add(0, destId);
			destId = this.previous[destId];
		}
		shortestPath.add(0, graph.getId((origin)));
//		while(destId != -1 && i < graph.numNodes()) {
//			int prevId = this.previous[destId];
//			System.out.println("Previous id: " + prevId);
//			temp[i] = prevId;
//			destId = this.previous[prevId];
//			i++;
////			System.out.println("This is negative one: " + destId);
//		}
//
//		while (i-1 >= 0) {
////			System.out.println("here: " + i);
//			System.out.println("Path: " + temp[i-1]);
//			result.add(temp[i-1]);
//			i--;
//		}


		return shortestPath;
	}


    private void initializeTable() {

		for (int i = 0; i < distances.length; i++) {
			known[i] = false;
			distances[i] = Integer.MAX_VALUE;
			previous[i] = -1;
		}
	}

    private void setOrigin(boolean[] known, int[] distances, int[] previous, int original) {

		known[original] = true;
		distances[original] = 0;
		previous[original] = -1;

	}


    /**
     * Return the shortest path as a 2D array of Points.
     * Each element in the array is another array that has 2 Points:
     * these two points define the beginning and end of a line segment.
     * @return 2D array of points
     */
    public Point[][] getPath() {
        if (shortestPath == null)
            return null;
        return graph.getPath(shortestPath); // delegating this task to the Graph class
    }

    /** Set the shortestPath to null.
     *  Called when the user presses Reset button.
     */
    public void resetPath() {
        shortestPath = null;
    }

}