package civic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseManager — handles all SQLite persistence for CivicEye.
 *
 * Tables:
 *   incidents  (incident_id, type, location, severity)
 *   teams      (team_name, location)
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:civiceye.db";
    private Connection conn;

    // ------------------------------------------------------------------ //
    //  Lifecycle
    // ------------------------------------------------------------------ //

    public DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);
            createTables();
            System.out.println("[DB] Connected to civiceye.db");
        } catch (Exception e) {
            System.out.println("[DB ERROR] Could not connect: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("[DB] Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("[DB ERROR] Close failed: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------ //
    //  Schema
    // ------------------------------------------------------------------ //

    private void createTables() throws SQLException {
        String incidents = """
            CREATE TABLE IF NOT EXISTS incidents (
                incident_id  INTEGER PRIMARY KEY,
                type         TEXT    NOT NULL,
                location     TEXT    NOT NULL,
                severity     TEXT    NOT NULL
            );
        """;

        String teams = """
            CREATE TABLE IF NOT EXISTS teams (
                team_name  TEXT PRIMARY KEY,
                location   TEXT NOT NULL
            );
        """;

        try (Statement st = conn.createStatement()) {
            st.execute(incidents);
            st.execute(teams);
        }
    }

    // ------------------------------------------------------------------ //
    //  Incidents — CRUD
    // ------------------------------------------------------------------ //

    /** Insert or replace an incident in the DB. */
    public boolean saveIncident(Incident incident) {
        String sql = "INSERT OR REPLACE INTO incidents (incident_id, type, location, severity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, incident.incidentId);
            ps.setString(2, incident.type);
            ps.setString(3, incident.location);
            ps.setString(4, incident.severity);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("[DB ERROR] saveIncident: " + e.getMessage());
            return false;
        }
    }

    /** Load all incidents from DB. */
    public List<Incident> loadAllIncidents() {
        List<Incident> list = new ArrayList<>();
        String sql = "SELECT incident_id, type, location, severity FROM incidents ORDER BY incident_id";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Incident(
                    rs.getInt("incident_id"),
                    rs.getString("type"),
                    rs.getString("location"),
                    rs.getString("severity")
                ));
            }
        } catch (SQLException e) {
            System.out.println("[DB ERROR] loadAllIncidents: " + e.getMessage());
        }
        return list;
    }

    /** Delete a single incident by ID. */
    public boolean deleteIncident(int id) {
        String sql = "DELETE FROM incidents WHERE incident_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("[DB ERROR] deleteIncident: " + e.getMessage());
            return false;
        }
    }

    // ------------------------------------------------------------------ //
    //  Teams — CRUD
    // ------------------------------------------------------------------ //

    /** Insert or replace a team in the DB. */
    public boolean saveTeam(Team team) {
        String sql = "INSERT OR REPLACE INTO teams (team_name, location) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, team.teamName);
            ps.setString(2, team.location);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("[DB ERROR] saveTeam: " + e.getMessage());
            return false;
        }
    }

    /** Load all teams from DB. */
    public List<Team> loadAllTeams() {
        List<Team> list = new ArrayList<>();
        String sql = "SELECT team_name, location FROM teams ORDER BY team_name";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Team(
                    rs.getString("team_name"),
                    rs.getString("location")
                ));
            }
        } catch (SQLException e) {
            System.out.println("[DB ERROR] loadAllTeams: " + e.getMessage());
        }
        return list;
    }

    /** Delete a team by name. */
    public boolean deleteTeam(String teamName) {
        String sql = "DELETE FROM teams WHERE team_name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, teamName);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("[DB ERROR] deleteTeam: " + e.getMessage());
            return false;
        }
    }

    // ------------------------------------------------------------------ //
    //  Utility
    // ------------------------------------------------------------------ //

    public int incidentCount() {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM incidents")) {
            return rs.getInt(1);
        } catch (SQLException e) {
            return 0;
        }
    }

    public int teamCount() {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM teams")) {
            return rs.getInt(1);
        } catch (SQLException e) {
            return 0;
        }
    }
}
