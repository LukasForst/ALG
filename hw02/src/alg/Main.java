package alg;

public class Main {
    public static void main(String[] args) {
        MatrixReader reader = new MatrixReader();
        Data result = reader.readData();
        System.out.println(result.toString());
        MatrixSolver solver = new MatrixSolver(result);
        System.out.println(solver.solve());
    }
}
