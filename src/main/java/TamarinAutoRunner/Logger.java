package TamarinAutoRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Logger {
    public static void initiateTraversalLog() {
        try {
            FileWriter fw = new FileWriter("./resultFiles/traversalLog.txt");
            fw.write("");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTraversalLogHeader(String lemma) {
        try {
            FileWriter fw = new FileWriter("./resultFiles/traversalLog.txt", true);
            fw.write(lemma + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTraversalLog(Node node, boolean validated) {
        try {
            FileWriter fw = new FileWriter("./resultFiles/traversalLog.txt", true);
            fw.write((validated ? "verified " : "falsified") + ": " + node.getThreats() + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeResultsToFile(HashMap<Node, ArrayList<String>> results, String lemma) {
        try {
            FileWriter fw = new FileWriter("./resultFiles/" + lemma + "_resultsDEBUGGING.txt");
            for (Node node : results.keySet()) {
                fw.write("\n");
                fw.write(node.toString() + "\n");
                fw.write("\n");
                for (String s : results.get(node)) {
                    fw.write(s);
                    fw.write("\n");
                    fw.flush();
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeResultsLogFile() {
        try {
            FileWriter fw = new FileWriter("./resultFiles/results.txt");
            fw.write("Maximal Threat Combinations\n");
            fw.write("---------------------\n");
            for (String s : Main.lemmas.keySet()) {
                fw.write(s + ":\n");
                for (Node node : Main.lemmas.get(s)) {
                    fw.write(node + "\n");
                }
                fw.write("\n");
                fw.flush();
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
