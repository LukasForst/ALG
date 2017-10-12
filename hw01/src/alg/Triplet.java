package alg;

public class Triplet implements Cloneable {
    private int realValue;
    private int computedValue;
    private int numberOfPositiveFields;

    public Triplet(int realValue, int computedValue, int numberOfPositiveFields) {
        this.realValue = realValue;
        this.computedValue = computedValue;
        this.numberOfPositiveFields = numberOfPositiveFields;
    }

    /**
     * t1 + t2
     * Note that this method creates new object!
     */
    public static Triplet add(Triplet t1, Triplet t2) {
        return new Triplet(t1.getRealValue() + t2.getRealValue(),
                t1.getComputedValue() + t2.getComputedValue(),
                t1.getNumberOfPositiveFields() + t2.getNumberOfPositiveFields());
    }

    /**
     * t1 - t2
     * Note that this method creates new object!
     */
    public static Triplet subtract(Triplet t1, Triplet t2) {
        return new Triplet(t1.getRealValue() - t2.getRealValue(),
                t1.getComputedValue() - t2.getComputedValue(),
                t1.getNumberOfPositiveFields() - t2.getNumberOfPositiveFields());
    }

    public static Triplet getMax(Triplet t1, Triplet t2) {
        if (t1.getComputedValue() >= 0 && t2.getComputedValue() >= 0) {
            return t1.getNumberOfPositiveFields() > t2.getNumberOfPositiveFields() ? t1 : t2;
        } else if (t1.getComputedValue() >= 0) {
            return t1;
        } else if (t2.getComputedValue() >= 0) {
            return t2;
        }

        return t1.getNumberOfPositiveFields() > t2.getNumberOfPositiveFields() ? t1 : t2;
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

    /**
     * this + triplet
     * Note that this method modifies current instance of Triplet!
     */
    public void add(Triplet triplet) {
        computedValue += triplet.getComputedValue();
        numberOfPositiveFields += triplet.getNumberOfPositiveFields();
    }

    /**
     * this - triplet
     * Note that this method modifies current instance of Triplet!
     */
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

    @Override
    protected Triplet clone() {
        return new Triplet(realValue, computedValue, numberOfPositiveFields);
    }
}
