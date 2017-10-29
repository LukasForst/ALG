package alg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixReader {
    public Data readData() {
        Data result = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int numberOfDevices = Integer.parseInt(reader.readLine());
            int[][] matrix = new int[numberOfDevices][];

            int min = Integer.MAX_VALUE;
            List<List<Integer>> paths = new ArrayList<>(numberOfDevices);
            for (int i = 0; i < numberOfDevices; i++) {
                matrix[i] = Arrays.stream(reader.readLine().split(" "))
                        .filter(field -> !field.isEmpty())
                        .mapToInt(Integer::parseInt)
                        .toArray();

                OptionalInt tmpMin = Arrays.stream(matrix[i]).min();
                if(tmpMin.isPresent())
                    min = tmpMin.getAsInt() < min ? tmpMin.getAsInt() : min;

                paths.add(new ArrayList<>());
            }


            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                int[] onePath = Arrays.stream(line.split(" "))
                        .filter(field -> !field.isEmpty())
                        .mapToInt(Integer::parseInt).toArray();
                paths.get(onePath[0]).add(onePath[1]);
                paths.get(onePath[1]).add(onePath[0]);
            }

            result = new Data(matrix, numberOfDevices, paths, min);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, e.toString());
        }
        return result;
    }
}
