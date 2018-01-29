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
                int maxBot = 0;

                int stX;
                int edX;
                if (startX > endX) {
                    stX = endX;
                    edX = startX;
                } else {
                    stX = startX;
                    edX = endX;
                }
                int stY;
                int edY;
                if (startY > endY) {
                    stY = endY;
                    edY = stY;
                } else {
                    stY = startY;
                    edY = endY;
                }

                for (int x = stX; x <= edX; x++) {
                    for (int y = stY; y <= edY; y++) {
                        maxBot += matrix[x][y];
                    }
                }

                bots[i] = new Bot(i, startX, startY, endX, endY, maxBot);
                botMax[i] = maxBot;
            }

            return new Solver(botsCount, bots, matrix, botMax, new boolean[rowsCount][columnsCount], rowsCount, columnsCount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Reading fucked up. Ending");
        }
    }
}
