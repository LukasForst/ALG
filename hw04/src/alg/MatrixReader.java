package alg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

public class MatrixReader {
    public Data readData() throws Exception {
        Data data = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] firstLine = reader.readLine().split(" ");

        int countOfAllServers = Integer.parseInt(firstLine[0]);
        int countOfKeyServers = Integer.parseInt(firstLine[1]);

        ArrayList<Collection<EdgePair>> adjacencyList = new ArrayList<>(countOfAllServers);

        for (int i = 0; i < countOfAllServers; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int i = 0; i < countOfAllServers; i++) {
            String[] line = reader.readLine().split(" ");

            int node1 = Integer.parseInt(line[0]);
            int node2 = Integer.parseInt(line[1]);
            int price = Integer.parseInt(line[2]);

            adjacencyList.get(node1).add(new EdgePair(node2, price));
            adjacencyList.get(node2).add(new EdgePair(node1, price));
        }

        String[] lastLine = reader.readLine().split(" ");
        boolean[] isKeyServer = new boolean[countOfAllServers];
        for (int i = 0; i < countOfKeyServers; i++) {
            int keyServerId = Integer.parseInt(lastLine[i]);
            isKeyServer[keyServerId] = true;
        }

        data = new Data(countOfAllServers, countOfKeyServers, adjacencyList, isKeyServer);
        return data;
    }
}