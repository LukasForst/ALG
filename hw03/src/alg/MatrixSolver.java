package alg;

import java.lang.reflect.Array;
import java.util.*;

public class MatrixSolver {
    private Data data;
    private final Map<Integer, Set<Integer>> connections;
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

        int leftSize;
        int rightSize;

        Queue<Pair> leftExplored = new ArrayDeque<>();
        Queue<Pair> rightExplored = new ArrayDeque<>();

        for (int child : connections.get(array[0])) {
            if (child != node) {
                leftExplored.add(new Pair(child, 1, array[0]));
            }
        }

        for (int child : connections.get(array[1])) {
            if (child != node) {
                rightExplored.add(new Pair(child, 1, array[1]));
            }
        }

        leftSize = leftExplored.size();
        rightSize = rightExplored.size();

        List<Pair> toBeRemoved = new LinkedList<>();
        for (Pair explored : rightExplored) {
            if (leftExplored.contains(explored) && connections.get(explored.getValue()).size() == 2) {
                sockets.add(explored.getValue());
                toBeRemoved.add(explored);
            }
        }
        rightExplored.removeAll(toBeRemoved);
        leftExplored.removeAll(toBeRemoved);

        toBeRemoved.clear();
        while (leftSize == rightSize) {
            Queue<Pair> nextDepthRight = oneDepthExplore(rightExplored);
            Queue<Pair> nextDepthLeft = oneDepthExplore(leftExplored);

            rightExplored = nextDepthRight;
            leftExplored = nextDepthLeft;

            rightSize = rightExplored.size();
            leftSize = leftExplored.size();

            for (Pair explored : rightExplored) {
                if (leftExplored.contains(explored) && connections.get(explored.getValue()).size() == 2) {
                    sockets.add(explored.getValue());
                    toBeRemoved.add(explored);
                }
            }
            rightExplored.removeAll(toBeRemoved);
            leftExplored.removeAll(toBeRemoved);

            toBeRemoved.clear();
            if (leftSize == 0 || rightSize == 0) {
                break;
            }
        }
        sockets.add(node);
        return rightSize == 0 && leftSize == 0;
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
        Set<Integer> children = connections.get(node.getValue());

        for (int child : children) {
            if (child != node.getParent()) {
                Pair pair = new Pair(child, node.getDepth() + 1, node.getValue());
                outOneDepthExplored.add(pair);
            }
        }
    }
}

class Pair {
    private final int value;
    private final int depth;
    private final int parent;

    public Pair(int value, int depth, int parent) {
        this.value = value;
        this.depth = depth;
        this.parent = parent;
    }

    public int getParent() {
        return parent;
    }

    public int getValue() {
        return value;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        return value == pair.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}