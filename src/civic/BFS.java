package civic;
import java.util.*;
public class BFS {
	 public static void traverse(
	            Graph graph,
	            String start) {

	        HashSet<String> visited =
	                new HashSet<>();

	        Queue<String> queue =
	                new LinkedList<>();

	        visited.add(start);
	        queue.add(start);

	        while (!queue.isEmpty()) {

	            String current =
	                    queue.poll();

	            System.out.print(
	                    current + " ");

	            for (Edge edge :
	                    graph.getAdjList()
	                            .get(current)) {

	                if (!visited.contains(
	                        edge.destination)) {

	                    visited.add(
	                            edge.destination);

	                    queue.add(
	                            edge.destination);
	                }
	            }
	        }

	        System.out.println();
	    }

}
