package civic;
import java.util.*;
public class Dijkstra {
	 static class Node
     implements Comparable<Node> {

 String vertex;
 int distance;

 Node(String vertex,
      int distance) {

     this.vertex = vertex;
     this.distance = distance;
 }

 @Override
 public int compareTo(Node other) {

     return Integer.compare(
             this.distance,
             other.distance);
 }
}

public static void shortestPath(
     Graph graph,
     String source) {

 HashMap<String, Integer>
         distance =
         new HashMap<>();

 PriorityQueue<Node> pq =
         new PriorityQueue<>();

 for (String vertex :
         graph.getAdjList()
                 .keySet()) {

     distance.put(
             vertex,
             Integer.MAX_VALUE);
 }

 distance.put(source, 0);

 pq.add(new Node(source, 0));

 while (!pq.isEmpty()) {

     Node current =
             pq.poll();

     for (Edge edge :
             graph.getAdjList()
                     .get(current.vertex)) {

         int newDistance =
                 distance.get(
                         current.vertex)
                         + edge.weight;

         if (newDistance <
                 distance.get(
                         edge.destination)) {

             distance.put(
                     edge.destination,
                     newDistance);

             pq.add(
                     new Node(
                             edge.destination,
                             newDistance));
         }
     }
 }

 System.out.println(
         "\nShortest Paths from "
                 + source);

 for (String vertex :
         distance.keySet()) {

     System.out.println(
             vertex +
                     " -> " +
                     distance.get(vertex));
 }
}
}
