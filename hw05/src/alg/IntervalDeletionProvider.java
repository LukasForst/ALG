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

        StringBuilder sb = new StringBuilder();
        for (Node node : components) {
            sb.append(node.getValue()).append(" ");
        }

        System.err.println("Total found components: " + components.size());
        System.err.println(sb.toString());


        return solution;
    }

    private void startRemoving(Node node) {
        if (node == null)
            return;

        switch (interval.intervalState(node.getValue())) {
            case BIGGER:
                verifyConnection(node, IntervalResult.BIGGER);
                startRemoving(node.getLeft());
                break;
            case SMALLER:
                verifyConnection(node, IntervalResult.SMALLER);
                startRemoving(node.getRight());
                break;
            case IN_INTERVAL:
                removeNode(node);
                break;
        }
    }

    private void verifyConnection(Node node, IntervalResult result) {
        Node parent = node.getParent();
        if (parent == null) return;
        switch (result) {
            case BIGGER:
                if (interval.intervalState(parent.getValue()) == IntervalResult.SMALLER) {

                    if (parent.getRight() == node) {
                        parent.setRight(null);
                    } else {
                        throw new IllegalStateException("Parent is smaller but this is left child");
                    }

                    node.setParent(null);
                    components.add(node);
                    components.add(getMostTopNode(parent));
                }
                break;
            case SMALLER:
                if (interval.intervalState(parent.getValue()) == IntervalResult.BIGGER) {
                    if (parent.getLeft() == node) {
                        parent.setLeft(null);
                    } else {
                        throw new IllegalStateException("Parent is bugger but this is right child");
                    }

                    node.setParent(null);
                    components.add(node);
                    components.add(getMostTopNode(parent));
                }
                break;
            case IN_INTERVAL:
                break;
        }

    }

    private Node getMostTopNode(Node node) {
        while (node.getParent() != null) {
            node = node.getParent();
        }
        return node;
    }

    private Node getMostRight(Node node) {
        boolean flag = false;
        while (node.getRight() != null && !flag) {
            if (interval.intervalState(node.getRight().getValue()) != IntervalResult.IN_INTERVAL) {
                node = node.getRight();
                continue;
            }

            flag = true;
        }
        return flag ? node : null;
    }

    private Node getMostLeft(Node node) {
        boolean flag = false;
        while (node.getLeft() != null && !flag) {
            if (interval.intervalState(node.getLeft().getValue()) != IntervalResult.IN_INTERVAL) {
                node = node.getLeft();
                continue;
            }

            flag = true;
        }
        return flag ? node : null;
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
                    if (nextLeft != null) {
                        if (interval.intervalState(nextLeft.getValue()) == IntervalResult.IN_INTERVAL) {
                            removeNode(nextLeft);
                        } else {
                            Node possibleRemoval = getMostLeft(nextLeft);
                            if (possibleRemoval != null) removeNode(possibleRemoval);
                        }
                    }

                    break;
                case SMALLER:
                    throw new IllegalStateException("This should not happened, smaller node on the right side.");
                case IN_INTERVAL:
                    removeNode(right);
                    break;
            }
        }

        Node left = toRemove.getLeft();
        if (left != null) {
            switch (interval.intervalState(left.getValue())) {
                case BIGGER:
                    throw new IllegalStateException("This should not happened, bigger node on the left side.");
                case SMALLER:
                    left.setParent(null);
                    components.add(left);

                    Node nextRight = left.getRight();
                    if (nextRight != null) {
                        if (interval.intervalState(nextRight.getValue()) == IntervalResult.IN_INTERVAL) {
                            removeNode(nextRight);
                        } else {
                            Node possibleRemoval = getMostRight(nextRight);
                            if (possibleRemoval != null) removeNode(possibleRemoval);
                        }
                    }
                    break;
                case IN_INTERVAL:
                    removeNode(left);
                    break;
            }
        }
    }

}
