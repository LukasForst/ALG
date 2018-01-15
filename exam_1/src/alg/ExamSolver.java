package alg;

public class ExamSolver {
    private int[][] matrix;
    private int rowsCount;
    private int columnsCount;
    private int startRowIndex;
    private int startColumnIndex;
    private int endRowIndex;
    private int endColumnIndex;

    public ExamSolver(int[][] matrix, int rowsCount, int columnsCount, int startRowIndex, int startColumnIndex, int endRowIndex, int endColumnIndex) {
        this.matrix = matrix;
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
        this.startRowIndex = startRowIndex;
        this.startColumnIndex = startColumnIndex;
        this.endRowIndex = endRowIndex;
        this.endColumnIndex = endColumnIndex;
    }

    public Result solve() {
        int reconfigPrice = Integer.MAX_VALUE;
        int pathPrice = Integer.MAX_VALUE;


        return new Result(reconfigPrice, pathPrice);
    }
}
