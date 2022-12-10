package TamarinAutoRunner;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import com.google.common.base.Stopwatch;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.commons.cli.*;

public class Main {
    public static final HashMap<String, HashSet<Node>> lemmas = new HashMap<>();

    public static void main(String[] args) throws ParseException {

        var totalTimer = new Stopwatch().start();
        Options options = new Options();
        Option protocolFileArg = Option.builder("pf")
                              .longOpt("protocolFile")
                              .argName("protocolFile")
                              .desc("The Tamarin Prover protocol file to be analyzed")
                              .hasArg()
                              .required(true)
                              .build();
        options.addOption(protocolFileArg);
        Option oracleFileArg = Option.builder("of")
                            .longOpt("oracleFile")
                            .argName("oracleFile")
                            .desc("An oracle file to be used for Tamarin Prover invocations")
                            .hasArg()
                            .required(false)
                            .build();
        options.addOption(oracleFileArg);
        Option tamarinBinArg = Option.builder("tam")
                            .longOpt("tamarinBin")
                            .argName("tamarinBin")
                            .hasArg()
                            .desc("The path to Tamarin Prover bin, if not installed as environment variable")
                            .required(false)
                            .build();
        options.addOption(tamarinBinArg);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        
        String protocol = cmd.getOptionValue(protocolFileArg);
        
        String oracleFile = cmd.getOptionValue(oracleFileArg);

        String tamarinBin = cmd.getOptionValue(tamarinBinArg);
        // String protocol =
        // "/Users/finn/Documents/Research_Project_Tamarin/TamarinAutoRunner/exampleFiles/Netto.spthy";
        // String oracleFile =
        // "-oracle=/Users/finn/Documents/Research_Project_Tamarin/TamarinAutoRunner/exampleFiles/oracle.py";
        // String tamarinBin =
        // "-tamarin=/Users/finn/Documents/Research_Project_Tamarin/tamarin-prover/1.6.1/bin/tamarin-prover";

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
        Stopwatch tamarinTimer = new Stopwatch().start();
        for (String lemma : lemmas.keySet()) {
            Logger.writeTraversalLogHeader(lemma);
            CombinationGraph graph = new CombinationGraph(keywords);
            GraphTraverser traverser = new GraphTraverser(graph, protocol, oracleFile, tamarinBin,
                    lemma);
            traverser.execute();
        }
        tamarinTimer.stop();
      totalTimer.stop();
      var tamarinTime = tamarinTimer.elapsedTime(TimeUnit.MILLISECONDS);
      var totalTime = totalTimer.elapsedTime(TimeUnit.MILLISECONDS);
      System.out.println("Total elapsed time: " + totalTime + " ms");
      System.out.println("Tamarin % of total time: " + tamarinTime / totalTime * 100 + "%");

        Logger.writeResultsLogFile();
    }

}
