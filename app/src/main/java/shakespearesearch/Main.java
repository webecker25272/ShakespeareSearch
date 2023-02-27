package shakespearesearch;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = "/shakespeare.txt";
        String searchTerm = "i";
        int numThreads = 4;

        ShakespeareSearch search = new ShakespeareSearch(fileName, searchTerm, numThreads);
        try {
            List<ShakespeareSearch.Match> matches = search.search();
            for (ShakespeareSearch.Match match : matches) {
                System.out.println("Match found on line " + match.getLineNumber() + ": " + match.getLineText());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
