package alg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixReader {
    public static Data readData() {
        DataBuilder db = new DataBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String[] firstLine = reader.readLine().split(" ");

            int numberOfNodes = Integer.parseInt(firstLine[0]);
            int numberOfConnections = Integer.parseInt(firstLine[1]);
            db.setnumberOfConnections(numberOfConnections).setNumberOfNodes(numberOfNodes);

            int averageConnections = numberOfConnections / numberOfNodes;

            ArrayList<Collection<Integer>> connections = new ArrayList<>(numberOfNodes + 2);
            for (int i = 0; i < numberOfNodes + 2; i++) {
                connections.add(new ArrayList<>(averageConnections));
            }

            for (int i = 0; i < numberOfConnections; i++) {
                String[] line = reader.readLine().split(" ");

                int node1 = Integer.parseInt(line[0]);
                int node2 = Integer.parseInt(line[1]);

                connections.get(node1).add(node2);
                connections.get(node2).add(node1);

            }
            db.addConnection(connections);

            return db.build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, e.toString());
        }
        return null;
    }
}
