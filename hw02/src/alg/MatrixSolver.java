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

    private int recursiveSolve(int[] availableDevices, int[] placedDevices, int currentCost, int depth) {

        for (int device : availableDevices) {
            placedDevices[depth] = device;
            int[] available = Arrays.stream(availableDevices).filter(dev -> dev != device).toArray();
            int nextDepth = depth + 1;
            int cost = getCost(placedDevices, currentCost, depth);

            if(cost > minPrice || areWiresCrossed(placedDevices, nextDepth))
                continue;

            if(available.length == 0){
                minPrice = minPrice > cost ? cost : minPrice;
                continue;
            }

            int result = recursiveSolve(
                    available,
                    placedDevices.clone(),
                    cost,
                    nextDepth);
        }
        return minPrice;
    }

    private int getCost(int[] placedDevices, int currentCost, int depth) {
        currentCost += placedDevices[depth] == -1 ? 0 : matrix[depth][placedDevices[depth]];
        return currentCost;
    }

    private boolean areWiresCrossed(int[] placedDevices, int depth) {
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < depth; i++){
            int currentDevice = placedDevices[i];
            List<Integer> possibleConnectionDevices = new ArrayList<>();
            data.getPaths().stream()
                    .filter(x -> x.getKey() == currentDevice || x.getValue() == currentDevice)
                    .forEach(x -> possibleConnectionDevices.add(x.getKey() == currentDevice ? x.getValue() : x.getKey()));

            int pushTimes = 0;
            Stack<Integer> backUp = (Stack<Integer>) stack.clone();

            for (int destination : possibleConnectionDevices) {
                if (backUp.contains(destination)) {
                    int stackHead = stack.peek();

                    if (!possibleConnectionDevices.contains(stackHead)) {
                        return true;
                    } else {
                        stack.pop();
                    }
                } else {
                    pushTimes++;
                }
            }

            for (int j = 0; j < pushTimes; j++) {
                stack.push(currentDevice);
            }


        }
        if(depth == data.getNumberOfDevices()){
            return !stack.isEmpty();
        }
        return false;
    }
}
