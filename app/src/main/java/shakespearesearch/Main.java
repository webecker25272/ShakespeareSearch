package shakespearesearch;

import shakespearesearch.algo.Algo;
import shakespearesearch.algo.algos.PureJavaLinearSearch;
import shakespearesearch.utils.eval.Iterator;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String resourceName = "/shakespeare.txt";
        String searchTerm = " test ";

        Iterator iterator = new Iterator();
        Algo PJLS = new PureJavaLinearSearch();
        iterator.iterate(PJLS, resourceName, searchTerm, 10, true);
        System.out.println(iterator.getResults());
    }
}
