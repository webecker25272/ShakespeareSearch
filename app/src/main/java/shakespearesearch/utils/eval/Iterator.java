package shakespearesearch.utils.eval;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shakespearesearch.algo.Algo;

public class Iterator {
    private final int DEFAULT_MIN_THREADS = 1;
    private final int DEFAULT_STEP_SIZE = 1;
    private List<Map<String, Object>> results = new ArrayList<>();

    public void iterate(Algo algo, String resourceName, String searchTerm, int maxThreads, boolean printLine)
            throws InterruptedException {
        RunTimer runTimer = new RunTimer(algo.getAlgoName());

        for (int numThreads = DEFAULT_MIN_THREADS; numThreads <= maxThreads; numThreads += DEFAULT_STEP_SIZE) {
            runTimer.start();
            int numMatches = algo.run(resourceName, searchTerm, numThreads);
            Map<String, Object> runResult = runTimer.stop(numMatches, numThreads, printLine);
            results.add(runResult);
        }
    }

    public  List<Map<String, Object>> getResults() {
        return results;
    }
}
