package alg;

import java.util.*;

public class Data {
    private final int numberOfNodes;
    private final int numberOfConnections;
    private final Map<Integer, List<Integer>> connections;
    private final List<Integer> possibleSockets;

    public Data(int numberOfNodes, int numberOfConnections, Map<Integer, List<Integer>> connections, List<Integer> possibleSockets) {
        this.numberOfNodes = numberOfNodes;
        this.numberOfConnections = numberOfConnections;
        this.connections = connections;
        this.possibleSockets = possibleSockets;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public int getNumberOfConnections() {
        return numberOfConnections;
    }

    public Map<Integer, List<Integer>> getConnections() {
        return connections;
    }

    public List<Integer> getPossibleSockets() {
        return possibleSockets;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(numberOfNodes).append(" ").append(numberOfConnections).append("\n");

        for (int key : connections.keySet()) {
            sb.append(key).append(": ");

            for (int connected : connections.get(key)) {
                sb.append(connected).append(" ");
            }
            sb.append("\n");
        }

        sb.append("Possible sockets:\n");
        for (int node : possibleSockets) {
            sb.append(node).append(" ");
        }
        sb.append("\n");

        return sb.toString();
    }

}

class DataBuilder {
    private int numberOfNodes;
    private int numberOfConnections = -1;
    private Map<Integer, List<Integer>> connections = new LinkedHashMap<>();
    private Set<Integer> possibleSockets = new HashSet<>();
    public Data build() {
        List<Integer> possibleSockets = new ArrayList<>(numberOfNodes);
        possibleSockets.addAll(this.possibleSockets);

        return new Data(numberOfNodes, numberOfConnections, connections, possibleSockets);
    }

    public DataBuilder setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        possibleSockets = new HashSet<>(numberOfNodes);
        int averageConnections = numberOfConnections / numberOfNodes;
        for(int i = 1; i < numberOfNodes + 1; i++){
            connections.put(i, new ArrayList<>(averageConnections));
        }

        return this;
    }

    public DataBuilder setnumberOfConnections(int numberOfConnections) {
        this.numberOfConnections = numberOfConnections;
        return this;
    }

    private void addOneConnection(int node1, int node2) {
        List<Integer> list = connections.get(node1);
        list.add(node2);
        if(list.size() == 2){
            possibleSockets.add(node1);
        } else{
            possibleSockets.remove((Integer) node1);
        }
    }

    public DataBuilder addConnection(int node1, int node2) {
        addOneConnection(node1, node2);
        addOneConnection(node2, node1);

        return this;
    }
}
