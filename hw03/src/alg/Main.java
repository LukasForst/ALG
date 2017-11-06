package alg;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        MatrixReader reader = new MatrixReader();

        Data result = reader.readData();

        System.out.println(result);
        MatrixSolver solver = new MatrixSolver(result);
        long startTime = System.currentTimeMillis();
        List<Integer> solved = solver.solve();
        System.out.println(Arrays.toString(solved.toArray()));

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution time: " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");
    }
}
