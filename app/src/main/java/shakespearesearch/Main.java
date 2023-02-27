package shakespearesearch;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String resourceName = "/shakespeare.txt";
        String searchTerm = " courtier ";
        int numThreads = 1;

        long startTime = System.currentTimeMillis();

        ShakespeareSearch search = new ShakespeareSearch(resourceName, searchTerm, numThreads);
        List<Match> matches = search.search();

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Search took ");
        System.out.println(matches.size() + " matches found in " + elapsedTime + " ms ("
                + (elapsedTime / matches.size()) + " ms/match).");
        //getting errors when having huge number of matches
        //test cases: no matches, a billion matches, null search term, illegal search term
    }
}
