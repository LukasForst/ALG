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
            int[] available = getNewAvailable(availableDevices, device);
            int nextDepth = depth + 1;
            int cost = currentCost + matrix[depth][device];

            if (cost > minPrice || areWiresCrossed(placedDevices, nextDepth))
                continue;

            if (available.length == 0) {
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

    private int[] getNewAvailable(int[] available, int device) {
        if (available.length == 0) {
            return new int[0];
        }
        int[] val = new int[available.length - 1];
        int idx = 0;
        for (int dev : available) {
            if (dev != device) {
                val[idx++] = dev;
            }

        }
        return val;
    }

    private boolean areWiresCrossed(int[] placedDevices, int depth) {
        Stack<Integer> stack = new Stack<>();
        List<Integer> possibleConnectionDevices;

        for (int i = 0; i < depth; i++) {
            int currentDevice = placedDevices[i];
            possibleConnectionDevices = data.getPaths().get(currentDevice);
            int pushTimes = 0;
            Stack<Integer> backUp = (Stack<Integer>) stack.clone();

            for(int j = 0; j < possibleConnectionDevices.size(); j++){
                int destination = possibleConnectionDevices.get(j);
                if (backUp.contains(destination)) {
                    int stackHead = stack.peek();

                    if (stackHead == destination) {
                        stack.pop();
                    } else if(possibleConnectionDevices.contains(stackHead)){
                        stack.pop();
                    } else{
                        return true;
                    }

                } else {
                    pushTimes++;
                }
            }

            for (int j = 0; j < pushTimes; j++) {
                stack.push(currentDevice);
            }
        }
        if (depth == data.getNumberOfDevices()) {
            return !stack.isEmpty();
        }
        return false;
    }
}
