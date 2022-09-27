import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        int nHumanThreats = 0;

        TamarinFileReader tfr = new TamarinFileReader();
        ArrayList<String> keywords = tfr
                .readFile("/Users/finn/Documents/Research_Project_Tamarin/TamarinAutoRunner/exampleFiles/Netto.spthy");
        for (String word : keywords)
            System.out.println(word);

        nHumanThreats = keywords.size();

        int iterator = nHumanThreats;

    }
}
