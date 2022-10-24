import java.util.HashSet;
import java.util.Set;

public class Node implements Comparable {
    private Set<String> threats = new HashSet<>(); // Threats (represented as strings) present in this node.
    private int noThreats = 0; // Number of individual threats in this node.
    private int level;
    private HashSet<Node> parents = new HashSet<>();
    private HashSet<Node> children = new HashSet<>();

    public Node(Set<String> threatCombination, int totalNumberOfThreats) {
        noThreats = threatCombination.size();
        level = totalNumberOfThreats - noThreats;
        for (String threat : threatCombination) {
            threats.add(threat);
        }
    }

    public Set<String> getThreats () {
        return threats;
    }

    public int getLevel() {
        return level;
    }

    public boolean isChildOf(Node node) {
        var parentThreats = node.threats;
        for (String threat : threats) {
            if (!parentThreats.contains(threat)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return ("Node(" +   threats + ", noThreats: " + noThreats + ", degree: " + getDegree() + ", DifferenceInOutDegree: " + DifferenceInOutDegree() + ")");
    }

    @Override
    public int compareTo(Object o) {
        Node node = (Node) o;
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

    public int getNumberOfThreats(){
        return noThreats;
    }

    public int getDegree(){
        return parents.size() + children.size();
    }
    
    public int DifferenceInOutDegree(){
        return Math.abs(parents.size() - children.size());
    }
}