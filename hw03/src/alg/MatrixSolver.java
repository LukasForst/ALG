package alg;

import java.util.*;

public class MatrixSolver {
    private Data data;
    private final Map<Integer, Set<Integer>> connections;
    private final List<Integer> possibleSockets;

    private final Set<Integer> visited;
    private final List<Integer> sockets;
    private final Queue<Integer> toVisit;

    private boolean hasSolution = false;

    public MatrixSolver(Data data) {
        this.data = data;
        connections = data.getConnections();
        possibleSockets = data.getPossibleSockets();

        int initialCapacity = data.getNumberOfNodes() / 2;
        visited = new HashSet<>(data.getNumberOfNodes());
        sockets = new ArrayList<>(initialCapacity);
        toVisit = new PriorityQueue<>(initialCapacity);
    }

    public List<Integer> solve() {
        for (int node : possibleSockets) {
            recursiveSolve(node, 0);

            if (hasSolution) {
                break;
            }
        }

        Collections.sort(sockets);
        return sockets;
    }

    private void recursiveSolve(int initialNode, int depth) {

    }
}
