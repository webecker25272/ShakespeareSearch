package shakespearesearch;

import shakespearesearch.algo.Algo;
import shakespearesearch.algo.algos.PureJavaLinearSearch;
import shakespearesearch.utils.eval.Iterator;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String resourceName = "/shakespeare.txt";
        String searchTerm = " rascal ";

        Algo PJLS = new PureJavaLinearSearch();
        Iterator.iterate(PJLS, resourceName, searchTerm);
    }
}
