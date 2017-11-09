package alg;

import java.nio.channels.Pipe;
import java.util.*;

public class MatrixSolver {
    private Data data;
    private final Map<Integer, Set<Integer>> connections;
    private final List<Integer> possibleSockets;

    private final Map<Integer, Pair> visited;
    private final List<Integer> sockets;
    private final Queue<Pair> toVisit;

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
            sockets.add(node);
            initialValue = node;
            int array[] = connections.get(node).stream().mapToInt(Integer::valueOf).toArray();
            toVisit.add(new Pair(array[0], 0, Direction.LEFT));
            toVisit.add(new Pair(array[1], 0, Direction.RIGHT));
            visited.put(node, new Pair(node, 0, Direction.INITIAL));
            if (recursiveSolve(toVisit.poll()) == RecursiveStatus.SOLVED) {
                if (sockets.size() > 1) {
                    break;
                }
            }
        }


        Collections.sort(sockets);
        return sockets;
    }

    private int initialValue = -1;
    private RecursiveStatus recursiveSolve(Pair initialNode) {
        if (initialNode == null) {
            return RecursiveStatus.SOLVED;
        }

        if (visited.containsKey(initialNode.getValue()) && initialNode.getValue() != initialValue) {
            Pair alreadyVisitedPair = visited.get(initialNode.getValue());
            if (alreadyVisitedPair.getDirection() != initialNode.getDirection()
                    && alreadyVisitedPair.getDepth() == initialNode.getDepth()) {
                sockets.add(initialNode.getValue());
                return recursiveSolve(toVisit.poll());
            } else {
                visited.clear();
                toVisit.clear();
                sockets.clear();
                return RecursiveStatus.WRONG_NODE;
            }
        } else {
            visited.put(initialNode.getValue(), initialNode);
        }

        for (int nodeValue : connections.get(initialNode.getValue())) {

        }

        return recursiveSolve(toVisit.poll());
    }

    private enum RecursiveStatus {
        WRONG_NODE, SOLVED, NEXT_NODE
    }
}

enum Direction {
    LEFT, RIGHT, INITIAL
}

class Pair {
    private final int value;
    private final int depth;
    private final Direction direction;

    public Pair(int value, int depth, Direction direction) {
        this.value = value;
        this.depth = depth;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
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

        if (value != pair.value) return false;
        if (depth != pair.depth) return false;
        return direction == pair.direction;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + depth;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }
}