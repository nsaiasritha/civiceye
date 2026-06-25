package civic;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // ── Data structures ───────────────────────────────────────────
        AVLTree  incidentTree  = new AVLTree();
        MaxHeap  priorityQueue = new MaxHeap();
        Graph    cityGraph     = new Graph();

        // ── Database ──────────────────────────────────────────────────
        DatabaseManager db = new DatabaseManager();

        // Load persisted incidents into the AVL tree on startup
        List<Incident> saved = db.loadAllIncidents();
        for (Incident i : saved) {
            incidentTree.insertIncident(i);
        }
        if (!saved.isEmpty()) {
            System.out.println("[DB] Loaded " + saved.size() + " incident(s) from database.");
        }

        // Load persisted teams
        List<Team> savedTeams = db.loadAllTeams();
        if (!savedTeams.isEmpty()) {
            System.out.println("[DB] Loaded " + savedTeams.size() + " team(s) from database.");
        }

        // ── Sample city road network ──────────────────────────────────
        cityGraph.addEdge("Vijayawada",  "Guntur",      35);
        cityGraph.addEdge("Vijayawada",  "Tenali",      30);
        cityGraph.addEdge("Guntur",      "Mangalgiri",  15);
        cityGraph.addEdge("Tenali",      "Mangaligiri", 28);
        cityGraph.addEdge("Tenali",      "Tadepalli",   10);
        cityGraph.addEdge("Mangalgiri",  "Tadepalli",   12);

        int choice;

        do {
            System.out.println("\n==================================");
            System.out.println("         CIVICEYE SYSTEM");
            System.out.println("==================================");
            System.out.println(" --- Incident Management ---");
            System.out.println("  1. Add Incident");
            System.out.println("  2. Search Incident");
            System.out.println("  3. Display All Incidents");
            System.out.println("  4. Delete Incident");
            System.out.println(" --- Priority Queue ---");
            System.out.println("  5. Add Incident To Priority Queue");
            System.out.println("  6. View Priority Queue");
            System.out.println("  7. Process Highest Priority Incident");
            System.out.println(" --- City Graph & Routing ---");
            System.out.println("  8. Display City Graph");
            System.out.println("  9. BFS Traversal");
            System.out.println(" 10. DFS Traversal");
            System.out.println(" 11. Dijkstra Shortest Path");
            System.out.println(" --- Teams ---");
            System.out.println(" 12. Add Team");
            System.out.println(" 13. View All Teams");
            System.out.println(" 14. Delete Team");
            System.out.println(" --- Utilities ---");
            System.out.println(" 15. Merge Sort Demo");
            System.out.println(" 16. Database Stats");
            System.out.println(" 17. Exit");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();

            switch (choice) {

                // ── 1: Add Incident ───────────────────────────────────
                case 1: {
                    System.out.print("Incident ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Type: ");
                    String type = sc.nextLine();

                    System.out.print("Location: ");
                    String location = sc.nextLine();

                    System.out.print("Severity (Critical/High/Medium/Low): ");
                    String severity = sc.nextLine();

                    Incident incident = new Incident(id, type, location, severity);
                    incidentTree.insertIncident(incident);

                    if (db.saveIncident(incident)) {
                        System.out.println("Incident Added & Saved to Database.");
                    } else {
                        System.out.println("Incident Added (in-memory only; DB save failed).");
                    }
                    break;
                }

                // ── 2: Search Incident ────────────────────────────────
                case 2: {
                    System.out.print("Enter Incident ID: ");
                    int searchId = sc.nextInt();
                    Incident found = incidentTree.search(searchId);
                    if (found != null)
                        System.out.println(found);
                    else
                        System.out.println("Incident Not Found.");
                    break;
                }

                // ── 3: Display All Incidents ──────────────────────────
                case 3: {
                    System.out.println("\nAll Incidents (sorted by ID):");
                    incidentTree.displayAll();
                    break;
                }

                // ── 4: Delete Incident ────────────────────────────────
                case 4: {
                    System.out.print("Enter Incident ID to delete: ");
                    int delId = sc.nextInt();
                    Incident target = incidentTree.search(delId);
                    if (target == null) {
                        System.out.println("Incident Not Found.");
                        break;
                    }
                    incidentTree.deleteIncident(delId);
                    if (db.deleteIncident(delId)) {
                        System.out.println("Incident " + delId + " deleted from memory and database.");
                    } else {
                        System.out.println("Incident " + delId + " deleted from memory (DB delete failed).");
                    }
                    break;
                }

                // ── 5: Add to Priority Queue ──────────────────────────
                case 5: {
                    System.out.print("Incident ID: ");
                    int pid = sc.nextInt();
                    Incident priorityIncident = incidentTree.search(pid);

                    if (priorityIncident == null) {
                        System.out.println("Incident Not Found.");
                        break;
                    }

                    int priority = switch (priorityIncident.severity.toLowerCase()) {
                        case "critical" -> 4;
                        case "high"     -> 3;
                        case "medium"   -> 2;
                        default         -> 1;
                    };

                    priorityQueue.insert(new PriorityIncident(priorityIncident, priority));
                    System.out.println("Added To Priority Queue with priority " + priority + ".");
                    break;
                }

                // ── 6: View Priority Queue ────────────────────────────
                case 6: {
                    System.out.println("\nPriority Queue:");
                    priorityQueue.display();
                    break;
                }

                // ── 7: Process Highest Priority ───────────────────────
                case 7: {
                    PriorityIncident highest = priorityQueue.extractMax();
                    if (highest != null) {
                        System.out.println("Processing:");
                        System.out.println(highest.incident);
                    } else {
                        System.out.println("Queue Empty.");
                    }
                    break;
                }

                // ── 8: Display City Graph ─────────────────────────────
                case 8: {
                    cityGraph.displayGraph();
                    break;
                }

                // ── 9: BFS ────────────────────────────────────────────
                case 9: {
                    System.out.print("Start Vertex: ");
                    String bfsStart = sc.next();
                    System.out.println("\nBFS Traversal:");
                    BFS.traverse(cityGraph, bfsStart);
                    break;
                }

                // ── 10: DFS ───────────────────────────────────────────
                case 10: {
                    System.out.print("Start Vertex: ");
                    String dfsStart = sc.next();
                    System.out.println("\nDFS Traversal:");
                    DFS.traverse(cityGraph, dfsStart);
                    System.out.println();
                    break;
                }

                // ── 11: Dijkstra ──────────────────────────────────────
                case 11: {
                    System.out.print("Source Vertex: ");
                    String source = sc.next();
                    Dijkstra.shortestPath(cityGraph, source);
                    break;
                }

                // ── 12: Add Team ──────────────────────────────────────
                case 12: {
                    sc.nextLine();
                    System.out.print("Team Name: ");
                    String teamName = sc.nextLine();
                    System.out.print("Team Location: ");
                    String teamLoc = sc.nextLine();
                    Team team = new Team(teamName, teamLoc);
                    if (db.saveTeam(team)) {
                        System.out.println("Team \"" + teamName + "\" saved to database.");
                    } else {
                        System.out.println("DB save failed.");
                    }
                    break;
                }

                // ── 13: View All Teams ────────────────────────────────
                case 13: {
                    List<Team> teams = db.loadAllTeams();
                    if (teams.isEmpty()) {
                        System.out.println("No teams recorded.");
                    } else {
                        System.out.println("\nAll Teams:");
                        for (Team t : teams) System.out.println("  " + t);
                    }
                    break;
                }

                // ── 14: Delete Team ───────────────────────────────────
                case 14: {
                    sc.nextLine();
                    System.out.print("Team Name to delete: ");
                    String delTeam = sc.nextLine();
                    if (db.deleteTeam(delTeam)) {
                        System.out.println("Team \"" + delTeam + "\" deleted.");
                    } else {
                        System.out.println("Team not found in database.");
                    }
                    break;
                }

                // ── 15: Merge Sort Demo ───────────────────────────────
                case 15: {
                    Incident[] arr = {
                        new Incident(105, "Accident",      "ZoneA", "High"),
                        new Incident(101, "Road Damage",   "ZoneB", "Medium"),
                        new Incident(103, "Water Leakage", "ZoneC", "Low"),
                        new Incident(102, "Fire Alert",    "ZoneD", "Critical")
                    };
                    MergeSort.sort(arr, 0, arr.length - 1);
                    System.out.println("\nSorted Incidents:");
                    for (Incident i : arr) System.out.println(i);
                    break;
                }

                // ── 16: Database Stats ────────────────────────────────
                case 16: {
                    System.out.println("\nDatabase Statistics:");
                    System.out.println("  Total Incidents : " + db.incidentCount());
                    System.out.println("  Total Teams     : " + db.teamCount());
                    System.out.println("  Database file   : civiceye.db");
                    break;
                }

                // ── 17: Exit ──────────────────────────────────────────
                case 17: {
                    db.close();
                    System.out.println("\nThank You For Using CivicEye");
                    break;
                }

                default:
                    System.out.println("Invalid Choice.");
            }

        } while (choice != 17);

        sc.close();
    }
}
