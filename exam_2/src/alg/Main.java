package alg;

public class Main {
    public static void main(String[] args) {
        Solver solver = DataReader.read();

        System.out.println(solver.solve());
    }
}
