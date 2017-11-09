package alg;

import java.lang.reflect.Array;
import java.lang.reflect.Parameter;
import java.util.*;

public class MatrixSolver {
    private Data data;
    private final Map<Integer, List<Integer>> connections;
    private final List<Integer> possibleSockets;

    private final List<Integer> sockets;

    public MatrixSolver(Data data) {
        this.data = data;
        connections = data.getConnections();
        possibleSockets = data.getPossibleSockets();

        int initialCapacity = data.getNumberOfNodes() / 2;
        sockets = new ArrayList<>(initialCapacity);
    }

    public List<Integer> solve() {
        for (int node : possibleSockets) {
            if (exploreFrom(node)) {
                break;
            } else {
                sockets.clear();
            }
        }

        Collections.sort(sockets);
        return sockets;
    }

    private boolean exploreFrom(int node) {
        int[] array = new int[2];
        int i = 0;
        for (int root : connections.get(node)) {
            array[i++] = root;
        }

        int leftSize = 0;
        int rightSize = 0;

        Queue<Pair> leftExplored = new ArrayDeque<>();
        Queue<Pair> rightExplored = new ArrayDeque<>();

        leftExplored.add(new Pair(array[0], node));
        rightExplored.add(new Pair(array[1], node));

        while (leftSize == rightSize) {
            Queue<Pair> nextDepthRight = oneDepthExplore(rightExplored);
            Queue<Pair> nextDepthLeft = oneDepthExplore(leftExplored);

            rightExplored = nextDepthRight;
            leftExplored = nextDepthLeft;

            rightSize = rightExplored.size();
            leftSize = leftExplored.size();

            if (rightSize == leftSize) {
                checkIfSocket(leftExplored, rightExplored);
            }

            if (leftSize == 0 || rightSize == 0) {
                break;
            }
        }
        sockets.add(node);
        return rightSize == 0 && leftSize == 0;
    }

    private void checkIfSocket(Queue<Pair> leftExplored, Queue<Pair> rightExplored) {
        ArrayList<Pair> ex = new ArrayList<>(rightExplored.size() + 10);
        ex.addAll(rightExplored);

        for (Pair explored : ex) {
            int value = explored.getValue();
            if (leftExplored.contains(explored) && connections.get(value).size() == 2) {
                sockets.add(value);
                rightExplored.remove(explored);
                leftExplored.remove(explored);
            }
        }
    }

    private Queue<Pair> oneDepthExplore(Queue<Pair> toBeExplored) {
        Queue<Pair> toBeFilled = new ArrayDeque<>();
        Pair pair = toBeExplored.poll();
        while (pair != null) {
            exploreChildren(pair, toBeFilled);
            pair = toBeExplored.poll();
        }
        return toBeFilled;
    }


    private void exploreChildren(Pair node, Queue<Pair> outOneDepthExplored) {
        List<Integer> children = connections.get(node.getValue());

        for (int child : children) {
            if (child != node.getParent()) {
                Pair pair = new Pair(child, node.getValue());
                outOneDepthExplored.add(pair);
            }
        }
    }
}

class Pair {
    private final int value;
    private final int parent;

    public Pair(int value, int parent) {
        this.value = value;
        this.parent = parent;
    }

    public int getParent() {
        return parent;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return value == ((Pair) o).value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}