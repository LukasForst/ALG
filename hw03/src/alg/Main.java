package alg;

import java.util.Collection;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Data result = MatrixReader.readData();
        MatrixSolver solver = new MatrixSolver(result);

        Collection<Integer> solved = solver.solve();
        Iterator<Integer> iterator = solved.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(iterator.next());

        int printed = 1;
        while (iterator.hasNext() && printed++ != 100) {
            sb.append(" ").append(iterator.next());
        }
        System.out.println(sb.toString());
    }
}
