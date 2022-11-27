package TamarinAutoRunner;

import java.util.HashSet;
import java.util.Set;

public class Node implements Comparable<Node> {
    private final Set<String> threats = new HashSet<>(); // Threats (represented as strings) present in this node.
    private final int noThreats; // Number of individual threats in this node.
    private final int level;
    private final HashSet<Node> parents = new HashSet<>();
    private final HashSet<Node> children = new HashSet<>();

    public Node(Set<String> threatCombination, int totalNumberOfThreats) {
        noThreats = threatCombination.size();
        level = totalNumberOfThreats - noThreats;
        threats.addAll(threatCombination);
    }

    public Set<String> getThreats() {
        return threats;
    }

    public int getLevel() {
        return level;
    }

    public boolean isChildOf(Node node) {
        for (String threat : threats) {
            if (!node.threats.contains(threat)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return ("Node(" + threats + ", noThreats: " + noThreats + ", degree: " + getDegree()
                + ", DifferenceInOutDegree: " + differenceAcestorsDescendents() + ")");
    }

    @Override
    public int compareTo(Node node) {
        if (noThreats < node.noThreats) {
            return -1;
        } else if (noThreats == node.noThreats) {
            return 0;
        }
        return 1;
    }

    public void addParent(Node parent) {
        parents.add(parent);
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public void removeChild(Node child) {
        children.remove(child);
    }

    public void removeParent(Node parent) {
        parents.remove(parent);
    }

    public HashSet<Node> getParents() {
        return parents;
    }

    public HashSet<Node> getChildren() {
        return children;
    }

    public int countAncestors() {
        return getAllAncestors().size();
    }

    private Set<Node> getAllAncestors() {
        HashSet<Node> set = new HashSet<>();
        set.addAll(getParents());
        for (Node parent : (HashSet<Node>) set.clone()) {
            set.addAll(parent.getAllAncestors());
        }
        return set;
    }

    public int countDescendents() {
        return getAllDescendents().size();
    }

    private Set<Node> getAllDescendents() {
        HashSet<Node> set = new HashSet<>();
        set.addAll(getChildren());
        for (Node parent : (HashSet<Node>) set.clone()) {
            set.addAll(parent.getAllDescendents());
        }
        return set;
    }

    public int getDegree() {
        return parents.size() + children.size();
    }

    public int differenceAcestorsDescendents() {
        return Math.abs(countDescendents() - countAncestors());
    }
}