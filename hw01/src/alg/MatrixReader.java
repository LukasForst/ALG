package alg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Lukas Forst
 * @date 10/10/17
 */
public class MatrixReader {

    private static int flipNumber(int number) {
        return number == 2 ? -2 : number;
    }

    public int[][] read() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            Pair<Integer, Integer> size = readSize(reader);
            int[][] data = new int[size.getLeft()][];

            for (int i = 0; i < data.length; i++) {
                String[] stringNumbers = reader.readLine().split(" ");
                data[i] = Arrays.stream(stringNumbers)
                        .mapToInt(Integer::parseInt)
                        .map(MatrixReader::flipNumber)
                        .toArray();
            }

            return data;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, e.toString());
            return new int[0][0];
        }
    }

    private Pair<Integer, Integer> readSize(BufferedReader reader) throws IOException {
        String[] line = reader.readLine().split(" ");
        int rows = Integer.parseInt(line[0]);
        int columns = Integer.parseInt(line[1]);
        return new Pair<>(rows, columns);
    }
}
