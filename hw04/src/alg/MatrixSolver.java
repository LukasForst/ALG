package alg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import java.util.TreeSet;

public class MatrixSolver {
    private Data data;
    private final ArrayList<Collection<EdgePair>> adjacencyList;
    private final boolean[] keyServers;

    private int[] parentOf;

    public MatrixSolver(Data data) {
        this.data = data;
        adjacencyList = data.getAdjacencyList();

        parentOf = new int[adjacencyList.size()];
        keyServers = data.getKeyServers();
    }

    public int solve() {
        Collection<Integer> res = findCycle();
        int finalPrice = 0;
        for (int node : res) {
            int current = getPriceOfSubTreeFrom(node, res);
            finalPrice += current;
        }
        return res.size();
    }

    private Collection<Integer> findCycle() {
        Stack<Integer> nodeStack = new Stack<>();

        int[] numberOfChildren = new int[adjacencyList.size()];
        boolean[] hasStackElement = new boolean[adjacencyList.size()];

        parentOf[0] = -1;

        Stack<Integer> toVisitStack = new Stack<>();
        toVisitStack.push(0);
        boolean flag = false;
        while (!toVisitStack.isEmpty() && !flag) {
            int processed = toVisitStack.pop();
            nodeStack.push(processed);
            hasStackElement[processed] = true;

            boolean returning = true;
            for (EdgePair p : adjacencyList.get(processed)) {
                int nextNode = p.getEndNode();
                if (nextNode == parentOf[processed]) continue;

                parentOf[nextNode] = processed;
                if (hasStackElement[nextNode]) {
                    hasStackElement[nextNode] = true;
                    nodeStack.push(nextNode);
                    returning = false;
                    flag = true;
                    break;
                }
                numberOfChildren[processed]++;
                toVisitStack.push(nextNode);
                returning = false;
            }

            if (returning) {
                nodeStack.pop();
                hasStackElement[processed] = false;
                int parent = parentOf[processed];
                while (numberOfChildren[parent] <= 1) {
                    nodeStack.pop();
                    hasStackElement[parent] = false;
                    parent = parentOf[parent];
                }
            }
        }

        int cycleEnd = nodeStack.pop();
        Collection<Integer> cycleNodes = new TreeSet<>();
        cycleNodes.add(cycleEnd);
        while (cycleEnd != nodeStack.peek()) {
            cycleNodes.add(nodeStack.pop());
        }

        return cycleNodes;
    }

    private int getPriceOfSubTreeFrom(int root, Collection<Integer> parent) {
        int finalPrice = 0;
        Stack<Integer> toVisit = new Stack<>();

        for (EdgePair pair : adjacencyList.get(root)) {
            int next = pair.getEndNode();
            if (parent.contains(next)) continue;

            parentOf[next] = root;
            toVisit.push(next);
        }

        while (!toVisit.isEmpty()) {
            int processed = toVisit.pop();
            if (keyServers[processed]) {
                finalPrice++;
            }

            for (EdgePair pair : adjacencyList.get(processed)) {
                int nextNode = pair.getEndNode();
                if (parentOf[processed] != nextNode) {
                    parentOf[nextNode] = processed;
                    toVisit.push(nextNode);
                }
            }
        }

        return finalPrice;
    }
}

class Pair {
    int price;
    int node;

    public Pair(int price, int depth) {
        this.price = price;
        this.node = depth;
    }
}
