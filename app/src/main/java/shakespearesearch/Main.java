package shakespearesearch;

import shakespearesearch.algo.Algo;
import shakespearesearch.algo.algos.PureJavaLinearSearch;
import shakespearesearch.utils.eval.AlgoResult;
import shakespearesearch.utils.eval.Iterator;

import java.util.List;

public class Main {
    private static final boolean PRINTLINE = true;
    private static final int MAXTHREADS = 10;
    private static final String RESOURCENAME = "/shakespeare.txt";
    private static final String SEARCHTERM = " test ";

    public static void main(String[] args) throws InterruptedException {        
        Iterator iterator = new Iterator();

        Algo PJLS = new PureJavaLinearSearch();
        List<AlgoResult> pjlsResult = iterator.iterate(PJLS, RESOURCENAME, SEARCHTERM, MAXTHREADS, PRINTLINE); //this is what gets displayed to the front end
        

    
    }
}
