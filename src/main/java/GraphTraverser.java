import java.util.Scanner;
import java.util.Set;

public class GraphTraverser {

    private CombinationGraph graph;
    private Node startingNode;
    private Node currentNode;

    public GraphTraverser(CombinationGraph graph) {
        this.graph = graph;
        startingNode = graph.getNode(graph.noOfLevels() / 2, 0);
    }

    private boolean validateNode(Node node) {
        // TODO: Run Tamarin for selected threat combination
        // for now, ask user to select successful or failed validation

        Scanner sc = new Scanner(System.in);

        System.out.println("Is " + node.toString() + " successful? \n y/n");

        var result = sc.nextLine();
        if (result.equals("y")) {
            graph.markVerified(node);
            return true;
        }
        if (result.equals("n")) {

            Set<Node> parents = node.getParents();

            graph.markFalsified(node);
            return false;
        }
        return true;
    }

    public void execute() {
        while(graph.GetNumberOfNodes()>0) {
            validateNode(graph.getNextNode());   
            System.out.println(graph);  
        }
        System.out.println("Program finished.");
    }

    // TODO Implement method to find node with max in-minus-out-degree that has not been validated.
    // TODO Change graph traversal to go through all the nodes with max in-minus-out-degree first (left to right, like in the paper).
    // 

}
