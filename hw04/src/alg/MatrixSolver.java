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
//        nodeStack.push(0);
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
        int[] arr = new int[adjencyList.size()];
        Stack<Integer> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            int processed = stack.pop();
            nodeStack.push(processed);

            boolean returning = true;
            for (EdgePair p : adjencyList.get(processed)) {
                int nextNode = p.getEndNode();
                if (nextNode == parentOf[processed]) continue;

                parentOf[nextNode] = processed;
                if (nodeStack.contains(nextNode)) {
                    nodeStack.push(nextNode);
                    return;
                }
                arr[processed]++;
                stack.push(nextNode);
                returning = false;
            }

            if (returning) {
                nodeStack.pop();
//                nodeStack.remove((Integer) processed);
                int parent = parentOf[processed];
                while (arr[parent] <= 1) {
//                    nodeStack.remove((Integer) parent);
                    nodeStack.pop();
                    parent = parentOf[parent];
                }
            }
        }
    }

    private int getPriceOfSubTreeFrom(int startNode) {
        int finalPrice = 0;

        return finalPrice;
    }

    private void recursiveSolve(int node, int parent) {

    }


}
