package graph;

import java.util.ArrayList;

/** The Driver class for project Dijkstra */
public class Driver {
    public static void main(String[] args) {
            // Initialize a graph
            Graph graph = new Graph();

            // Create an instance of the Dijkstra class
            Dijkstra dijkstra = new Dijkstra("USA.txt", graph);

//            CityNode a = new CityNode("SanFrancisco", 0.28, 2.31);
//            CityNode b = new CityNode("LosAngeles",  0.62, 1.6);
//            CityNode c = new CityNode("NewYork", 5.94, 2.71);
//            CityNode d = new CityNode("Phoenix", 1.47, 1.39);


//            dijkstra.computeShortestPath(a, b);


            // Create a graphical user interface and wait for user to click
            // on two cities:
            GUIApp app = new GUIApp(dijkstra, graph);
    }
}
