package alg;


public class ExamSolver {
    private Point[][] matrix;
    private int rowsCount;
    private int columnsCount;
    private int startRowIndex;
    private int startColumnIndex;
    private int endRowIndex;
    private int endColumnIndex;

    public ExamSolver(Point[][] matrix, int rowsCount, int columnsCount, int startRowIndex, int startColumnIndex, int endRowIndex, int endColumnIndex) {
        this.matrix = matrix;
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
        this.startRowIndex = startRowIndex;
        this.startColumnIndex = startColumnIndex;
        this.endRowIndex = endRowIndex;
        this.endColumnIndex = endColumnIndex;
    }

    public Result solve() {
        startReCreatingMatrix();

        Point start = matrix[startRowIndex][startColumnIndex];
        start.reconfigPrice = 0;
        start.pathPrice = 0;

        goDeeper(null, start, startRowIndex + 1, startColumnIndex);
        goDeeper(null, start, startRowIndex - 1, startColumnIndex);
        goDeeper(null, start, startRowIndex, startColumnIndex + 1);
        goDeeper(null, start, startRowIndex, startColumnIndex - 1);

        Point end = matrix[endRowIndex][endColumnIndex];
        return new Result(end.reconfigPrice, end.pathPrice);
    }

    private void createPathsFrom(Point previousPoint, int rowIndex, int columnIndex) {
        Point currentPoint = matrix[rowIndex][columnIndex];

        if (hasToReconfigure(previousPoint, currentPoint)) {
            currentPoint.reconfigPrice = previousPoint.reconfigPrice + 1;
        } else {
            currentPoint.reconfigPrice = previousPoint.reconfigPrice;
        }

        currentPoint.pathPrice = previousPoint.pathPrice + 1;
        currentPoint.previous.clear();
        currentPoint.previous.add(previousPoint);

        goDeeper(previousPoint, currentPoint, rowIndex + 1, columnIndex);
        goDeeper(previousPoint, currentPoint, rowIndex - 1, columnIndex);
        goDeeper(previousPoint, currentPoint, rowIndex, columnIndex + 1);
        goDeeper(previousPoint, currentPoint, rowIndex, columnIndex - 1);
    }

    private boolean hasToReconfigure(Point from, Point to) {
        return from.sector != to.sector && Math.abs(from.sectorSize - to.sectorSize) > Math.min(to.sectorSize, from.sectorSize);
    }

    private void goDeeper(Point previousPoint, Point currentPoint, int rowIndex, int columnIndex) {
        if (rowIndex >= rowsCount || rowIndex < 0 || columnIndex >= columnsCount || columnIndex < 0) return;

        if (previousPoint == null) {
            createPathsFrom(currentPoint, rowIndex, columnIndex);
            return;
        }

        Point next = matrix[rowIndex][columnIndex];
        if (next == previousPoint) return;

        if (hasToReconfigure(next, currentPoint)) {
            if (currentPoint.reconfigPrice + 1 < next.reconfigPrice)
                createPathsFrom(currentPoint, rowIndex, columnIndex);
            else if (currentPoint.reconfigPrice + 1 == next.reconfigPrice) {
                if (currentPoint.pathPrice + 1 < next.pathPrice) createPathsFrom(currentPoint, rowIndex, columnIndex);
                else if (currentPoint.pathPrice + 1 == next.pathPrice) {
                    next.previous.add(currentPoint);
                }
            }
        } else {
            if (currentPoint.reconfigPrice < next.reconfigPrice) createPathsFrom(currentPoint, rowIndex, columnIndex);
            else if (currentPoint.reconfigPrice == next.reconfigPrice) {
                if (currentPoint.pathPrice + 1 < next.pathPrice) createPathsFrom(currentPoint, rowIndex, columnIndex);
                else if (currentPoint.pathPrice + 1 == next.pathPrice) next.previous.add(currentPoint);
            }
        }
    }

    private void startReCreatingMatrix() {
        for (int row = 0; row < rowsCount; row++) {
            for (int column = 0; column < columnsCount; column++) {
                Point current = matrix[row][column];
                if (current.sector != null) continue;

                Sector sector = new Sector();
                current.sector = sector;
                recreateMatrix(current.value, row, column, sector);
            }
        }

        for (int row = 0; row < rowsCount; row++) {
            for (int column = 0; column < columnsCount; column++) {
                Point curr = matrix[row][column];
                curr.sectorSize = curr.sector.size;
            }
        }
    }

    private void recreateMatrix(int value, int rowIndex, int columnIndex, Sector sector) {
        if (rowIndex - 1 >= 0) {
            Point curr = matrix[rowIndex - 1][columnIndex];
            if (curr.sector == null && curr.value == value) {
                curr.sector = sector;
                sector.size++;
                recreateMatrix(value, rowIndex - 1, columnIndex, sector);
            }
        }

        if (rowIndex + 1 < rowsCount) {
            Point curr = matrix[rowIndex + 1][columnIndex];
            if (curr.sector == null && curr.value == value) {
                curr.sector = sector;
                sector.size++;
                recreateMatrix(value, rowIndex + 1, columnIndex, sector);
            }
        }

        if (columnIndex - 1 >= 0) {
            Point curr = matrix[rowIndex][columnIndex - 1];
            if (curr.sector == null && curr.value == value) {
                curr.sector = sector;
                sector.size++;
                recreateMatrix(value, rowIndex, columnIndex - 1, sector);
            }
        }

        if (columnIndex + 1 < columnsCount) {
            Point curr = matrix[rowIndex][columnIndex + 1];
            if (curr.sector == null && curr.value == value) {
                curr.sector = sector;
                sector.size++;
                recreateMatrix(value, rowIndex, columnIndex + 1, sector);
            }
        }
    }
}
