package alg;

/**
 * @author Lukas Forst
 * @date 10/10/17
 */
public class Main {
    public static void main(String[] args) {
        MatrixReader reader = new MatrixReader();
        MatrixSolver solver = new MatrixSolver();

        Triplet[][] data = reader.read();
        for (Triplet[] row : data) {
            for (Triplet triplet : row) {
                System.out.print(triplet + " ");
            }
            System.out.println();
        }

        System.out.println("Result is: " + solver.solve(data));
    }
}
