package TamarinAutoRunner;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.common.collect.Sets;

public class CombinationGraph {
    private final ArrayList<ArrayList<Node>> nodes = new ArrayList<>();
    private final int totalNumberOfThreats;
    private int noNodes = 0;

    public CombinationGraph(ArrayList<String> threats) {
        HashSet<String> set = new HashSet<>(threats);
        totalNumberOfThreats = set.size();

        var powerSet = Sets.powerSet(set);
        for (int i = 0; i < totalNumberOfThreats + 1; i++) {
            nodes.add(new ArrayList<>());
        }
        for (var threatCombination : powerSet) {
            Node node = new Node(threatCombination, totalNumberOfThreats);
            int level = node.getLevel();
            ArrayList<Node> list;
            list = nodes.get(level);
            list.add(node);
            noNodes++;
        }
        addEdges();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (var node : nodes) {
            res.append(node.toString());
        }
        return res.toString();
    }

    public void addEdges() {
        for (ArrayList<Node> level : nodes) {
            for (Node node : level) {
                if (node.getLevel() == totalNumberOfThreats) {
                    break;
                }
                for (Node node2 : nodes.get(node.getLevel() + 1)) {
                    if (node2.isChildOf(node)) {
                        node.addChild(node2);
                        node2.addParent(node);
                    }
                }
            }
        }
    }

    private void removeNode(Node node) {
        for (var parent : node.getParents()) {
            parent.removeChild(node);
        }
        for (var child : node.getChildren()) {
            child.removeParent(node);
        }
        nodes.get(node.getLevel()).remove(node);
        noNodes--;
    }

    public void markFalsified(Node node) {
        ArrayList<Node> parents = new ArrayList<>(node.getParents()); 
        for (var parent : parents) {
            markFalsified(parent);
        }
        removeNode(node);
    }

    public void markVerified(Node node) {
        ArrayList<Node> children = new ArrayList<>(node.getChildren()); 
        for (var child : children) {
            markVerified(child);
        }
        removeNode(node);
    }

    public Node getNextNode() {
        Node selectedNode = null;
        for (var level : nodes) {
            for (Node node : level) {
                if (selectedNode == null) {
                    selectedNode = node;
                    continue;
                }
                if (node.getDegree() > selectedNode.getDegree()) {
                    selectedNode = node;
                }
                if (node.getDegree() == selectedNode.getDegree()
                        && node.differenceAncestorsDescendents() < selectedNode.differenceAncestorsDescendents()) {
                    selectedNode = node;
                }
            }
        }
        return selectedNode;
    }

    public int getNumberOfNodes() {
        return noNodes;
    }
}
