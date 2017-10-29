package alg;

import java.util.List;
import java.util.Stack;

public class MatrixSolver {
    private List<List<Integer>> paths;
    private int[][] matrix;
    private int minPrice = Integer.MAX_VALUE;
    private int numberOfDevices;
    private int minDevicePlacement;

    public MatrixSolver(Data data) {
        matrix = data.getMatrix();
        paths = data.getPaths();
        numberOfDevices = matrix.length;
        minDevicePlacement = data.getMinPriceForPlacement();
    }

    public int solve() {
        int[] availableDevices = new int[numberOfDevices];
        int[] placedDevices = new int[numberOfDevices];

        for (int i = 0; i < availableDevices.length; i++) {
            availableDevices[i] = i;
            placedDevices[i] = -1;
        }

        return recursiveSolve(availableDevices, placedDevices, 0, 0);
    }

    private int recursiveSolve(int[] availableDevices, int[] placedDevices, int currentCost, int depth) {
        for (int device : availableDevices) {
            placedDevices[depth] = device;
            int cost = currentCost + matrix[depth][device];

            int[] available = getNewAvailable(availableDevices, device);
            int nextDepth = depth + 1;

            if (cost + (available.length * minDevicePlacement) >= minPrice || areWiresCrossed(placedDevices, nextDepth)) {
                continue;
            }

            if (available.length == 0) {
                minPrice = minPrice > cost ? cost : minPrice;
                continue;
            }

            recursiveSolve(available, placedDevices, cost, nextDepth);
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

    private Stack<Integer> stack = new Stack<>();
    private int lastDepth = 0;

    private boolean areWiresCrossed(int[] placedDevices, int depth) {

        if (lastDepth >= depth) {
            stack.clear();
            for (int i = 0; i < depth; i++) {
                int currentDevice = placedDevices[i];
                if (checkOneDeviceStack(currentDevice, stack)) {
                    return true;
                }
            }
        } else {
            int currentDevice = placedDevices[depth - 1];
            if (checkOneDeviceStack(currentDevice, stack)) {
                return true;
            }
        }

        lastDepth = depth;
        return depth == numberOfDevices && !stack.isEmpty();
    }


    private boolean checkOneDeviceStack(int currentDevice, Stack<Integer> stack) {
        List<Integer> possibleConnectionDevices;

        possibleConnectionDevices = paths.get(currentDevice);
        int pushTimes = 0;

        @SuppressWarnings("unchecked") // we know that this is integer stack
                Stack<Integer> backUp = (Stack<Integer>) stack.clone();

        for (int destination : possibleConnectionDevices) {
            if (backUp.contains(destination)) {
                int stackHead = stack.pop();

                if (stackHead != destination && !possibleConnectionDevices.contains(stackHead)) {
                    stack.push(stackHead);
                    return true;
                }

            } else {
                pushTimes++;
            }
        }

        for (int j = 0; j < pushTimes; j++) {
            stack.push(currentDevice);
        }

        return false;
    }
}
