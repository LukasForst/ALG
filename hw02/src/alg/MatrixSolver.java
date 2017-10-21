package alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        if (currentCost > minPrice || areWiresCrossed(placedDevices, depth)) {
            return Integer.MAX_VALUE;
        } else if (isComplete(availableDevices.length, (int) Arrays.stream(placedDevices).filter(number -> number != -1).count())) {
            minPrice = minPrice > currentCost ? currentCost : minPrice;
            System.out.println("Possible cost: " + currentCost);
            return currentCost;
        }

        ArrayList<Integer> results = new ArrayList<>();
        for (int device : availableDevices) {
            placedDevices[depth] = device;
            int[] available = Arrays.stream(availableDevices).filter(dev -> dev != device).toArray();
            int nextDepth = depth + 1;
            int cost = getCost(placedDevices, currentCost, depth);

            int result = recursiveSolve(
                    available,
                    placedDevices,
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

    private boolean areWiresCrossed(int[] placedDevices, int depth) {
        return false;
    }

    private boolean isComplete(int availableDevicesCount, int placedDevices) {
        return availableDevicesCount == 0 && placedDevices == data.getNumberOfDevices();
    }
}
