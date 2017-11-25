package alg;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class IntervalDeletionProvider {
    private final Interval interval;
    private final Node root;

    private final Collection<Node> components;
    private final Collection<Integer> deletedNodes;

    public IntervalDeletionProvider(Data data) {
        interval = data.getInterval();
        root = data.getTreeRoot();
        components = new TreeSet<>();

        deletedNodes = new ArrayList<>(); // TODO: 25.11.2017 delete this
    }

    public AbstractMap.SimpleEntry<Integer, Integer> solve() {
        AbstractMap.SimpleEntry<Integer, Integer> solution = null;

        startRemoving(root);

        System.err.println("Total removed nodes: " + deletedNodes.size());
        System.err.println("Total found components: " + components.size());

        return solution;
    }

    private void startRemoving(Node node) {
        if (node == null)
            return;

        switch (interval.intervalState(node.getValue())) {
            case BIGGER:
                startRemoving(node.getLeft());
                break;
            case SMALLER:
                startRemoving(node.getRight());
                break;
            case IN_INTERVAL:
                removeNode(root);
                break;
        }
    }

    private void removeNode(Node toRemove) {
        deletedNodes.add(toRemove.getValue());

        Node parent = toRemove.getParent();
        if (parent != null) {
            if (parent.getLeft() == toRemove) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        }

        Node right = toRemove.getRight();
        if (right != null) {
            switch (interval.intervalState(right.getValue())) {
                case BIGGER:
                    right.setParent(null);
                    components.add(right);

                    Node nextLeft = right.getLeft();
                    if (nextLeft != null && interval.intervalState(nextLeft.getValue()) == IntervalResult.IN_INTERVAL) {
                        removeNode(nextLeft);
                    }

                    break;
                case SMALLER:
                    throw new IllegalStateException("This should not happen, smaller node on the right side.");
                case IN_INTERVAL:
                    removeNode(right);
                    break;
            }
        }

        Node left = toRemove.getLeft();
        if (left != null) {
            switch (interval.intervalState(left.getValue())) {
                case BIGGER:
                    throw new IllegalStateException("This should not happen, bigger node on the left side.");
                case SMALLER:
                    left.setParent(null);
                    components.add(left);

                    Node nextRight = left.getRight();
                    if (nextRight != null && interval.intervalState(nextRight.getValue()) == IntervalResult.IN_INTERVAL) {
                        removeNode(nextRight);
                    }
                    break;
                case IN_INTERVAL:
                    removeNode(left);
                    break;
            }
        }
    }

}
