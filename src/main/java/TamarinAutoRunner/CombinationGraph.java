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

        // Print all nodes
        // System.out.println(this);
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
        // System.out.println("Removing node: " + node + " positioned at level " +
        // node.getLevel());
        for (var parent : node.getParents()) {
            parent.removeChild(node);
        }
        for (var child : node.getChildren()) {
            child.removeParent(node);
        }
        nodes.get(node.getLevel()).remove(node);
        // if (nodes.get(node.getLevel()).size() == 0){
        // nodes.remove(node.getLevel());
        // }
        noNodes--;
    }

    public void markFalsified(Node node) {
        // System.out.println(node);
        ArrayList<Node> parents = new ArrayList<>(node.getParents()); // create copy of parents list to avoid
                                                                      // ConcurrentModificationException
        for (var parent : parents) {
            markFalsified(parent);
        }
        removeNode(node);

        // TODO write log information
    }

    public void markVerified(Node node) {
        // System.out.println(node);
        ArrayList<Node> children = new ArrayList<>(node.getChildren()); // create copy of parents list to avoid
                                                                        // ConcurrentModificationException
        for (var child : children) {
            markVerified(child);
        }
        removeNode(node);

        // TODO write log information
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
                        && node.differenceAcestorsDescendents() < selectedNode.differenceAcestorsDescendents()) {
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
