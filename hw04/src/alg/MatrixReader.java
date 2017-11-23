package alg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
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

            long initTime = System.currentTimeMillis();
            ArrayList<Collection<EdgePair>> adjacencyList = new ArrayList<>(countOfAllServers + 2);

            for (int i = 0; i < countOfAllServers; i++) {
                adjacencyList.add(new ArrayList<>());
            }
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - initTime;
//            System.out.println("Initialising time " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");

            initTime = System.currentTimeMillis();
            for (int i = 0; i < countOfAllServers; i++) {
                String[] line = reader.readLine().split(" ");

                int node1 = Integer.parseInt(line[0]);
                int node2 = Integer.parseInt(line[1]);
                int price = Integer.parseInt(line[2]);

                adjacencyList.get(node1).add(new EdgePair(node2, price));
                adjacencyList.get(node2).add(new EdgePair(node1, price));
            }
            endTime = System.currentTimeMillis();
            totalTime = endTime - initTime;
//            System.out.println("Reading nodes time " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");

            initTime = System.currentTimeMillis();
            String[] lastLine = reader.readLine().split(" ");
            boolean[] isKeyServer = new boolean[countOfAllServers];
            for (int i = 0; i < countOfKeyServers; i++) {
                int keyServerId = Integer.parseInt(lastLine[i]);
                isKeyServer[keyServerId] = true;
            }
            endTime = System.currentTimeMillis();
            totalTime = endTime - initTime;
//            System.out.println("Reading keyservers time " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms\n");
            data = new Data(countOfAllServers, countOfKeyServers, adjacencyList, isKeyServer);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, e.toString());
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
//        System.out.println("Total reading time " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");
        return data;
    }
}