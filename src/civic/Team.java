package civic;

public class Team {
	String teamName;
    String location;

    public Team(String teamName,
                String location) {

        this.teamName = teamName;
        this.location = location;
    }

    @Override
    public String toString() {
        return teamName + " at " + location;
    }
}
