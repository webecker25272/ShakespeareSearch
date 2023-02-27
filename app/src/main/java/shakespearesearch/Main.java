package shakespearesearch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String resourceName = "/shakespeare.txt";
        String searchTerm = "a";
        int maxThreads = 200;

        for (int numThreads = 1; numThreads <= maxThreads; numThreads++) {
            Search search = new Search(resourceName, searchTerm, numThreads);
            Evaluator timer = new Evaluator("Java", numThreads);
            timer.start();
            int matches = search.search();
            timer.stop(matches);
            timer.evaluate();
        }
    }
}


//test cases: no matches, a billion matches, null search term, illegal search term