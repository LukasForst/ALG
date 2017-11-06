package alg;

import java.util.*;

public class Data {
    private final int numberOfNodes;
    private final int numberOfConnections;
    private final Map<Integer, Set<Integer>> connections;
    private final List<Integer> possibleSockets;

    public Data(int numberOfNodes, int numberOfConnections, Map<Integer, Set<Integer>> connections, List<Integer> possibleSockets) {
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

    public Map<Integer, Set<Integer>> getConnections() {
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
    private int numberOfConnections;
    private Map<Integer, Set<Integer>> connections = new LinkedHashMap<>();

    public Data build() {
        TreeMap<Integer, Set<Integer>> treeMap = new TreeMap<>();
        treeMap.putAll(connections);

        List<Integer> possibleSockets = new ArrayList<>(numberOfNodes);
        for (int oneNode : treeMap.keySet()) {
            if (connections.get(oneNode).size() == 2) {
                possibleSockets.add(oneNode);
            }
        }

        return new Data(numberOfNodes, numberOfConnections, treeMap, possibleSockets);
    }

    public DataBuilder setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        return this;
    }

    public DataBuilder setnumberOfConnections(int numberOfConnections) {
        this.numberOfConnections = numberOfConnections;
        return this;
    }

    private void addOneConnection(int node1, int node2) {
        if (connections.containsKey(node1)) {
            connections.get(node1).add(node2);
        } else {
            Set<Integer> set = new HashSet<>();
            set.add(node2);
            connections.put(node1, set);
        }
    }

    public DataBuilder addConnection(int node1, int node2) {
        addOneConnection(node1, node2);
        addOneConnection(node2, node1);

        return this;
    }
}
