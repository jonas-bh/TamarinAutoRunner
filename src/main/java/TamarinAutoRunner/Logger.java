package TamarinAutoRunner;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    public static void initiateTraversalLog() {
        try {
            FileWriter fw = new FileWriter("traversalLog.txt");
            fw.write("");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTraversalLogHeader(String lemma) {
        try {
            FileWriter fw = new FileWriter("traversalLog.txt", true);
            fw.write(lemma + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTraversalLog(Node node, boolean validated) {
        try {
            FileWriter fw = new FileWriter("traversalLog.txt", true);
            fw.write((validated ? "verified " : "falsified") + ": " + node.getThreats() + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeResultsLogFile() {
        try {
            FileWriter fw = new FileWriter("results.txt");
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
