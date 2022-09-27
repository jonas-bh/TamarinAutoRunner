import java.util.ArrayList;
import com.google.common.collect.Sets;

public class Main {

    public static void main(String[] args) {

        int nHumanThreats = 0;

        TamarinFileReader tfr = new TamarinFileReader();
        ArrayList<String> keywords = tfr
                .readFile("C:\\Users\\jbroc\\OneDrive\\Uni\\SD\\3. semester\\Research project\\TamarinAutoRunner\\exampleFiles\\Netto.spthy");
        nHumanThreats = keywords.size();
        new CombinationTree(keywords);
        int iterator = nHumanThreats;

    }
}
