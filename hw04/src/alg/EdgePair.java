package alg;

class EdgePair {
    private final int endNode;
    private final int price;

    public EdgePair(int endNode, int price) {
        this.endNode = endNode;
        this.price = price;
    }

    public int getEndNode() {
        return endNode;
    }

    public int getPrice() {
        return price;
    }
}