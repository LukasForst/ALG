package alg;

import java.util.*;

public class MatrixSolver {
    private final ArrayList<Collection<EdgePair>> adjacencyList;
    private final int allServersCount;
    private final boolean[] keyServers;
    private final byte[] isInCycle;
    private int[] parentOf;

    private int[] numberOfChildren;
    private boolean[] hasStackElement;

    public boolean solved = false;
    public int solution = 0;

    public MatrixSolver(Data data) {
        adjacencyList = data.getAdjacencyList();
        allServersCount = data.getCountOfAllServers();
        parentOf = new int[allServersCount];
        keyServers = data.getKeyServers();
        isInCycle = new byte[allServersCount];
        numberOfChildren = new int[allServersCount];
        hasStackElement = new boolean[allServersCount];
    }

    public int solve() {
        Collection<Integer> cycle = findCycle();
        Collection<Integer> compMap = new ArrayList<>(cycle.size());
        int finalPrice = 0;
        for (int node : cycle) {
            int current = getPriceOfSubTreeFrom(node);

            if (current != 0 || keyServers[node]) {
                compMap.add(node);
                isInCycle[node] = 2;
            } else {
                compMap.add(node);
                isInCycle[node] = 1;
            }

            finalPrice += current * 2;
        }

        AbstractMap.SimpleEntry<Collection<EdgePair>, Integer> transformed = transformMapToCycle(compMap);
        if (transformed.getKey().size() == 1) {
            solution = getNonCycledPrice(transformed.getKey().iterator().next(), finalPrice);
        } else {
            int shortestInCycle = findShortest(transformed);
            solution = finalPrice + shortestInCycle;
        }
        solved = true;
        return solution;
    }

    private int getNonCycledPrice(EdgePair startNode, int finPrice) {
        int tmpPrice = 0;
        Stack<EdgePair> toVisit = new Stack<>();
        toVisit.push(startNode);

        boolean flag = false;
        while (!toVisit.isEmpty() && !flag) {
            EdgePair processed = toVisit.pop();
            int processedValue = processed.getEndNode();

            for (EdgePair p : adjacencyList.get(processedValue)) {
                int next = p.getEndNode();
                if (next == parentOf[processedValue] || isInCycle[next] != 0) continue;

                tmpPrice += p.getPrice();
                toVisit.push(p);

                if (keyServers[next]) {
                    flag = true;
                    break;
                }
            }
        }

        return finPrice - (tmpPrice * 2);
    }

    private int findShortest(AbstractMap.SimpleEntry<Collection<EdgePair>, Integer> transformed) {
        int maxPrice = transformed.getValue();

        if (transformed.getKey().size() == 1)
            return 0;

        int minPrice = maxPrice;

        for (EdgePair p : transformed.getKey()) {
            int tmpPrice = (maxPrice - p.getPrice()) * 2;
            minPrice = Integer.min(tmpPrice, minPrice);
        }
        return minPrice;
    }

    private AbstractMap.SimpleEntry<Collection<EdgePair>, Integer> transformMapToCycle(Collection<Integer> compulsoryMap) {
        Collection<EdgePair> result = new ArrayList<>(compulsoryMap.size());
        Stack<Integer> toVisit = new Stack<>();
        int first = getFirst(compulsoryMap);
        toVisit.push(first);

        int priceFromLast = 0;
        int finalPrice = 0;
        boolean[] visited = new boolean[adjacencyList.size()];
        while (!toVisit.empty()) {
            int processed = toVisit.pop();

            for (EdgePair pair : adjacencyList.get(processed)) {
                int nextValue = pair.getEndNode();
                if (nextValue == parentOf[processed] || isInCycle[nextValue] == 0 || visited[nextValue])
                    continue;

                priceFromLast += pair.getPrice();

                if (isInCycle[nextValue] == 2) {
                    result.add(new EdgePair(nextValue, priceFromLast));
                    finalPrice += priceFromLast;
                    priceFromLast = 0;
                }

                parentOf[nextValue] = processed;
                toVisit.push(nextValue);
                visited[nextValue] = true;
            }
        }

        return new AbstractMap.SimpleEntry<>(result, finalPrice);
    }

    private Collection<Integer> findCycle() {
        int first = new Random().nextInt(allServersCount) % (allServersCount - 1);
        Stack<Integer> nodeStack = new Stack<>();
        parentOf[first] = -1;
        Stack<Integer> toVisitStack = new Stack<>();
        toVisitStack.push(first);
        boolean flag = false;
        int countInCycle = 0;
        while (!toVisitStack.isEmpty()) {
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

            if (flag) break;

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

        for (int i = 0; i < allServersCount; i++) {
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

    private int getFirst(Collection<Integer> c) {
        for (int key : c) {
            if (isInCycle[key] == 2) {
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

                if (nextNode != parentOf[processedValue]) {

                    parentOf[nextNode] = processedValue;
                    if (!hasStackElement[nextNode]) {
                        numberOfChildren[processedValue]++;
                        toVisitStack.push(p);
                        returning = false;
                    } else {
                        nodeStack.push(p);
                        return price;
                    }
                }
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
