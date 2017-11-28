package alg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.TreeSet;

public class TreeReader {
    public Data read() {
        Data data;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int numberOfNodes = Integer.parseInt(reader.readLine());

            Collection<Integer> allNodes = new TreeSet<>();
            String[] dataRead = reader.readLine().split(" ");
            int rootValue = Integer.parseInt(dataRead[0]);
            Node root = new Node(rootValue, 0, null);
            allNodes.add(rootValue);

            for (int i = 1; i < numberOfNodes; i++) {
                int value = Integer.parseInt(dataRead[i]);
                allNodes.add(value);
                if (value > root.getValue()) {
                    addToRight(root, value, 1);
                } else {
                    addToLeft(root, value, 1);
                }
            }
            dataRead = reader.readLine().split(" ");
            data = new Data(root, numberOfNodes, new Interval(Integer.parseInt(dataRead[0]), Integer.parseInt(dataRead[1])), allNodes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Reading fucked up. Ending");
        }

        return data;
    }

    private void addToLeft(Node parent, int valueToInsert, int depth) {
        Node left = parent.getLeft();
        if (left == null) {
            Node toAdd = new Node(valueToInsert, depth, parent);
            parent.setLeft(toAdd);
        } else if (left.getValue() > valueToInsert) {
            addToLeft(left, valueToInsert, ++depth);
        } else if (left.getValue() < valueToInsert) {
            addToRight(left, valueToInsert, ++depth);
        } else {
            throw new IllegalArgumentException("Wrong argument, this is set. Value " + valueToInsert + " cannot be inserted.");
        }
    }

    private void addToRight(Node parent, int valueToInsert, int depth) {
        Node right = parent.getRight();
        if (right == null) {
            Node toAdd = new Node(valueToInsert, depth, parent);
            parent.setRight(toAdd);
        } else if (right.getValue() > valueToInsert) {
            addToLeft(right, valueToInsert, ++depth);
        } else if (right.getValue() < valueToInsert) {
            addToRight(right, valueToInsert, ++depth);
        } else {
            throw new IllegalArgumentException("Wrong argument, this is set. Value " + valueToInsert + " cannot be inserted.");
        }
    }
}