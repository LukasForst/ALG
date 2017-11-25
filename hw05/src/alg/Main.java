package alg;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TreeReader reader = new TreeReader();

        Data data = reader.read();
        System.err.println("Nodes count: " + data.getNumberOfNodesInTree());
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.err.println("Total execution time: " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");
    }
}
