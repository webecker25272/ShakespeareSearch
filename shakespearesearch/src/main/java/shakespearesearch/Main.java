package shakespearesearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import shakespearesearch.controller.resourceserver.ResourceController;
import shakespearesearch.controller.resourceserver.ResourceServer;
import shakespearesearch.model.algo.Algo;
import shakespearesearch.model.algo.algos.PureJavaLinearSearch;
import shakespearesearch.model.eval.AlgoResult;
import shakespearesearch.model.eval.Iterator;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@ComponentScan({"shakespearesearch", "backendserver"})
public class Main {
    private static final boolean PRINTLINE = true;
    private static final int MAXTHREADS = 10;
    private static final String SEARCHTERM = " test ";

    public static void main(String[] args) throws InterruptedException, IOException {     
        String responseData = ResourceController.readLinesFromResource();

        Iterator iterator = new Iterator();

        Algo PJLS = new PureJavaLinearSearch();
        List<AlgoResult> pjlsResult = iterator.iterate(PJLS, responseData, SEARCHTERM, MAXTHREADS, PRINTLINE); //this is what gets displayed to the front end
        
        //new algos go here
    



        SpringApplication.run(Main.class, args);
    }
}
