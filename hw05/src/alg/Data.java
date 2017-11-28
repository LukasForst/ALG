package alg;

import java.util.Collection;

public class Data {
    private final Node treeRoot;
    private final Collection<Integer> allNodesValues;
    private final int numberOfNodesInTree;
    private final Interval interval;

    public Data(Node treeRoot, int numberOfNodesInTree, Interval interval, Collection<Integer> allNodesValues) {
        this.treeRoot = treeRoot;
        this.numberOfNodesInTree = numberOfNodesInTree;
        this.interval = interval;
        this.allNodesValues = allNodesValues;
    }

    public Collection<Integer> getAllNodesValues() {
        return allNodesValues;
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
