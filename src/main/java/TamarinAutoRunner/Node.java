package TamarinAutoRunner;

import java.util.HashSet;
import java.util.Set;

public class Node implements Comparable<Node> {
    private final Set<String> threats = new HashSet<>(); 
    private final int noThreats;
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
        return ("Node(" + threats + ")");
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

    public int isGreaterThan(Node that) {
        int thisNodeThreats = this.threats.size();
        int thatNodeThreats = that.threats.size();

        if (thisNodeThreats > thatNodeThreats && this.threats.containsAll(that.threats)) {
            return 1;
        } else if (thatNodeThreats > thisNodeThreats && that.threats.containsAll(this.threats)) {
            return -1;
        }
        return 0;
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

    public int differenceAncestorsDescendents() {
        return Math.abs(countDescendents() - countAncestors());
    }
}