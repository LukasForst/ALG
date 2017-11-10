package alg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class Data {
    private final int numberOfNodes;
    private final int numberOfConnections;
    private final ArrayList<Collection<Integer>> connections;
    private final Collection<Integer> possibleSockets;

    public Data(int numberOfNodes, int numberOfConnections, ArrayList<Collection<Integer>> connections, Collection<Integer> possibleSockets) {
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

    public ArrayList<Collection<Integer>> getConnections() {
        return connections;
    }

    public Collection<Integer> getPossibleSockets() {
        return possibleSockets;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(numberOfNodes).append(" ").append(numberOfConnections).append("\n");

        for (int node = 0; node < connections.size(); node++) {
            sb.append(node).append(": ");

            for (int connected : connections.get(node)) {
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
    private ArrayList<Collection<Integer>> connections;

    public Data build() {
        Collection<Integer> possibleSockets = new TreeSet<>();
        for (int i = 1; i < connections.size(); i++) {
            if (connections.get(i).size() == 2) {
                possibleSockets.add(i);
            }
        }

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

    public DataBuilder addConnection(ArrayList<Collection<Integer>> connections) {
        this.connections = connections;
        return this;
    }
}
