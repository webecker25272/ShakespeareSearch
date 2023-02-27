package shakespearesearch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String resourceName = "/shakespeare.txt";
        String searchTerm = " hermit ";

        
        for (int i = 1; i <= 20; i++) {
            ShakespeareSearch search = new ShakespeareSearch(resourceName, searchTerm, i);
            long startTime = System.nanoTime();
            int matches = search.search();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            System.out.println("Thread count: " + i + ".  Time taken: " + duration + " ms.  Avg: " + duration/matches);
        }
        

        //getting errors when having huge number of matches
        //test cases: no matches, a billion matches, null search term, illegal search term
    }
}
