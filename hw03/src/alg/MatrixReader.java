package alg;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
            for (int i = 0; i < numberOfConnections; i++) {
                String[] line = reader.readLine().split(" ");
                db.addConnection(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
            }
            long totalAddingConnection = System.currentTimeMillis() - readingAndAddingConnectgions;
            System.out.println("Total adding connections " + TimeUnit.MILLISECONDS.toSeconds(totalAddingConnection) + "s = " + totalAddingConnection + "ms");

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
