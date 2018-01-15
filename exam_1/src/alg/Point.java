package alg;

import java.util.ArrayList;
import java.util.Collection;

public class Point {
    public int value;
    public Sector sector = null;
    public int sectorSize = Integer.MAX_VALUE - 20;
    public int reconfigPrice = Integer.MAX_VALUE - 20;
    public int pathPrice = Integer.MAX_VALUE - 20;
    public Collection<Point> previous = new ArrayList<>();

    public Point(int value) {
        this.value = value;
    }
}

class Sector {
    public int size = 1;
}
