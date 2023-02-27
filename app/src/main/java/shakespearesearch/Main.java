package shakespearesearch;

import shakespearesearch.PureJavaLinearSearch.PureJavaLinearSearch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String resourceName = "/shakespeare.txt";
        String searchTerm = "a";

        Algo PJLS = new PureJavaLinearSearch();
        Iterator.iterate(PJLS, resourceName, searchTerm);
    }
}
