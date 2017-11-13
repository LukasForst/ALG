package alg;

class EdgePair implements Comparable<EdgePair> {
    private final int startNode;
    private final int endNode;
    private final int price;

    public EdgePair(int startNode, int endNode, int price) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.price = price;
    }

    public int getStartNode() {
        return startNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int compareTo(EdgePair o) {
        return getPrice() - o.getPrice();
    }
}