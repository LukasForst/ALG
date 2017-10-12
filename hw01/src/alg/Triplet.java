package alg;

public class Triplet {
    private int realValue;
    private int computedValue;
    private int numberOfPositiveFields;

    public Triplet(int realValue, int computedValue, int numberOfPositiveFields) {
        this.realValue = realValue;
        this.computedValue = computedValue;
        this.numberOfPositiveFields = numberOfPositiveFields;
    }

    public int getRealValue() {
        return realValue;
    }

    public void setRealValue(int realValue) {
        this.realValue = realValue;
    }

    public int getComputedValue() {
        return computedValue;
    }

    public void setComputedValue(int computedValue) {
        this.computedValue = computedValue;
    }

    public int getNumberOfPositiveFields() {
        return numberOfPositiveFields;
    }

    public void setNumberOfPositiveFields(int numberOfPositiveFields) {
        this.numberOfPositiveFields = numberOfPositiveFields;
    }

    public void add(Triplet triplet) {
        computedValue += triplet.getComputedValue();
        numberOfPositiveFields += triplet.getNumberOfPositiveFields();
    }

    public void subtract(Triplet triplet) {
        computedValue -= triplet.getComputedValue();
        numberOfPositiveFields -= triplet.getNumberOfPositiveFields();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triplet triplet = (Triplet) o;

        if (realValue != triplet.realValue) return false;
        if (computedValue != triplet.computedValue) return false;
        return numberOfPositiveFields == triplet.numberOfPositiveFields;
    }

    @Override
    public int hashCode() {
        int result = realValue;
        result = 31 * result + computedValue;
        result = 31 * result + numberOfPositiveFields;
        return result;
    }

    @Override
    public String toString() {
        return "{" + realValue + ", " + computedValue + ", " + numberOfPositiveFields + "}";
    }
}
