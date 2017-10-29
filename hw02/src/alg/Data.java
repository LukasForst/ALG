package alg;

import java.util.Arrays;
import java.util.List;

public class Data {
    private int[][] matrix;
    private int numberOfDevices;
    private List<List<Integer>> paths;
    private int minPriceForPlacement;

    public Data(int[][] matrix, int numberOfDevices, List<List<Integer>> paths, int minPriceForPlacement) {
        this.matrix = matrix;
        this.numberOfDevices = numberOfDevices;
        this.paths = paths;
        this.minPriceForPlacement = minPriceForPlacement;
    }

    public int getMinPriceForPlacement() {
        return minPriceForPlacement;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getNumberOfDevices() {
        return numberOfDevices;
    }

    public List<List<Integer>> getPaths() {
        return paths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (numberOfDevices != data.numberOfDevices) return false;
        if (!Arrays.deepEquals(matrix, data.matrix)) return false;
        return paths.equals(data.paths);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(matrix);
        result = 31 * result + numberOfDevices;
        result = 31 * result + paths.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(numberOfDevices).append("\n");
        for (int[] aMatrix : matrix) {
            for (int anAMatrix : aMatrix) {
                builder.append(anAMatrix).append(" ");
            }
            builder.append("\n");
        }

        for(int i = 0; i < paths.size(); i++){
            builder.append(i).append(" - ");
            for(int j = 0; j < paths.get(i).size(); j++){
                builder.append(paths.get(i).get(j)).append(" ");
            }
            builder.append("\n");
        }

        builder.append("\n");
        return builder.toString();
    }
}
