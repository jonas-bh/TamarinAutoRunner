package TamarinAutoRunner;

import java.util.ArrayList;
import com.google.common.collect.Sets;


public class Main {

    public static void main(String[] args) {
        String protocol = args[0];
        String oracleFile = "";
        String tamarinBin = "";

        // java -jar ./build/libs/TamarinAutoRunner-1.0-SNAPSHOT.jar ./exampleFiles/Netto.spthy -oracle=./exampleFiles/oracle.py -tamarin=/Users/finn/Documents/Research_Project_Tamarin/tamarin-prover/1.6.1/bin/tamarin-prover
        for (int i = 1; i < args.length; i++) {
            if (args[i].startsWith("-oracle")) {
                oracleFile = args[i].split("=")[1];
            }
            if (args[i].startsWith("-tamarin")) {
                tamarinBin = args[i].split("=")[1];
            }
        }

        int nHumanThreats = 0;

        TamarinFileReader tfr = new TamarinFileReader();
        ArrayList<String> keywords = tfr
                .readFile(protocol);
        nHumanThreats = keywords.size();

        // for (int i = 0; i < 15; i++) {
        // keywords.add("Odd" + i) ;

        // }

        CombinationGraph graph = new CombinationGraph(keywords);
        int iterator = nHumanThreats;

        GraphTraverser traverser = new GraphTraverser(graph, protocol, oracleFile, tamarinBin);
        traverser.execute();

    }
}
