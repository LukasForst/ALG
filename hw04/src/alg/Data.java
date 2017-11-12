package alg;

import java.util.Collection;

public class Data {
    private final int countOfAllServers;
    private final int countOfKeyServers;

    private final int[][] coincidenceMatrix;
    private final Collection<Integer> keyServers;

    public Data(int countOfAllServers, int countOfKeyServers, int[][] coincidenceMatrix, Collection<Integer> keyServers) {
        this.countOfAllServers = countOfAllServers;
        this.countOfKeyServers = countOfKeyServers;
        this.coincidenceMatrix = coincidenceMatrix;
        this.keyServers = keyServers;
    }

    public int getCountOfAllServers() {
        return countOfAllServers;
    }

    public int getCountOfKeyServers() {
        return countOfKeyServers;
    }

    public int[][] getCoincidenceMatrix() {
        return coincidenceMatrix;
    }

    public Collection<Integer> getKeyServers() {
        return keyServers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(countOfAllServers).append(countOfKeyServers);
        for (int i = 0; i < coincidenceMatrix.length; i++) {
            sb.append(i);
            for (int j = 0; j < coincidenceMatrix[i].length; i++) {
                if (coincidenceMatrix[i][j] != 0) {
                    sb.append(j).append(" ").append(coincidenceMatrix[i][j]).append("\n");
                }
            }
        }
        for (int keyServer : keyServers) {
            sb.append(keyServer).append(" ");
        }
        sb.append("\n");

        return sb.toString();
    }
}
