import java.util.ArrayList;
import java.util.HashSet;
import com.google.common.collect.Sets;

public class CombinationGraph {
    private ArrayList<ArrayList<Node>> nodes = new ArrayList<>();
    private int totalNumberOfThreats;
    private int noOfLevels = 0;
    private int noOfValidatedNodes = 0;
    private int size = 0;

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
            size++;
        }

        // Print all nodes
        for (var node : nodes) {
            System.out.println(node);
        }
        addEdges();
        noOfLevels = nodes.size();
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

    public int noOfLevels(){
        return noOfLevels;
    }

    public Node getNode(int level, int index){
        return nodes.get(level).get(index);
    }

    public void incrementNoOfValidatedNodes () {
        noOfValidatedNodes++;
    }

    public boolean fullyValidated () {
        return noOfValidatedNodes == size;  
    }

    public int getTotalNumberOfThreats(){
        return totalNumberOfThreats;
    }
}
