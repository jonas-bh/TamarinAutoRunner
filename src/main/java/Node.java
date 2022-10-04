import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Node implements Comparable {
    private Set<String> threats = new HashSet<>();
    private int size = 0; // Number of individual threats in this node.
    private int level;
    private HashSet<Node> parents = new HashSet<>();
    private HashSet<Node> children = new HashSet<>();
    private boolean wasValidated = false;
    private boolean validationResult = false;

    public Node(Set<String> threatCombination, int totalNumberOfThreats) {
        size = threatCombination.size();
        level = totalNumberOfThreats - size;
        for (String threat : threatCombination) {
            threats.add(threat);
        }
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
        return ("Node(" + threats + ", " + size + ")");
    }

    @Override
    public int compareTo(Object o) {
        Node node = (Node) o;
        if (size < node.size) {
            return -1;
        } else if (size == node.size) {
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

    public void setWasValidated() {
        wasValidated = true;
    }

    public boolean getWasValidated() {
        return wasValidated;
    }

    public void setValidationResult(boolean result) {
        validationResult = result;
    }

    public boolean getValidationResult() {
        return validationResult;
    }

    public HashSet<Node> getParents() {
        return parents;
    }

    public HashSet<Node> getChildren() {
        return children;
    }

    public int getSize(){
        return size;
    }
}