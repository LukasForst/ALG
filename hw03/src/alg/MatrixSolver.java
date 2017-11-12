package alg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class MatrixSolver {
    private final ArrayList<Collection<Integer>> connections;
    private final Collection<Integer> possibleSockets;

    private final Collection<Integer> sockets;
    private int[] parentOf;

    public MatrixSolver(Data data) {
        connections = data.getConnections();
        possibleSockets = data.getPossibleSockets();

        sockets = new TreeSet<>();
        parentOf = new int[data.getNumberOfNodes() + 1];
    }

    public Collection<Integer> solve() {
        for (int node : possibleSockets) {
            if (exploreFrom(node)) {
                break;
            } else {
                sockets.clear();
            }
        }

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

        Collection<Integer> leftExplored = new ArrayList<>();
        Collection<Integer> rightExplored = new ArrayList<>();

        leftExplored.add(array[0]);
        rightExplored.add(array[1]);

        parentOf[array[0]] = node;
        parentOf[array[1]] = node;

        while (leftSize == rightSize) {
            Collection<Integer> nextDepthRight = oneDepthExplore(rightExplored);
            Collection<Integer> nextDepthLeft = oneDepthExplore(leftExplored);

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

    private void checkIfSocket(Collection<Integer> leftExplored, Collection<Integer> rightExplored) {
        Collection<Integer> ex = new ArrayList<>(rightExplored.size() + 1);
        ex.addAll(rightExplored);

        for (int explored : ex) {
            if (leftExplored.contains(explored) && connections.get(explored).size() == 2) {
                sockets.add(explored);
                rightExplored.remove(explored);
                leftExplored.remove(explored);
            }
        }
    }

    private Collection<Integer> oneDepthExplore(Collection<Integer> toBeExplored) {
        Collection<Integer> toBeFilled = new TreeSet<>();
        for (int pair : toBeExplored) {
            exploreChildren(pair, toBeFilled);
        }
        return toBeFilled;
    }

    private void exploreChildren(int node, Collection<Integer> outOneDepthExplored) {
        Collection<Integer> children = connections.get(node);
        int parent = parentOf[node];
        for (int child : children) {
            if (child != parent) {
                parentOf[child] = node;
                outOneDepthExplored.add(child);
            }
        }
    }
}