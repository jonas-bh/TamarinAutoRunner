import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Node {
    String name;
    Set<String> threats = new HashSet<>();
    int noOfThreats = 0;
    ArrayList<Node> parents;
    ArrayList<Node> children;

    public Node(Set<String> threatCombination) {
        if (threatCombination.isEmpty()) {
            name = "HONEST";
        }
        else {
            name = generateNormalizedName(threatCombination);
        }
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
        noOfThreats = threatCount;
        return output.substring(1);
    }

    @Override
    public String toString() {
        return ("Node(" + threats + ", " + noOfThreats + ")");
    }
}
