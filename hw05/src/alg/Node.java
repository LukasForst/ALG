package alg;

public class Node implements Comparable<Node> {
    private final int value;

    private int depth;

    private Node left;
    private Node right;
    private Node parent;

    public Node(int value) {
        this.value = value;
    }

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

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
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
