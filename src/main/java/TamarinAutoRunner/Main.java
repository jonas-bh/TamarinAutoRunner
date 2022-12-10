package TamarinAutoRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    public static final HashMap<String, HashSet<Node>> lemmas = new HashMap<>();

    public static void main(String[] args) {

        // String protocol = args[0];
        // String oracleFile = "";
        // String tamarinBin = "";
        String protocol = "/Users/finn/Documents/Research_Project_Tamarin/TamarinAutoRunner/exampleFiles/Netto.spthy";
        String oracleFile = "/Users/finn/Documents/Research_Project_Tamarin/TamarinAutoRunner/exampleFiles/oracle.py";
        String tamarinBin = "/Users/finn/Documents/Research_Project_Tamarin/tamarin-prover/1.6.1/bin/tamarin-prover";

        // java -jar ./build/libs/TamarinAutoRunner-1.0-SNAPSHOT.jar
        // ./exampleFiles/Netto.spthy -oracle=./exampleFiles/oracle.py
        // -tamarin=/Users/finn/Documents/Research_Project_Tamarin/tamarin-prover/1.6.1/bin/tamarin-prover
        // for (int i = 1; i < args.length; i++) {
        // if (args[i].startsWith("-oracle")) {
        // oracleFile = args[i].split("=")[1];
        // }
        // if (args[i].startsWith("-tamarin")) {
        // tamarinBin = args[i].split("=")[1];
        // }
        // }

        TamarinFileReader tfr = new TamarinFileReader();
        ArrayList<String> keywords = tfr
                .readFile(protocol);

        // for (int i = 0; i < 15; i++) {
        // keywords.add("Odd" + i) ;
        // }

        Logger.initiateTraversalLog();

        for (String lemma : lemmas.keySet()) {
            Logger.writeTraversalLogHeader(lemma);
            CombinationGraph graph = new CombinationGraph(keywords);
            GraphTraverser traverser = new GraphTraverser(graph, protocol, oracleFile, tamarinBin,
                    lemma);
            traverser.execute();
        }

        Logger.writeResultsLogFile();

    }

}
