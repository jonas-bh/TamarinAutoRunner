package TamarinAutoRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphTraverser {

    private CombinationGraph graph;
    HashMap<Node, ArrayList<String>> results = new HashMap<>();

    String protocol;
    String oracleFile;
    String tamarinBin;

    public GraphTraverser(CombinationGraph graph, String protocol, String oracleFile, String tamarinBin) {
        this.graph = graph;
        this.protocol=protocol;
        this.oracleFile=oracleFile;
        this.tamarinBin=tamarinBin;
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

        System.out.println();
        System.out.println("Running Tamarin on " + node.toString() + "...");

        Process process = null;

        try {

            // Necessary when running tamarin from a binary file
            // var tamarinPath = "/Users/finn/Documents/Research_Project_Tamarin/tamarin-prover/1.6.1/bin/tamarin-prover";
            // // var tamarinPath = "tamarin-prover";
            // var spthyFile = "/Users/finn/Documents/Research_Project_Tamarin/TamarinAutoRunner/exampleFiles/Netto.spthy";
            // var oracleFile = "./exampleFiles/oracle.py"; // Relative from the current working directory
            String command = buildCommand(node);
            System.out.println("Trying to run command: " + command);
            File currentDir = new File(System.getProperty("user.dir"));
            System.out.println(currentDir);
            process = Runtime.getRuntime().exec(command, null, currentDir);

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

            System.out.println("Finished running Tamarin on Node: " + node.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Tamarin finished.");

        return interpretResult(results.get(node));
    }

    private String buildCommand(Node node) {
        String dKeywords = getDKeyword(node);
        String command = "";

        if (!tamarinBin.isEmpty()){
            command += tamarinBin + " ";
        }
        else {
            command += "tamarin-prover ";
        }
        
        command += protocol + " ";

        if (!oracleFile.isEmpty()) {
            command += "--heuristic=o --oraclename=" + oracleFile + " ";
        }
        
        
        command += "--stop-on-trace=SEQDFS "; // add a true (sequential) depth-first search (DFS) option
        command += "--prove ";
        command += dKeywords;
        return command;
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
                fw.write(node.toString());
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
