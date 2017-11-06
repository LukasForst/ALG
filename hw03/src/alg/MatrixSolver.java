package alg;

import java.util.*;

public class MatrixSolver {
    private Data data;
    private final Map<Integer, Set<Integer>> connections;
    private final List<Integer> possibleSockets;

    private final Map<Integer, Integer> visited;
    private final List<Integer> sockets;
    private final Queue<Integer> toVisit;

    private boolean hasSolution = false;

    public MatrixSolver(Data data) {
        this.data = data;
        connections = data.getConnections();
        possibleSockets = data.getPossibleSockets();

        int initialCapacity = data.getNumberOfNodes() / 2;
        visited = new HashMap<>(data.getNumberOfNodes());
        sockets = new ArrayList<>(initialCapacity);
        toVisit = new ArrayDeque<>(initialCapacity);
    }

    public List<Integer> solve() {
        for (int node : possibleSockets) {
            if (recursiveSolve(node, 0) == RecursiveStatus.SOLVED) {
                break;
            }
        }

        Collections.sort(sockets);
        return sockets;
    }

    private RecursiveStatus recursiveSolve(int initialNode, int depth) {
        if (visited.containsKey(initialNode)) {
            if (visited.get(initialNode) != depth) {
                sockets.clear();
                visited.clear();
                return RecursiveStatus.WRONG_NODE;
            } else {
                sockets.add(initialNode);
                if (toVisit.peek() != null) {
                    return recursiveSolve(toVisit.poll(), ++depth);
                } else {
                    return RecursiveStatus.SOLVED;
                }
            }
        } else {
            visited.put(initialNode, depth);
        }

        toVisit.addAll(connections.get(initialNode));
        toVisit.remove(initialNode);

        if (toVisit.peek() == null) {
            return RecursiveStatus.SOLVED;
        } else {
            return recursiveSolve(toVisit.poll(), ++depth);
        }
    }

    private enum RecursiveStatus {
        WRONG_NODE, SOLVED, NEXT_NODE
    }
}