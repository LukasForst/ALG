package alg;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        MatrixReader reader = new MatrixReader();
        Data result = reader.readData();
        MatrixSolver solver = new MatrixSolver(result);
        Collection<Integer> solved = solver.solve();
//        solved = solver.solve();
//        solved = solver.solve();
//        solved = solver.solve();
//        solved = solver.solve();
//        solved = solver.solve();

//        System.out.print(solved.get(0));
//        int printSize = solved.size() > 100 ? 100 : solved.size();
//        for(int i = 1; i < printSize; i++){
//            System.out.print(" " + solved.get(i));
//        }

        Iterator<Integer> iterator = solved.iterator();
        System.out.print(iterator.next());
        int printed = 1;
        while(iterator.hasNext() && printed++ != 100){
            System.out.print(" " + iterator.next());
        }

        System.out.println();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution time: " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");
    }
}
