package alg;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        MatrixReader reader = new MatrixReader();

        Data result = reader.readData();

        MatrixSolver solver = new MatrixSolver(result);

        long startTime = System.currentTimeMillis();
        System.out.println(solver.solve());

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution time: " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");

    }
}
