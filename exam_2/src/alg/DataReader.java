package alg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class DataReader {
    public static Solver read() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String[] count = reader.readLine().split(" ");
            int rowsCount = Integer.parseInt(count[0]);
            int columnsCount = Integer.parseInt(count[1]);

            int[][] matrix = new int[rowsCount][];
            for (int i = 0; i < rowsCount; i++) {
                matrix[i] = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            }

            int botsCount = Integer.parseInt(reader.readLine());
            int[] botMax = new int[botsCount];
            Bot[] bots = new Bot[botsCount];

            for (int i = 0; i < botsCount; i++) {
                int[] data = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

                int startX = data[0];
                int startY = data[1];
                int endX = data[2];
                int endY = data[3];
                int maxBot = getMaxForBot(matrix, startX, endX, startY, endY);

                bots[i] = new Bot(i, startX, startY, endX, endY, maxBot);
                botMax[i] = maxBot;
            }

            return new Solver(botsCount, bots, matrix, botMax, rowsCount, columnsCount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Reading fucked up. Ending");
        }
    }

    private static int getMaxForBot(int[][] matrix, int startRowIdx, int endRowIdx, int startColIdx, int endColIdx) {
        int harvested = 0;
        if (startRowIdx == endRowIdx) {
            int x = startRowIdx;
            if (startColIdx > endColIdx) {
                for (int y = startColIdx; y >= endColIdx; y--) {
                    harvested += matrix[x][y];
                }
                return harvested;
            } else {
                for (int y = startColIdx; y <= endColIdx; y++) {
                    harvested += matrix[x][y];
                }
                return harvested;
            }

        } else if (startRowIdx > endRowIdx) {
            int y = startColIdx;
            for (int x = startRowIdx; x >= endRowIdx; x--) {
                harvested += matrix[x][y];
            }
            return harvested;
        } else {
            int y = startColIdx;
            for (int x = startRowIdx; x <= endRowIdx; x++) {
                harvested += matrix[x][y];
            }
            return harvested;
        }
    }
}
