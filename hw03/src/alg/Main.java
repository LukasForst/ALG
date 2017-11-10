package alg;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        Data result = MatrixReader.readData();
        MatrixSolver solver = new MatrixSolver(result);

        long solveStartTime = System.currentTimeMillis();

        Collection<Integer> solved = solver.solve();

        long totalTime = System.currentTimeMillis() - solveStartTime;
        System.out.println("SolveTime time: " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms\n");

        Iterator<Integer> iterator = solved.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(iterator.next());
        int printed = 1;
        while (iterator.hasNext() && printed++ != 100) {
            sb.append(" ").append(iterator.next());
        }
        System.out.println(sb.toString());

        long endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("Execution time: " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");
    }
}
