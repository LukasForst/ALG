package alg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TreeReader {
    public Data read() {
        Data data;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int numberOfNodes = Integer.parseInt(reader.readLine());

            int[] nodesArray = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            Arrays.sort(nodesArray);

            int initialRootIndex = numberOfNodes / 2;
            Node treeRoot = new Node(nodesArray[initialRootIndex], 0, null);

            constructTree(nodesArray, 0, initialRootIndex - 1, 1, treeRoot);
            constructTree(nodesArray, initialRootIndex + 1, numberOfNodes - 1, 1, treeRoot);

            String[] intervalDeletion = reader.readLine().split(" ");
            data = new Data(treeRoot, numberOfNodes, new Interval(Integer.parseInt(intervalDeletion[0]), Integer.parseInt(intervalDeletion[1])));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Reading fucked up. Ending");
        }

        return data;
    }

    private void constructTree(int[] arr, int min, int max, int depth, Node root) {
        if (max - min == 0) {
            int value = arr[max];
            Node node = new Node(value, depth, root);
            if (root.getValue() > value) {
                root.setLeft(node);
            } else {
                root.setRight(node);
            }
        } else {
            int nextNodeIndex = (max + min) / 2;
            int value = arr[nextNodeIndex];

            Node node = new Node(value, depth, root);
            if (root.getValue() > value) {
                root.setLeft(node);
            } else {
                root.setRight(node);
            }

            depth++;
            if (nextNodeIndex - 1 >= min) {
                constructTree(arr, min, nextNodeIndex - 1, depth, node);
            }

            if (nextNodeIndex + 1 <= max) {
                constructTree(arr, nextNodeIndex + 1, max, depth, node);
            }
        }
    }
}