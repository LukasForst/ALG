package alg;

/**
 * @author Lukas Forst
 * @date 10/10/17
 */
public class MatrixSolver {
    public int solve(Triplet[][] data) {
        int rows = data.length;
        int columns = data[0].length;

        Triplet currentMax = new Triplet(0, 0, 0);
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                int numberOfSquares = getNumberOfSquares(i, j);
                for (int s = 1; s <= numberOfSquares; s++) {
                    Triplet finalValueTriplet = data[i][j].clone();
                    finalValueTriplet.subtract(data[i - 1][j - 1]); //y
                    finalValueTriplet.subtract(data[i - s][j - s]); //m

                    finalValueTriplet.add(data[i - 1][j - s]); //k
                    finalValueTriplet.add(data[i - s][j - 1]); //l

                    if (j - s - 1 >= 0 && i - s - 1 >= 0) {
                        finalValueTriplet.add(data[i - s - 1][j - s - 1]); //c
                        finalValueTriplet.subtract(data[i][j - s - 1]); //a
                        finalValueTriplet.subtract(data[i - s - 1][j]); //b
                    } else {
                        if (j - s - 1 >= 0) {
                            finalValueTriplet.subtract(data[i][j - s - 1]); //a
                        }

                        if (i - s - 1 >= 0) {
                            finalValueTriplet.subtract(data[i - s - 1][j]); //b
                        }
                    }

                    currentMax = Triplet.getMax(currentMax, finalValueTriplet);
                }
            }
        }
        return currentMax.getNumberOfPositiveFields();
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
