package alg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TreeReader {
    public Node read() {
        Node root;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int numberOfNodes = Integer.parseInt(reader.readLine());

            int[] nodes = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            root = new Node(nodes[0]);
            root.setDepth(0);
            for (int i = 1; i < numberOfNodes; i++) {
                int value = nodes[i];
                if (value > root.getValue()) {
                    addToRight(root, value, 1);
                } else {
                    addToLeft(root, value, 1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Reading fucked up. Ending");
        }

        return root;
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
