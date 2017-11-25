package alg;

enum IntervalResult {
    BIGGER, SMALLER, IN_INTERVAL
}

public class Interval {
    private final int min;
    private final int max;

    public Interval(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    /**
     * Returns -1 if it is smaller than interval, 1 if it is bigger and 0 if interval contains this value;
     */
    public IntervalResult intervalState(int valueToVerify) {
        if (min > valueToVerify)
            return IntervalResult.SMALLER;
        else if (max < valueToVerify)
            return IntervalResult.BIGGER;
        return IntervalResult.IN_INTERVAL;
    }

    @Override
    public String toString() {
        return "Interval{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
