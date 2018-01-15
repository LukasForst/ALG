package alg;

public class Main {
    public static void main(String[] args) {
        ExamSolver solver = MatrixReader.read();

        Result result = solver.solve();

        System.out.println(result.recofingPrice + " " + result.pathPrice);
    }
}
