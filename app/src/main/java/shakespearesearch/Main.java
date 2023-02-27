package shakespearesearch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String resourceName = "/shakespeare.txt";
        String searchTerm = "a";

        //looks like 20 threads is the winner so far...
        for (int numThreads = 1; numThreads <= 200; numThreads++) {
            Search search = new Search(resourceName, searchTerm, numThreads);
            long startTime = System.nanoTime();
            int matches = search.search();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            System.out.println("Thread count: " + numThreads + ".  Time taken: " + duration + " ms.  Avg: " + duration/matches);
        }
        

        //test cases: no matches, a billion matches, null search term, illegal search term
    }
}
