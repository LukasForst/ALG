package alg;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        MatrixReader reader = new MatrixReader();

        Data result = reader.readData();
        MatrixSolver solver = new MatrixSolver(result);
        System.out.println(solver.solve());

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.err.println("Total execution time: " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");
    }
}
