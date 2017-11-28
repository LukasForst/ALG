package alg;


import java.util.*;

public class IntervalDeletionProvider {
    private final Interval interval;
    private final Node root;

    private final int numberOfAllNodes;
    private final TreeSet<Node> components;
    private final Collection<Integer> allNodesValues;
    private int numberOfDeletedNodes;

    public IntervalDeletionProvider(Data data) {
        interval = data.getInterval();
        root = data.getTreeRoot();
        numberOfAllNodes = data.getNumberOfNodesInTree();
        allNodesValues = data.getAllNodesValues();

        numberOfDeletedNodes = 0;
        components = new TreeSet<>();
    }

    public AbstractMap.SimpleEntry<Integer, Integer> solve() {
        AbstractMap.SimpleEntry<Integer, Integer> solution = null;

        startRemoving(root);

        System.err.println("Total removed nodes: " + numberOfDeletedNodes);

        Node newRoot = merge(0, allNodesValues.size() - 1);

        return getResult(newRoot);
    }

    private Node merge(int min, int max) {
        Node root = getRootComponent(min, max);
        if (root == null)
            return null;

        // TODO: 28.11.2017 check last/first
//        if((root == components.last() && components.size() == 1)){
//            components.remove(root);
//            return root;
//        }
//        components.remove(root);
//        if(root == components.first() || root == components.last()) return root;

        AbstractMap.SimpleEntry<Integer, Integer> minMax = removeSubTree(root);

        root.insertLeft(merge(min, minMax.getKey() - 1));
        root.insertRight(merge(minMax.getValue() + 1, max));

        return root;
    }

    private AbstractMap.SimpleEntry<Integer, Integer> getResult(Node node) {
        int maxDepth = 0;
        Queue<Node> toVisit = new LinkedList<>();

        toVisit.add(node);
        int[] count = new int[numberOfAllNodes - numberOfDeletedNodes];
        node.setDepth(0);
        count[0] = 1;

        while (!toVisit.isEmpty()) {
            Node processed = toVisit.poll();
            int depth = processed.getDepth();
            maxDepth = Integer.max(depth, maxDepth);

            count[depth]++;

            if (processed.getLeft() != null) {
                Node left = processed.getLeft();
                left.setDepth(depth + 1);
                toVisit.add(left);
            }

            if (processed.getRight() != null) {
                Node right = processed.getRight();
                right.setDepth(depth + 1);
                toVisit.add(right);
            }
        }

        int nodesInDepth = count[maxDepth - 1];
        return new AbstractMap.SimpleEntry<>(maxDepth, nodesInDepth);
    }

    private AbstractMap.SimpleEntry<Integer, Integer> removeSubTree(Node subtreeRoot) {
        Stack<Node> toVisit = new Stack<>();
        toVisit.push(subtreeRoot);
        int minValueInSubtree = Integer.MAX_VALUE;
        int maxValueInSubtree = Integer.MIN_VALUE;
        while (!toVisit.empty()) {
            Node processed = toVisit.pop();

            minValueInSubtree = Integer.min(minValueInSubtree, processed.getValue());
            maxValueInSubtree = Integer.max(maxValueInSubtree, processed.getValue());

            if (processed.getLeft() != null) toVisit.push(processed.getLeft());
            if (processed.getRight() != null) toVisit.push(processed.getRight());
        }

        int index = -1;
        int minIndex = Integer.MAX_VALUE;
        int maxIndex = Integer.MIN_VALUE;
        for (Integer allNodesValue : allNodesValues) {
            index++;

            int next = allNodesValue;
            if (minValueInSubtree > next)
                continue;

            if (minValueInSubtree == next)
                minIndex = index;

            if (maxValueInSubtree == next) {
                maxIndex = index;
                break;
            }
        }

        return new AbstractMap.SimpleEntry<>(minIndex, maxIndex);
    }

    private Node getRootComponent(int min, int max) {
        if (min > max) return null;
        int nodesCount = max - min + 1;

        int index = ((1 + nodesCount) / 2) + min;

        Iterator<Integer> iter = allNodesValues.iterator();
        int idx = 0;
        while (iter.hasNext() && idx < min) {
            iter.next();
            idx++;
        }

        int i = min + 1;
        while (iter.hasNext() && i != index) {
            i++;
            iter.next();
        }
        int nodeValue = iter.next();

        Node previous = null;
        Node current = null;
        for (Node component : components) {
            current = component;

            if (previous == null) {
                previous = current;
                if (previous.getValue() > nodeValue) return previous;
            }

            if (previous.getValue() > nodeValue || current.getValue() < nodeValue) {
                previous = current;
            } else {
                if (containsNode(previous, nodeValue)) {
                    return previous;
                } else if (containsNode(current, nodeValue)) {
                    return current;
                } else {
                    throw new IllegalStateException("Left component is smaller and right is bigger but they does not contain " + nodeValue);
                }
            }
        }
        return current;
    }
//    private Node getRootComponent(int[] nodesValues, int min, int max) {
//        int index = (1 + max - min) / 2;
//        int nodeValue = nodesValues[index];
//
//        Node previous = null;
//        for (Node componentRoot : components) {
//
//            if (previous != null && previous.getValue() <= nodeValue && componentRoot.getValue() >= nodeValue) {
//                if (containsNode(previous, nodeValue)) {
//                    return previous;
//                } else if (containsNode(componentRoot, nodeValue)) {
//                    return componentRoot;
//                } else {
//                    throw new IllegalStateException("Left component is smaller and right is bigger but they does not contain " + nodeValue);
//                }
//            }
//
//            previous = componentRoot;
//        }
//        throw new IllegalStateException("Value was not found");
//    }

    private boolean containsNode(Node startNode, int value) {
        if (startNode == null)
            return false;
        Stack<Node> stackToVisit = new Stack<>();
        stackToVisit.push(startNode);
        while (!stackToVisit.empty()) {
            Node processed = stackToVisit.pop();
            if (processed.getValue() == value)
                return true;

            if (processed.getLeft() != null)
                stackToVisit.push(processed.getLeft());

            if (processed.getRight() != null)
                stackToVisit.push(processed.getRight());
        }

        return false;
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
        numberOfDeletedNodes++;
        allNodesValues.remove(toRemove.getValue());

        Node parent = toRemove.getParent();
        if (parent != null) {
            if (parent.getLeft() == toRemove) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
            toRemove.setParent(null);
        }

        Node right = toRemove.getRight();
        if (right != null) {
            switch (interval.intervalState(right.getValue())) {
                case BIGGER:
                    right.setParent(null);
                    toRemove.setRight(null);
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
                    toRemove.setLeft(null);
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
