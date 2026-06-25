package civic;
import java.util.*;

public class DFS {
	 public static void traverse(
	            Graph graph,
	            String start) {

	        HashSet<String> visited =
	                new HashSet<>();

	        dfs(graph, start, visited);
	    }

	    private static void dfs(
	            Graph graph,
	            String current,
	            HashSet<String> visited) {

	        visited.add(current);

	        System.out.print(
	                current + " ");

	        for (Edge edge :
	                graph.getAdjList()
	                        .get(current)) {

	            if (!visited.contains(
	                    edge.destination)) {

	                dfs(graph,
	                        edge.destination,
	                        visited);
	            }
	        }
	    }
}
