package alg;

public class Main {
    public static void main(String[] args) {
        MatrixReader reader = new MatrixReader();
        Data result = reader.readData();
        System.out.println(result.toString());
    }
}
