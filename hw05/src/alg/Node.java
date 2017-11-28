package alg;

public class Node implements Comparable<Node> {
    private final int value;

    private int depth;

    private Node left;
    private Node right;
    private Node parent;

    public Node(int value, int depth, Node parent) {
        this.value = value;
        this.depth = depth;
        this.parent = parent;
    }

    public int refreshDepth() {
        int tmpDepth = 0;
        Node node = this;
        while (node.getParent() != null) {
            tmpDepth++;
            node = node.getParent();
        }
        depth = tmpDepth;
        return tmpDepth;
    }

    public void insertValue(int value) {
        if (value > this.value) {
            if (right == null) {
                right = new Node(value, depth + 1, this);
            } else {
                right.insertValue(value);
            }
        } else {
            if (left == null) {
                left = new Node(value, depth + 1, this);
            } else {
                left.insertValue(value);
            }
        }
    }

    public void insertLeft(Node node) {
        if (node == null) return;
        Node current = this;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }

        current.setLeft(node);
    }

    public void insertRight(Node node) {
        if (node == null) return;
        Node current = this;
        while (current.getRight() != null) {
            current = current.getRight();
        }

        current.setRight(node);
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node node) {
        this.left = node;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node node) {
        this.right = node;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(Node o) {
        return value - o.getValue();
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", depth=" + depth +
                '}';
    }
}
