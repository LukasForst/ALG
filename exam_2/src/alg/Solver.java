package alg;

public class Solver {
    private int botsCount;
    private Bot[] bots;
    private int[] botsMax;

    private int rowsCount;
    private int columnsCount;
    private int[][] matrix;
    private boolean[][] visited;

    private int max = Integer.MIN_VALUE;
    private int currentValue = 0;

    public Solver(int botsCount, Bot[] bots, int[][] matrix, int[] botsMax, boolean[][] visited, int rowsCount, int columnsCount) {
        this.botsCount = botsCount;
        this.bots = bots;
        this.matrix = matrix;
        this.botsMax = botsMax;
        this.visited = visited;
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
    }

    public int solve() {
        return 0;
    }
}
