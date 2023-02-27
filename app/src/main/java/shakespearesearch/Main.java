package shakespearesearch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String resourceName = "/shakespeare.txt";
        String searchTerm = "example phrase";
        int numThreads = 4;

        ShakespeareSearch search = new ShakespeareSearch(resourceName, searchTerm, numThreads);
        search.search();
    }
}