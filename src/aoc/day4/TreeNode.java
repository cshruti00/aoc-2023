package aoc.day4;

import java.util.LinkedList;
import java.util.List;

public class TreeNode {

    private int value;
    private List<TreeNode> childNodes;

    public TreeNode(int value) {
        this.value = value;
        this.childNodes = new LinkedList<>();
    }

    public void addChild(TreeNode childNode) {
        this.childNodes.add(childNode);
    }

//    public void showTreeNodes() {
//        BreathFirstSearchPrintTreeNodes.printNodes(this);
//    }

    public int getValue() {
        return value;
    }

    public List<TreeNode> getChildNodes() {
        return childNodes;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "value=" + value +
                ", childNodes=" + childNodes +
                '}';
    }


}
