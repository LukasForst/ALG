package alg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixReader {
    public Data readData() {
        long startTime = System.currentTimeMillis();
        Data data = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String[] firstLine = reader.readLine().split(" ");

            int countOfAllServers = Integer.parseInt(firstLine[0]);
            int countOfKeyServers = Integer.parseInt(firstLine[1]);

            Collection<Integer> keyServersLabels = new TreeSet<>();
            int coincidenceMatrix[][] = new int[countOfAllServers][countOfAllServers];
            for (int i = 0; i < countOfAllServers; i++) {
                String[] line = reader.readLine().split(" ");

                int node1 = Integer.parseInt(line[0]);
                int node2 = Integer.parseInt(line[1]);
                int price = Integer.parseInt(line[2]);

                coincidenceMatrix[node1][node2] = price;
                coincidenceMatrix[node2][node1] = price;
            }

            String[] lastLine = reader.readLine().split(" ");
            for (int i = 0; i < countOfKeyServers; i++) {
                keyServersLabels.add(Integer.parseInt(lastLine[i]));
            }

            data = new Data(countOfAllServers, countOfKeyServers, coincidenceMatrix, keyServersLabels);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, e.toString());
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total reading time " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");
        return data;
    }
}
