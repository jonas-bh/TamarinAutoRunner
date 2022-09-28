import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Node implements Comparable {
    String name;
    Set<String> threats = new HashSet<>();
    int size = 0; // Number of individual threats in this node.
    int level;

    HashSet<Node> parents = new HashSet<>();
    HashSet<Node> children = new HashSet<>();

    public Node(Set<String> threatCombination, int totalNumberOfThreats) {
        if (threatCombination.isEmpty()) {
            name = "HONEST";
        } else {
            name = generateNormalizedName(threatCombination);
        }
        level = totalNumberOfThreats - size;
    }

    public int getLevel() {
        return level;
    }

    public boolean isChildOf(Node node){
        var parentThreats = node.threats;
        for (String threat : threats) {
            if (!parentThreats.contains(threat)){
                return false;
            }
        }
        return true;
    }

    public String generateNormalizedName(Set<String> threatCombination) {
        var threatCount = 0;
        var output = "";
        var list = new ArrayList<>(threatCombination);
        Collections.sort(list);
        for (var item : threatCombination) {
            threats.add(item);
            output = output + "-" + item;
            threatCount++;
        }
        size = threatCount;
        return output.substring(1);
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
}