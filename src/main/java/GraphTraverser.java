import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphTraverser {

    private CombinationGraph graph;
    HashMap<Node, ArrayList<String>> results = new HashMap<>();

    public GraphTraverser(CombinationGraph graph) {
        this.graph = graph;
    }

    private boolean validateNode(Node node) {

        boolean result = runTamarinOnNode(node);

        if (result) {
            graph.markVerified(node);
            return true;
        } else {
            graph.markFalsified(node);
            return false;
        }
    }

    private String getDKeyword(Node node) {
        String keywords = "";

        for (String k : node.getThreats()) {
            keywords += " -D" + k;
        }
        return keywords;
    }

    public boolean runTamarinOnNode(Node node) {

        System.out.println("Running Tamarin on " + node.toString() + "...");

        Process process = null;

        try {

            // Necessary when running tamarin from a binary file
            var tamarinPath = "/Users/finn/Documents/Research_Project_Tamarin/tamarin-prover/1.6.1/bin/tamarin-prover";
            // var tamarinPath = "tamarin-prover";
            var spthyFile = "/Users/finn/Documents/Research_Project_Tamarin/TamarinAutoRunner/exampleFiles/Netto.spthy";
            var oracleFile = "./exampleFiles/oracle.py"; // Relative from the current working directory
            String dKeywords = getDKeyword(node);
            var command = tamarinPath + " " + spthyFile + " --heuristic=o --oraclename=" + oracleFile
                    + " --stop-on-trace=SEQDFS --prove " + dKeywords;

            System.out.println(command);

            process = Runtime.getRuntime().exec(command);

            process.getErrorStream().close(); // Close ErrorStream because it causes the subprocess to hang during
                                              // emptying.

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            boolean includeLine = false;
            String line = "";
            results.put(node, new ArrayList<>());

            while ((line = br.readLine()) != null) {

                if (includeLine && line.startsWith("======")) {
                    includeLine = false;
                }

                if (includeLine) {
                    results.get(node).add(line);
                }

                if (line.startsWith("======")) {
                    includeLine = true;
                }

                System.out.println(line);
            }

            int exitVal = process.waitFor();
            System.out.println("Exit value: " + exitVal);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Tamarin finished.");

        return interpretResult(results.get(node));
    }

    private boolean interpretResult(ArrayList<String> result) {
        for (String s : result) {
            if (s.contains("falsified"))
                return false;
        }
        return true;
    }

    public void execute() {
        while (graph.GetNumberOfNodes() > 0) {
            validateNode((graph.getNextNode()));
            // System.out.println(graph);
        }
        System.out.println("Program finished.");
        writeResultsToFile();
    }

    private void writeResultsToFile() {

        try {
            FileWriter fw = new FileWriter("results.txt");

            for (Node node : results.keySet()) {
                for (String s : results.get(node)) {
                    fw.write(s);
                    fw.write("\n");
                }
            }

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
