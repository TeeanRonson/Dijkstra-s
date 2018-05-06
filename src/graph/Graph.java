package graph;

/**
 * A class that represents a graph where nodes are cities (of type CityNode)
 * and edges connect them and the cost of each edge is the distance between
 * the cities.
 * Fill in code in this class. You may add additional methods and variables.
 * You are required to implement a HashTable and a PriorityQueue from scratch.
 */
import java.util.*;
import java.io.*;
import java.awt.Point;

public class Graph {
    public final int EPS_DIST = 5;

    private CityNode[] nodes;
	private int numNodes;
	private int numEdges;
	private Edge[] adjacencyList;
    private HashTable cityToId;

	public Graph() {
		this.nodes = new CityNode[20];
		this.numNodes = 0;
		this.numEdges = 0;
		this.adjacencyList = new Edge[20];
		this.cityToId = new HashTable();
	}
	/**
	 * Read graph info from the given file, and create nodes and edges of
	 * the graph.
	 *
	 * @param filename name of the file that has nodes and edges
	 */
	public void loadGraph(String filename) {

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

			int docLines = 0;
			int nextSet = Integer.MAX_VALUE;
			String currentLine;
			String arcs = "ARCS";


			while ((currentLine = reader.readLine()) != null ) {
				docLines++;


				if (docLines > 2 && !currentLine.equals(arcs) && docLines < nextSet) {

					String[] info = currentLine.split("\\s+");
					String cityName = info[0];
					double lon = Double.parseDouble(info[1]);
					double lat = Double.parseDouble(info[2]);
					CityNode temp = new CityNode(cityName, lon, lat);
					this.cityToId.add(cityName);
					addNode(temp);
				}

				if (currentLine.equals(arcs)) {
					nextSet = docLines + 1;
					continue;
				}

				if (docLines >= nextSet) {
					String[] info = currentLine.split("\\s+");
					String src = info[0];
					String dest = info[1];
					int cost = Integer.parseInt(info[2]);

					int srcId = cityToId.get(src);
					int destId = cityToId.get(dest);
					Edge newEdge0 = new Edge(destId, cost, null);
					Edge newEdge1 = new Edge(srcId, cost, null);
					addEdge(srcId, newEdge0);
					addEdge(destId, newEdge1);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File Input Unsuccessful");
		}

	}

	/**
	 * Add a node to the array of nodes.
	 * Increment numNodes variable.
     * Called from loadGraph.
	 *
	 * @param node a CityNode to add to the graph
	 */
	public void addNode(CityNode node) {
		nodes[this.numNodes] = node;
		this.numNodes++;
	}

	/**
	 * Return the number of nodes in the graph
	 * @return number of nodes
	 */
	public int numNodes() {
		return this.numNodes;
	}

	public Edge[] getAdjacencyList() {
		return this.adjacencyList;
	}

	/**
	 * Adds the edge to the linked list for the given nodeId
	 * Called from loadGraph.
     *
	 * @param nodeId id of the node
	 * @param edge edge to add
	 */
	public void addEdge(int nodeId, Edge edge) {

		if (this.adjacencyList[nodeId] != null) {
			Edge current = this.adjacencyList[nodeId];
			while (current.getNext() != null) {
				current = current.getNext();
			}
			current.setNext(edge);
			this.numEdges++;

		} else {
			this.adjacencyList[nodeId] = edge;
			this.numEdges++;
		}

	}

	/**
	 * Returns an integer id of the given city node
	 * @param city node of the graph
	 * @return its integer id
	 */
	public int getId(CityNode city) {

		int id = this.cityToId.get(city.getCity());

        return id;
    }

	/**
	 * Return the edges of the graph as a 2D array of points.
	 * Called from GUIApp to display the edges of the graph.
	 *
	 * @return a 2D array of Points.
	 * For each edge, we store an array of two Points, v1 and v2.
	 * v1 is the source vertex for this edge, v2 is the destination vertex.
	 * This info can be obtained from the adjacency list
	 *
	 * place in locations
	 */
	public Point[][] getEdges() {

		int j = 0;
		Point[][] edges2D = new Point[this.numEdges][2];

		for (int i = 0; i < numNodes; i++) {
			CityNode current = this.nodes[i];
			Point srcPoint = current.getLocation();

			Edge currEdge = this.adjacencyList[i];

			while (currEdge != null) {
				int id = currEdge.getNeighbor();
				CityNode dest = nodes[id];
				Point destPoint = dest.getLocation();
				edges2D[j][0] = srcPoint;
				edges2D[j++][1] = destPoint;
				currEdge = currEdge.getNext();
			}
		}

		return edges2D;
	}

	/**
	 * Get the nodes of the graph as a 1D array of Points.
	 * Used in GUIApp to display the nodes of the graph.
	 * @return a list of Points that correspond to nodes of the graph.
	 */
	public Point[] getNodes() {

		if (this.nodes == null) {
            System.out.println("Graph has no nodes. Write loadGraph method first. ");
            return null;
        }

		Point[] pNodes = new Point[numNodes];
	    int i = 0;
		while(i < this.numNodes) {
			CityNode currNode = this.nodes[i];
			pNodes[i] = currNode.getLocation();
			i++;
		}


		return pNodes;
	}

	/**
	 * Used in GUIApp to display the names of the airports.
	 * @return the list that contains the names of cities (that correspond
	 * to the nodes of the graph)
	 */
	public String[] getCities() {

		if (this.nodes == null) {
            System.out.println("Graph has no nodes. Write loadGraph method first. ");
            return null;
        }
		String[] labels = new String[nodes.length];

        int i = 0;
        while (i < this.numNodes) {
			CityNode currNode = this.nodes[i];
			labels[i] = currNode.getCity();
        	i++;
		}
		return labels;

	}

	/** Take a list of node ids on the path and return an array where each
	 * element contains two points (an edge between two consecutive nodes)
	 * @param pathOfNodes A list of node ids on the path
	 * @return array where each element is an array of 2 points
	 */
	public Point[][] getPath(List<Integer> pathOfNodes) {


		int i = 0;
		Point[][] edges2D = new Point[pathOfNodes.size()-1][2];

		while(i < pathOfNodes.size() - 1) {
			int id = pathOfNodes.get(i);
			int nextId = pathOfNodes.get(i+1);
			CityNode orig = this.nodes[id];
			CityNode dest = this.nodes[nextId];

			edges2D[i][0] = orig.getLocation();
			edges2D[i][1] = dest.getLocation();
			i++;
		}

        return edges2D;
	}

	/**
	 * Return the CityNode for the given nodeId
	 * @param nodeId id of the node
	 * @return CityNode
	 */
	public CityNode getNode(int nodeId) {
		return this.nodes[nodeId];
	}

	/**
	 * Take the location of the mouse click as a parameter, and return the node
	 * of the graph at this location. Needed in GUIApp class.
	 * @param loc the location of the mouse click
	 * @return reference to the corresponding CityNode
	 */
	public CityNode getNode(Point loc) {
//		System.out.println("Hello3");

		for (CityNode v : nodes) {
			Point p = v.getLocation();
			if ((Math.abs(loc.x - p.x) < EPS_DIST) && (Math.abs(loc.y - p.y) < EPS_DIST))
				return v;
		}
		return null;
	}
}