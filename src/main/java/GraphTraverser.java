import java.util.Scanner;

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
        node.setWasValidated();
        if (result.equals("y")) {
            node.setValidationResult(true);
            return true;
        }
        if (result.equals("n")) {
            node.setValidationResult(false);
            return false;
        }
        return true;
    }

    public void execute() {
        currentNode = startingNode;
        while (!graph.fullyValidated()) {
            boolean testResult = validateNode(currentNode);
            currentNode.setWasValidated();
            currentNode.setValidationResult(testResult);
            if (testResult) {
                // go up
                childrenSetValidated(currentNode, true);
                var parents = currentNode.getParents();
                var iterator = parents.iterator();
                if (currentNode.getSize() == graph.getTotalNumberOfThreats()){ // if top node
                    childrenSetValidated(currentNode, true);
                    break;
                }
                if (!parents.isEmpty()) {
                    boolean nextNodeFound = false;
                    while (!nextNodeFound) {
                        currentNode = iterator.next();
                        if (!currentNode.getWasValidated()) {
                            nextNodeFound = true;
                        }
                    }
                }
            } else {
                // go down
                parentsSetValidated(currentNode, false);
                var children = currentNode.getChildren();
                var iterator = children.iterator();
                if (currentNode.getSize() == 0){ // if HONEST node
                    parentsSetValidated(currentNode, false);
                    break;
                }
                if (!children.isEmpty()) {
                    boolean nextNodeFound = false;
                    while (!nextNodeFound) {
                        currentNode = iterator.next();
                        if (!currentNode.getWasValidated()) {
                            nextNodeFound = true;
                        }
                    }
                }
            }
        }
        System.out.println("Program finished.");
    }


    // TODO Implement method to find node with max in-minus-out-degree that has not been validated.
    // TODO Change graph traversal to go through all the nodes with max in-minus-out-degree first (left to right, like in the paper).
    // 

    public void childrenSetValidated(Node node, boolean result) {
        for (var child : node.getChildren()) {
            if (child.getWasValidated() == false) {
                child.setValidationResult(result);
                childrenSetValidated(child, result);
            }
        }
    }

    public void parentsSetValidated(Node node, boolean result) {
        for (var parent : node.getParents()) {
            if (parent.getWasValidated() == false) {
                parent.setValidationResult(result);
                parentsSetValidated(parent, result);
            }
        }
    }
}
