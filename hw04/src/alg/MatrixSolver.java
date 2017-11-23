package alg;

import java.util.*;

public class MatrixSolver {
    private Data data;
    private final ArrayList<Collection<EdgePair>> adjacencyList;
    private final boolean[] keyServers;
    private final int[] isInCycle;
    private int[] parentOf;

    int[] numberOfChildren;
    boolean[] hasStackElement;

    public MatrixSolver(Data data) {
        this.data = data;
        adjacencyList = data.getAdjacencyList();

        parentOf = new int[adjacencyList.size()];
        keyServers = data.getKeyServers();
        isInCycle = new int[adjacencyList.size()];
        numberOfChildren = new int[adjacencyList.size()];
        hasStackElement = new boolean[adjacencyList.size()];
    }

    public int solve() {
        Collection<Integer> cycle = findCycle();
        Map<Integer, Boolean> compulsoryMap = new HashMap<>(cycle.size());
        int mandatoryNodesCount = 0;
        int finalPrice = 0;
        for (int node : cycle) {
            int current = getPriceOfSubTreeFrom(node);

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
//        if (!checkCycle(compulsoryMap)) throw new IllegalStateException("Cycle is not cycle!");
        AbstractMap.SimpleEntry<Collection<EdgePair>, Integer> transformed = transformMapToCycle(compulsoryMap);
        int shortestInCycle = findShortest(transformed);
        return finalPrice + shortestInCycle;
    }

    private int findShortest(AbstractMap.SimpleEntry<Collection<EdgePair>, Integer> transformed) {
        int maxPrice = transformed.getValue();
        int minPrice = maxPrice;

        for (EdgePair p : transformed.getKey()) {
            int tmpPrice = (maxPrice - p.getPrice()) * 2;
            minPrice = Integer.min(tmpPrice, minPrice);
        }
        return minPrice;
    }

    private AbstractMap.SimpleEntry<Collection<EdgePair>, Integer> transformMapToCycle(Map<Integer, Boolean> compulsoryMap) {
        Collection<EdgePair> result = new ArrayList<>(compulsoryMap.keySet().size());
        Stack<Integer> toVisit = new Stack<>();
        int first = getFirst(compulsoryMap);
        toVisit.push(first);

        int priceFromLast = 0;
        int finalPrice = 0;
        int lastKey = first;
        boolean[] visited = new boolean[adjacencyList.size()];
        while (!toVisit.empty()) {
            int processed = toVisit.pop();

            for (EdgePair pair : adjacencyList.get(processed)) {
                int nextValue = pair.getEndNode();
                if (nextValue == parentOf[processed] || isInCycle[nextValue] == 0 || visited[nextValue])
                    continue;

                priceFromLast += pair.getPrice();

                if (isInCycle[nextValue] == 2) {
                    result.add(new EdgePair(lastKey, nextValue, priceFromLast));
                    finalPrice += priceFromLast;
                    priceFromLast = 0;
                    lastKey = nextValue;
                }

                parentOf[nextValue] = processed;
                toVisit.push(nextValue);
                visited[nextValue] = true;
            }
        }

        return new AbstractMap.SimpleEntry<Collection<EdgePair>, Integer>(result, finalPrice);
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

    private Collection<Integer> findCycle() {
        Stack<Integer> nodeStack = new Stack<>();
        parentOf[0] = -1;
        Stack<Integer> toVisitStack = new Stack<>();
        toVisitStack.push(0);
        boolean flag = false;
        int countInCycle = 0;
        while (!toVisitStack.isEmpty() && !flag) {
            int processed = toVisitStack.pop();
            nodeStack.push(processed);
            countInCycle++;
            hasStackElement[processed] = true;

            numberOfChildren[processed] = 0;
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
                countInCycle--;
                hasStackElement[processed] = false;
                int parent = parentOf[processed];
                numberOfChildren[parent]--;

                while (numberOfChildren[parent] == 0) {
                    nodeStack.pop();
                    countInCycle--;
                    hasStackElement[parent] = false;
                    parent = parentOf[parent];
                    numberOfChildren[parent]--;
                }
            }
        }

        for (int i = 0; i < adjacencyList.size(); i++) {
            numberOfChildren[i] = 0;
            hasStackElement[i] = false;
        }

        int cycleEnd = nodeStack.pop();
        Collection<Integer> cycleNodes = new ArrayList<>(countInCycle);
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
            finalPrice += getPriceOfSubTreeRecursive(node);
        }


        return finalPrice;
    }

    private int getPriceOfSubTreeRecursive(EdgePair startNode) {
        int price = 0;
        int tmpPrice = 0;

        Stack<EdgePair> nodeStack = new Stack<>();
        Stack<EdgePair> toVisitStack = new Stack<>();
        toVisitStack.push(startNode);

        while (!toVisitStack.isEmpty()) {
            EdgePair processed = toVisitStack.pop();
            nodeStack.push(processed);
            int processedValue = processed.getEndNode();

            tmpPrice += processed.getPrice();
            if (keyServers[processedValue]) {
                price += tmpPrice;
                tmpPrice = 0;
            }

            numberOfChildren[processedValue] = 0;
            boolean returning = true;
            for (EdgePair p : adjacencyList.get(processedValue)) {
                int nextNode = p.getEndNode();
                if (nextNode == parentOf[processedValue]) continue;

                parentOf[nextNode] = processedValue;
                if (hasStackElement[nextNode]) {
                    hasStackElement[nextNode] = true;
                    nodeStack.push(p);
                    returning = false;
                    break;
                }
                numberOfChildren[processedValue]++;
                toVisitStack.push(p);
                returning = false;
            }

            if (returning) {
                EdgePair pair = nodeStack.pop();

                if (!keyServers[processedValue]) {
                    tmpPrice -= pair.getPrice();
                }

                hasStackElement[processedValue] = false;
                int parent = parentOf[processedValue];
                numberOfChildren[parent]--;

                while (numberOfChildren[parent] == 0) {
                    pair = nodeStack.pop();

                    if (tmpPrice > 0 && !keyServers[pair.getEndNode()]) {
                        tmpPrice -= pair.getPrice();
                    }

                    hasStackElement[parent] = false;
                    parent = parentOf[parent];
                    numberOfChildren[parent]--;
                }
            }
        }

        return price;
    }
}
