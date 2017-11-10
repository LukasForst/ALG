package alg;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixReader {
    public Data readData() {
        DataBuilder db = new DataBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] firstLine = reader.readLine().split(" ");
            long startTime = System.currentTimeMillis();

            int numberOfNodes = Integer.parseInt(firstLine[0]);
            int numberOfConnections = Integer.parseInt(firstLine[1]);
            db.setnumberOfConnections(numberOfConnections).setNumberOfNodes(numberOfNodes);
            long readingAndAddingConnectgions = System.currentTimeMillis();

            Map<Integer, Collection<Integer>> connections = new LinkedHashMap<>();
            for (int i = 0; i < numberOfConnections; i++) {
                String[] line = reader.readLine().split(" ");

                int node = Integer.parseInt(line[0]);
                int node2 = Integer.parseInt(line[1]);

                Collection<Integer> list = connections.get(node);
                if(list != null){
                    list.add(node2);
                } else {
                    list = new LinkedList<>();
                    list.add(node2);
                    connections.put(node, list);
                }

                list = connections.get(node2);
                if(list != null){
                    list.add(node);
                } else {
                    list = new LinkedList<>();
                    list.add(node);
                    connections.put(node2, list);
                }
            }
            db.addConnection(connections);

            long totalAddingConnection = System.currentTimeMillis() - readingAndAddingConnectgions;
            System.out.println("Total adding connections " + TimeUnit.MILLISECONDS.toSeconds(totalAddingConnection) + "s = " + totalAddingConnection + "ms");
//
            long buildingStartTime = System.currentTimeMillis();
            Data d = db.build();
            long buildingTotalTime = System.currentTimeMillis() - buildingStartTime;
            System.out.println("Total building " + TimeUnit.MILLISECONDS.toSeconds(buildingTotalTime) + "s = " + buildingTotalTime + "ms");

            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Total reading " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");

            return d;
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, e.toString());
        }
        return null;
    }
}
