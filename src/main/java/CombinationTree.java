import java.util.ArrayList;
import java.util.HashSet;
import com.google.common.collect.Sets;

public class CombinationTree {
    public CombinationTree(ArrayList<String> threats) {
        HashSet<String> set = new HashSet<>();
        for (var item : threats) {
            set.add(item);
        }
        var powerSet = Sets.powerSet(set);
        ArrayList<Node> nodes = new ArrayList<>();
        for (var threatCombination : powerSet) {
            nodes.add(new Node(threatCombination));
        }
        for (var node : nodes) {
            System.out.println(node);
        }
    }
}
