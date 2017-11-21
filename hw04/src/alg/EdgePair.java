package alg;

class EdgePair {
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
}