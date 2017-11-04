package alg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixReader {
    public Data readData() {
        DataBuilder db = new DataBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] firstLine = reader.readLine().split(" ");

            int numberOfNodes = Integer.parseInt(firstLine[0]);
            int numberOfConnections = Integer.parseInt(firstLine[1]);
            db.setNumberOfNodes(numberOfNodes)
                    .setnumberOfConnections(numberOfConnections);

            for (int i = 0; i < numberOfConnections; i++) {
                String[] line = reader.readLine().split(" ");
                db.addConnection(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
            }

            return db.build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, e.toString());
        }
        return null;
    }
}
