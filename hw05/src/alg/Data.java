package alg;

public class Data {
    private final Node treeRoot;
    private final int numberOfNodesInTree;
    private final Interval interval;

    public Data(Node treeRoot, int numberOfNodesInTree, Interval interval) {
        this.treeRoot = treeRoot;
        this.numberOfNodesInTree = numberOfNodesInTree;
        this.interval = interval;
    }

    public Node getTreeRoot() {
        return treeRoot;
    }

    public int getNumberOfNodesInTree() {
        return numberOfNodesInTree;
    }

    public Interval getInterval() {
        return interval;
    }

    @Override
    public String toString() {
        return "Data{" +
                "treeRoot=" + treeRoot +
                ", numberOfNodesInTree=" + numberOfNodesInTree +
                ", interval=" + interval +
                '}';
    }
}
