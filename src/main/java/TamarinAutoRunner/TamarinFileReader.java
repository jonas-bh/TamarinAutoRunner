package TamarinAutoRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class TamarinFileReader {
    public ArrayList<String> readFile(String path) {
        ArrayList<String> keywords = new ArrayList<String>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = "";
        try {
            while ((line = br.readLine()) != null) {

                if (line.startsWith("#ifdef")) {

                    String keyword = line.split(" ")[1];
                    keywords.add(keyword);

                }

                if (line.startsWith("lemma") && line.contains("attack")) {

                    if (line.contains("[")) {
                        line = line.substring(line.indexOf(" ") + 1, line.indexOf("["));
                    } else {
                        line = line.substring(line.indexOf(" ") + 1, line.indexOf(":"));
                    }
                    Main.lemmas.put(line, new HashSet<>());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keywords;

    }

}
