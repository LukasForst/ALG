package alg;

import java.util.*;

public class MatrixSolver {
    private Data data;
    private int minPrice = Integer.MAX_VALUE;

    public MatrixSolver(Data data) {
        this.data = data;
    }

    public int solve() {
        int[] availableDevices = new int[data.getNumberOfDevices()];
        int[] availablePlaces = new int[data.getNumberOfDevices()];
        ArrayList<AbstractMap.SimpleEntry<Integer, Integer>> placedDevices = new ArrayList<>();

        for (int i = 0; i < availableDevices.length; i++) {
            availableDevices[i] = i;
            availablePlaces[i] = i;
        }
        return recursiveSolve(availableDevices, availablePlaces, placedDevices);
    }

    private int recursiveSolve(int[] availableDevices, int[] availablePlaces, List<AbstractMap.SimpleEntry<Integer, Integer>> placedDevices) {
        if (areWiresCrossed(placedDevices)) {
            return Integer.MAX_VALUE;
        } else if (isComplete(availableDevices.length, availablePlaces.length)) {
            return getCost(placedDevices);
        }

        ArrayList<Integer> results = new ArrayList<>();
        for (int device : availableDevices) {
            for (int availablePlace : availablePlaces) {
                List<AbstractMap.SimpleEntry<Integer, Integer>> nextPlacedDevices = new ArrayList<>(placedDevices);
                nextPlacedDevices.add(new AbstractMap.SimpleEntry<>(availablePlace, device));

                int result = recursiveSolve(
                        Arrays.stream(availableDevices).filter(d -> d != device).toArray(),
                        Arrays.stream(availablePlaces).filter(p -> p != availablePlace).toArray(),
                        nextPlacedDevices);

                results.add(result);
            }
        }

        return Collections.min(results);
    }

    private int getCost(List<AbstractMap.SimpleEntry<Integer, Integer>> placedDevices) {
        int cost = 0;
        for (AbstractMap.SimpleEntry<Integer, Integer> entry : placedDevices) {
            cost += data.getMatrix()[entry.getKey()][entry.getValue()];
        }
        return cost;
    }

    private boolean areWiresCrossed(List<AbstractMap.SimpleEntry<Integer, Integer>> placedDevices) {
        return false;
    }

    private boolean isComplete(int availableDevicesCount, int availablePlacesCount) {
        return availableDevicesCount == 0 && availablePlacesCount == 0;
    }
}
