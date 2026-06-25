package civic;
import java.util.*;

public class Graph {
	private HashMap<String, ArrayList<Edge>> adjList;

    public Graph() {
        adjList = new HashMap<>();
    }

    public void addVertex(String vertex) {

        adjList.putIfAbsent(vertex,
                new ArrayList<>());
    }

    public void addEdge(String source,
                        String destination,
                        int weight) {

        addVertex(source);
        addVertex(destination);

        adjList.get(source)
                .add(new Edge(destination, weight));

        adjList.get(destination)
                .add(new Edge(source, weight));
    }

    public HashMap<String,
            ArrayList<Edge>> getAdjList() {

        return adjList;
    }

    public void displayGraph() {

        for (String vertex : adjList.keySet()) {

            System.out.print(vertex + " -> ");

            for (Edge edge :
                    adjList.get(vertex)) {

                System.out.print(
                        "(" + edge.destination +
                                ", " + edge.weight + ") ");
            }

            System.out.println();
        }
    }
}
