package alg;

/**
 * @author Lukas Forst
 * @date 10/10/17
 */
public class Main {
    public static void main(String[] args) {
        MatrixReader reader = new MatrixReader();
        MatrixSolver solver = new MatrixSolver();

        int[][] data = reader.read();
        System.out.println(solver.solve(data));
    }
}
