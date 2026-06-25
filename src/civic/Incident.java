package civic;

public class Incident {
	int incidentId;
    String type;
    String location;
    String severity;

    public Incident(int incidentId, String type,
                    String location, String severity) {

        this.incidentId = incidentId;
        this.type = type;
        this.location = location;
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "Incident ID: " + incidentId +
                ", Type: " + type +
                ", Location: " + location +
                ", Severity: " + severity;
    }

}
