package aoc.day8;

public class Node {
    private final String value;
    private Node left;
    private Node right;



    public Node(String value) {
        this.value = value;
    }

    public Node addNodes(Node left, Node right) {
        this.left = left;
        this.right = right;
        return this;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value='" + value + '\'' +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
