package shakespearesearch;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String resourceName = "/shakespeare.txt";
        String searchTerm = "i";
        int numThreads = 4;

        long startTime = System.currentTimeMillis();

        ShakespeareSearch search = new ShakespeareSearch(resourceName, searchTerm, numThreads);
        List<Match> matches = search.search();

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Search took ");
        System.out.println(matches.size() + " matches found in " + elapsedTime + " ms ("
                + (elapsedTime / matches.size()) + " ms/match).");
    }
}
