package alg;

import java.util.*;

public class Data {
    private final int numberOfNodes;
    private final int numberOfConnections;
    private final Map<Integer, Collection<Integer>> connections;
    private final Collection<Integer> possibleSockets;

    public Data(int numberOfNodes, int numberOfConnections, Map<Integer, Collection<Integer>> connections, Collection<Integer> possibleSockets) {
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

    public Map<Integer, Collection<Integer>> getConnections() {
        return connections;
    }

    public Collection<Integer> getPossibleSockets() {
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
    private Map<Integer, Collection<Integer>> connections = new LinkedHashMap<>();
    public Data build() {
        Collection<Integer> possibleSockets = new TreeSet<>();
        for(int key : connections.keySet()){
            if (connections.get(key).size() != 2) {
                continue;
            } else {
                possibleSockets.add(key);
            }
        }
//        Collections.sort(possibleSockets);
        return new Data(numberOfNodes, numberOfConnections, connections, possibleSockets);
    }

    public DataBuilder setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        return this;
    }

    public DataBuilder setnumberOfConnections(int numberOfConnections) {
        this.numberOfConnections = numberOfConnections;
        return this;
    }

    public DataBuilder addConnection(Map<Integer, Collection<Integer>> connections) {
        this.connections = connections;
        return this;
    }
}
