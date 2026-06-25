package civic;

public class AVLNode {
	Incident incident;

    AVLNode left;
    AVLNode right;

    int height;

    public AVLNode(Incident incident) {
        this.incident = incident;
        this.height = 1;
    }
}
