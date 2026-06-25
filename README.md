# CivicEye — Civic Incident Management System

A Java console application that manages civic incidents using AVL trees, a MaxHeap priority queue, and graph-based city routing — now connected to a **SQLite database** for persistent storage.

---

## What's New: Database Integration

| Feature | Details |
|---|---|
| **Database** | SQLite (`civiceye.db` created automatically in project root) |
| **Driver** | `lib/sqlite-jdbc.jar` (bundled) |
| **Persistence** | Incidents & teams survive across sessions |
| **New options** | Delete Incident (4), Add Team (12), View Teams (13), Delete Team (14), DB Stats (16) |

---

## Requirements

- **Java JDK 11 or higher** — must include `javac` (not just JRE)
  - Download: https://adoptium.net/

---

## How to Build & Run

### Linux / macOS
```bash
chmod +x compile.sh run.sh
./compile.sh
./run.sh
```

### Windows
```
compile.bat
run.bat
```

### Eclipse IDE
1. File → Import → Existing Projects into Workspace → select `CivicEye/`
2. Right-click project → Build Path → Configure Build Path → Libraries → Add External JARs → select `lib/sqlite-jdbc.jar`
3. Run `Main.java`

### IntelliJ IDEA
1. File → Open → select `CivicEye/`
2. File → Project Structure → Modules → Dependencies → + → JARs → select `lib/sqlite-jdbc.jar`
3. Run `Main.java`

---

## Menu Reference

```
 --- Incident Management ---
  1. Add Incident             ← saved to DB automatically
  2. Search Incident
  3. Display All Incidents
  4. Delete Incident          ← removed from DB
 --- Priority Queue ---
  5. Add Incident To Priority Queue
  6. View Priority Queue
  7. Process Highest Priority Incident
 --- City Graph & Routing ---
  8. Display City Graph
  9. BFS Traversal
 10. DFS Traversal
 11. Dijkstra Shortest Path
 --- Teams ---
 12. Add Team                 ← saved to DB
 13. View All Teams           ← loaded from DB
 14. Delete Team              ← removed from DB
 --- Utilities ---
 15. Merge Sort Demo
 16. Database Stats
 17. Exit
```

---

## Project Structure

```
CivicEye/
├── src/civic/
│   ├── Main.java             ← updated (menu + DB calls)
│   ├── DatabaseManager.java  ← NEW: all SQLite logic
│   ├── AVLTree.java          ← updated (delete support added)
│   ├── Incident.java
│   ├── AVLNode.java
│   ├── MaxHeap.java
│   ├── PriorityIncident.java
│   ├── Graph.java
│   ├── Edge.java
│   ├── BFS.java
│   ├── DFS.java
│   ├── Dijkstra.java
│   ├── MergeSort.java
│   └── Team.java
├── lib/
│   └── sqlite-jdbc.jar       ← SQLite JDBC driver (bundled)
├── compile.sh / compile.bat
├── run.sh / run.bat
└── civiceye.db               ← created on first run
```
