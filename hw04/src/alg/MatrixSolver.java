package alg;

import java.util.*;

public class MatrixSolver {
    private Data data;
    private final int keyServersCount;
    private final ArrayList<Collection<EdgePair>> adjencyList;
    private final Collection<Integer> keyServers;
    Stack<Integer> nodeStack = new Stack<>();
    int[] parentOf;
    private Collection<Integer> visitedKeyServers = new HashSet<>();
    private Collection<Integer> visitedServers = new HashSet<>();
    private boolean cycleFound = false;
    private Queue<EdgePair> toVisit = new PriorityQueue<>();

    public MatrixSolver(Data data) {
        this.data = data;
        keyServersCount = data.getCountOfKeyServers();
        adjencyList = data.getAdjacencyList();
        keyServers = data.getKeyServers();
    }

    public int solve() {
        Collection<Integer> res = findCycle();
        if (res != null)
            return res.size();
        return -1;
    }

    private Collection<Integer> findCycle() {
        nodeStack = new Stack<>();
        boolean[] containsNode = new boolean[adjencyList.size()];
        parentOf = new int[adjencyList.size()];
        parentOf[0] = -1;
        nodeStack.push(0);
        findCycleRecursive(0);

        int cycleEnd = nodeStack.pop();
        Collection<Integer> cycleNodes = new TreeSet<>();
        cycleNodes.add(cycleEnd);
        while (cycleEnd != nodeStack.peek()) {
            cycleNodes.add(nodeStack.pop());
        }

        return cycleNodes;
    }

    private void removeObsoleteNodes() {
        for (Collection<EdgePair> pairs : adjencyList) {

        }
    }

    private void findCycleRecursive(int node) {
        for (EdgePair edgePair : adjencyList.get(node)) {
            if (cycleFound)
                return;

            int nextNode = edgePair.getEndNode();
            if (nextNode == parentOf[node]) {
                continue;
            }
            parentOf[nextNode] = node;

            if (nodeStack.contains(nextNode)) {
                nodeStack.push(nextNode);
                cycleFound = true;
                return;
            }

            nodeStack.push(nextNode);
            findCycleRecursive(nextNode);
        }

        if (!nodeStack.isEmpty() && !cycleFound) {
            int next = nodeStack.pop();
        }
    }

    private int getPriceOfSubTreeFrom(int startNode) {
        int finalPrice = 0;

        return finalPrice;
    }

    private void recursiveSolve(int node, int parent) {

    }


}
