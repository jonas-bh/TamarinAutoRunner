import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import com.google.common.collect.Sets;

public class CombinationGraph {
    ArrayList<ArrayList<Node>> nodes = new ArrayList<>();
    int totalNumberOfThreats;

    public CombinationGraph(ArrayList<String> threats) {
        HashSet<String> set = new HashSet<>();
        for (var item : threats) {
            set.add(item);
        }
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
        }

        // Print all nodes
        for (var node : nodes) {
            System.out.println(node);
        }
        addEdges();
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
        System.out.println("fsad");
    }

}
