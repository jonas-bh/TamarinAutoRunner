import java.util.ArrayList;
import com.google.common.collect.Sets;

public class Main {

    public static void main(String[] args) {

        int nHumanThreats = 0;

        TamarinFileReader tfr = new TamarinFileReader();
        ArrayList<String> keywords = tfr
                .readFile(
                        "/Users/finn/Documents/Research_Project_Tamarin/TamarinAutoRunner/exampleFiles/Netto.spthy");
        nHumanThreats = keywords.size();

        // for (int i = 0; i < 15; i++) {
        //     keywords.add("Odd" + i) ;

        // }

        CombinationGraph graph = new  CombinationGraph(keywords);
        int iterator = nHumanThreats;

        GraphTraverser traverser = new GraphTraverser(graph);
        traverser.execute();

    }
}
