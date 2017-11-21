package alg;

import java.util.*;

public class MatrixSolver {
    private Data data;
    private final ArrayList<Collection<EdgePair>> adjacencyList;
    private final boolean[] keyServers;
    private final int[] isInCycle;
    private int[] parentOf;
    private int finalPriceForSubTree = 0;

    public MatrixSolver(Data data) {
        this.data = data;
        adjacencyList = data.getAdjacencyList();

        parentOf = new int[adjacencyList.size()];
        keyServers = data.getKeyServers();
        isInCycle = new int[adjacencyList.size()];
    }

    public int solve() {
        Collection<Integer> cycle = findCycle();
        Map<Integer, Boolean> compulsoryMap = new HashMap<>(cycle.size());
        int mandatoryNodesCount = 0;
        int finalPrice = 0;
        StringBuilder sb = new StringBuilder();
        for (int node : cycle) {
            int current = getPriceOfSubTreeFrom(node);
            sb.append(node).append(" - ").append(current).append("\n");

            if (current != 0 || keyServers[node]) {
                mandatoryNodesCount++;
                compulsoryMap.put(node, true);
                isInCycle[node] = 2;
            } else {
                compulsoryMap.put(node, false);
                isInCycle[node] = 1;
            }

            finalPrice += current * 2;
        }

        if (!checkCycle(compulsoryMap)) throw new IllegalStateException("Cycle is not cycle!");

        int shortestInCycle = findShortestPathInCycle(compulsoryMap, mandatoryNodesCount);
        System.out.println("In cycle - " + shortestInCycle);
        return finalPrice + shortestInCycle * 2;
    }

    private boolean checkCycle(Map<Integer, Boolean> compulsoryMap) {
        Set<Integer> keySet = compulsoryMap.keySet();
        for (int key : keySet) {
            int count = 0;
            for (EdgePair pair : adjacencyList.get(key)) {
                if (isInCycle[pair.getEndNode()] != 0) {
                    count++;
                }
            }

            if (count != 2) {
                return false;
            }
        }
        return true;
    }

    private int findShortestPathInCycle(Map<Integer, Boolean> compulsoryMap, int mandatoryNodesCount) {
        int startNode = getFirst(compulsoryMap);
        int finalPriceForCycle = 0;
        if (startNode != -1) {

            Iterator<EdgePair> iterator = adjacencyList.get(startNode).iterator();
            int count = 0;
            int tmp = 0;
            EdgePair[] pairs = new EdgePair[2];
            while (iterator.hasNext() && count < 2) {
                EdgePair pair = iterator.next();
                if (isInCycle[pair.getEndNode()] != 0) {
                    pairs[count++] = pair;
                }
                tmp++;
            }
            count = 0;
            int leftPrice = getPriceFromToInCycle(startNode, pairs[0].getEndNode(), pairs[0].getPrice(), mandatoryNodesCount);
            int rightPrice = getPriceFromToInCycle(startNode, pairs[1].getEndNode(), pairs[1].getPrice(), mandatoryNodesCount);
            System.out.println("Start node " + startNode);
            System.out.println("Left - " + leftPrice);
            System.out.println("RIght - " + rightPrice);

        } else {
            finalPriceForCycle = -100;
        }
        return finalPriceForCycle;
    }

    private int getPriceFromToInCycle(int rootNode, int startNode, int price, int mandatoryNodesCount) {
        int counter = 1;
        Stack<Integer> toVisit = new Stack<>();
        toVisit.push(startNode);
        if (isInCycle[startNode] == 2) counter++;

        parentOf[startNode] = rootNode;
        while (!toVisit.isEmpty() && counter != mandatoryNodesCount) {
            int processed = toVisit.pop();
            for (EdgePair pair : adjacencyList.get(processed)) {
                int nextNode = pair.getEndNode();

                if (parentOf[processed] != nextNode && isInCycle[nextNode] != 0) {
                    price += pair.getPrice();
                    if (isInCycle[nextNode] == 2) counter++;
                    parentOf[nextNode] = processed;
                    toVisit.push(nextNode);
                    break;
                }
            }
        }
        return price;
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
                numberOfChildren[parent]--;

                while (numberOfChildren[parent] == 0) {
                    nodeStack.pop();
                    hasStackElement[parent] = false;
                    parent = parentOf[parent];
                    numberOfChildren[parent]--;
                }
            }
        }

        int cycleEnd = nodeStack.pop();
        Collection<Integer> cycleNodes = new TreeSet<>();
        cycleNodes.add(cycleEnd);
        isInCycle[cycleEnd] = 1;
        while (cycleEnd != nodeStack.peek()) {
            int nodeInCycle = nodeStack.pop();
            isInCycle[nodeInCycle] = 1;
            cycleNodes.add(nodeInCycle);
        }

        return cycleNodes;
    }

    private int getFirst(Map<Integer, Boolean> compulsoryMap) {
        for (int key : compulsoryMap.keySet()) {
            if (compulsoryMap.get(key)) {
                return key;
            }
        }
        return -1;
    }

    private int getPriceOfSubTreeFrom(int root) {
        int finalPrice = 0;
        Stack<EdgePair> toVisit = new Stack<>();

        for (EdgePair pair : adjacencyList.get(root)) {
            int next = pair.getEndNode();
            if (isInCycle[next] != 0) continue;

            parentOf[next] = root;
            toVisit.add(pair);
        }

        while (!toVisit.isEmpty()) {
            EdgePair node = toVisit.pop();
            getPriceOfSubTreeRecursive(node.getEndNode());
            if (keyServers[node.getEndNode()] || finalPriceForSubTree != 0) {
                finalPriceForSubTree += node.getPrice();
            }

            finalPrice += finalPriceForSubTree;
            finalPriceForSubTree = 0;

        }


        return finalPrice;
    }

    private int getPriceOfSubTreeRecursive(int node) {
        int price = 0;
        for (EdgePair pair : adjacencyList.get(node)) {
            int next = pair.getEndNode();

            if (parentOf[node] == next) continue;

            price += pair.getPrice();
            if (keyServers[next]) {
                finalPriceForSubTree += price;
                price = 0;
            }
            parentOf[next] = node;
            price += getPriceOfSubTreeRecursive(next);
        }
        return price;
    }
}
