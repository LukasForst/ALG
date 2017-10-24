package alg;

import java.util.*;

public class MatrixSolver {
    private Data data;
    private int[][] matrix;

    private int minPrice = Integer.MAX_VALUE;

    public MatrixSolver(Data data) {
        this.data = data;
        matrix = data.getMatrix();
    }

    public int solve() {
        int[] availableDevices = new int[data.getNumberOfDevices()];
        int[] placedDevices = new int[data.getNumberOfDevices()];

        for (int i = 0; i < availableDevices.length; i++) {
            availableDevices[i] = i;
            placedDevices[i] = -1;
        }
        return recursiveSolve(availableDevices, placedDevices, 0, 0);
    }

    private void fixStack() {
        stack = (Stack<Integer>) backUpStack.clone();
    }

    private int recursiveSolve(int[] availableDevices, int[] placedDevices, int currentCost, int depth) {
        if (currentCost > minPrice) {
            return Integer.MAX_VALUE;
        } else if (areWiresCrossed(placedDevices, depth)) {
            fixStack();
            return Integer.MAX_VALUE;
        } else if (isComplete(availableDevices.length, (int) Arrays.stream(placedDevices).filter(number -> number != -1).count())) {
            minPrice = minPrice > currentCost ? currentCost : minPrice;
            fixStack();
            return currentCost;
        }


        int[] sortedArray = Arrays.stream(availableDevices)
                .boxed()
                .sorted(Comparator.comparingInt(a -> matrix[depth][a]))
                .mapToInt(x -> x)
                .toArray();

        ArrayList<Integer> results = new ArrayList<>();
        for (int device : sortedArray) {
            placedDevices[depth] = device;
            int[] available = Arrays.stream(availableDevices).filter(dev -> dev != device).toArray();
            int nextDepth = depth + 1;
            int cost = getCost(placedDevices, currentCost, depth);

            int result = recursiveSolve(
                    available,
                    placedDevices.clone(),
                    cost,
                    nextDepth);
            results.add(result);
        }
        return Collections.min(results);
    }

    private int getCost(int[] placedDevices, int currentCost, int depth) {
        currentCost += placedDevices[depth] == -1 ? 0 : matrix[depth][placedDevices[depth]];
        return currentCost;
    }

    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> backUpStack = new Stack<>();
    private boolean areWiresCrossed(int[] placedDevices, int depth) {
        if (depth == 0)
            return false;
        int currentDevice = placedDevices[depth - 1];

        Set<Integer> possibleConnectionDevices = new HashSet<>();
        data.getPaths().stream()
                .filter(x -> x.getKey() == currentDevice || x.getValue() == currentDevice)
                .forEach(x -> possibleConnectionDevices.add(x.getKey() == currentDevice ? x.getValue() : x.getKey()));
        backUpStack = (Stack<Integer>) stack.clone();

        int pushTimes = 0;
        int popTimes = 0;
        for (int destination : possibleConnectionDevices) {
            if (stack.contains(destination)) {
                int stackHead = stack.peek();

                if (!possibleConnectionDevices.contains(stackHead)) {
                    return true;
                } else {
                    popTimes++;
                }
            } else {
                pushTimes++;
            }
        }

        for (int i = 0; i < pushTimes; i++) {
            stack.push(currentDevice);
        }

        for (int i = 0; i < popTimes; i++) {
            stack.pop();
        }
        return false;
    }

    private boolean isComplete(int availableDevicesCount, int placedDevices) {
        return availableDevicesCount == 0 && placedDevices == data.getNumberOfDevices();
    }
}
