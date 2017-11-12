package alg;

/**
 * @author Lukas Forst
 * @date 10/10/17
 */
public class MatrixSolver {
    public int solve(Triplet[][] data) {
        int rows = data.length;
        int columns = data[0].length;

        int currentMax = 0;
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                int numberOfSquares = getNumberOfSquares(i, j);
                for (int s = 1; s <= numberOfSquares; s++) {
                    int numberOfOnes = data[i][j].getNumberOfPositiveFields();
                    int computedValue = data[i][j].getComputedValue();

                    numberOfOnes -= data[i - 1][j - 1].getNumberOfPositiveFields();
                    computedValue -= data[i - 1][j - 1].getComputedValue();

                    numberOfOnes -= data[i - s][j - s].getNumberOfPositiveFields();
                    computedValue -= data[i - s][j - s].getComputedValue();

                    numberOfOnes += data[i - 1][j - s].getNumberOfPositiveFields();
                    computedValue += data[i - 1][j - s].getComputedValue();

                    numberOfOnes += data[i - s][j - 1].getNumberOfPositiveFields();
                    computedValue += data[i - s][j - 1].getComputedValue();

                    if (j - s - 1 >= 0 && i - s - 1 >= 0) {

                        numberOfOnes += data[i - s - 1][j - s - 1].getNumberOfPositiveFields();
                        computedValue += data[i - s - 1][j - s - 1].getComputedValue();

                        numberOfOnes -= data[i][j - s - 1].getNumberOfPositiveFields();
                        computedValue -= data[i][j - s - 1].getComputedValue();

                        numberOfOnes -= data[i - s - 1][j].getNumberOfPositiveFields();
                        computedValue -= data[i - s - 1][j].getComputedValue();


                    } else {
                        if (j - s - 1 >= 0) {
                            numberOfOnes -= data[i][j - s - 1].getNumberOfPositiveFields();
                            computedValue -= data[i][j - s - 1].getComputedValue();

                        }

                        if (i - s - 1 >= 0) {
                            numberOfOnes -= data[i - s - 1][j].getNumberOfPositiveFields();
                            computedValue -= data[i - s - 1][j].getComputedValue();
                        }
                    }

                    currentMax = computedValue >= 0 && numberOfOnes > currentMax ? numberOfOnes : currentMax;
                }
            }
        }
        return currentMax;
    }

    private int getNumberOfSquares(int row, int column) {
        if (row == column) {
            return row;
        } else if (row < column) {
            return row;
        } else {
            return column;
        }
    }
}
