package civic;

public class AVLTree {
    AVLNode root;

    int height(AVLNode node) {
        if (node == null) return 0;
        return node.height;
    }

    int getBalance(AVLNode node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    AVLNode rightRotate(AVLNode y) {
        AVLNode x  = y.left;
        AVLNode t2 = x.right;
        x.right = y;
        y.left  = t2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    AVLNode leftRotate(AVLNode x) {
        AVLNode y  = x.right;
        AVLNode t2 = y.left;
        y.left  = x;
        x.right = t2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    AVLNode insert(AVLNode node, Incident incident) {
        if (node == null) return new AVLNode(incident);

        if      (incident.incidentId < node.incident.incidentId) node.left  = insert(node.left,  incident);
        else if (incident.incidentId > node.incident.incidentId) node.right = insert(node.right, incident);
        else return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        if (balance > 1  && incident.incidentId < node.left.incident.incidentId)  return rightRotate(node);
        if (balance < -1 && incident.incidentId > node.right.incident.incidentId) return leftRotate(node);
        if (balance > 1  && incident.incidentId > node.left.incident.incidentId)  { node.left = leftRotate(node.left);  return rightRotate(node); }
        if (balance < -1 && incident.incidentId < node.right.incident.incidentId) { node.right = rightRotate(node.right); return leftRotate(node); }

        return node;
    }

    public void insertIncident(Incident incident) {
        root = insert(root, incident);
    }

    // ── Delete ────────────────────────────────────────────────────────

    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) current = current.left;
        return current;
    }

    AVLNode delete(AVLNode node, int id) {
        if (node == null) return null;

        if      (id < node.incident.incidentId) node.left  = delete(node.left,  id);
        else if (id > node.incident.incidentId) node.right = delete(node.right, id);
        else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                AVLNode successor = minValueNode(node.right);
                node.incident = successor.incident;
                node.right = delete(node.right, successor.incident.incidentId);
            }
        }

        if (node == null) return null;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        if (balance > 1  && getBalance(node.left)  >= 0) return rightRotate(node);
        if (balance > 1  && getBalance(node.left)  <  0) { node.left  = leftRotate(node.left);  return rightRotate(node); }
        if (balance < -1 && getBalance(node.right) <= 0) return leftRotate(node);
        if (balance < -1 && getBalance(node.right) >  0) { node.right = rightRotate(node.right); return leftRotate(node); }

        return node;
    }

    public void deleteIncident(int id) {
        root = delete(root, id);
    }

    // ── Search ────────────────────────────────────────────────────────

    public Incident search(int id) {
        AVLNode current = root;
        while (current != null) {
            if      (id == current.incident.incidentId) return current.incident;
            else if (id <  current.incident.incidentId) current = current.left;
            else                                         current = current.right;
        }
        return null;
    }

    // ── Display ───────────────────────────────────────────────────────

    public void displayInorder(AVLNode node) {
        if (node != null) {
            displayInorder(node.left);
            System.out.println(node.incident);
            displayInorder(node.right);
        }
    }

    public void displayAll() {
        displayInorder(root);
    }
}
