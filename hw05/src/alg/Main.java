package alg;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TreeReader reader = new TreeReader();

        Node root = reader.read();
        System.out.println(root);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.err.println("Total execution time: " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");
    }
}
