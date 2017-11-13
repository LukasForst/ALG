package alg;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        MatrixReader reader = new MatrixReader();
        Data result = reader.readData();
//        System.out.println(result);
/*
        long solveStartTime = System.currentTimeMillis();
        MatrixSolver solver = new MatrixSolver(result);
        System.out.println(solver.solve());

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - solveStartTime;
        System.out.println("Solve time " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");

        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("Total execution time: " + TimeUnit.MILLISECONDS.toSeconds(totalTime) + "s = " + totalTime + "ms");
        */
    }
}
