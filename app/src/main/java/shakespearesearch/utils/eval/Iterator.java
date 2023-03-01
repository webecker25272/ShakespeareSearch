package shakespearesearch.utils.eval;

import java.util.Map;

import shakespearesearch.algo.Algo;

public class Iterator {
    private static final int DEFAULT_MIN_THREADS = 1;
    private static final int DEFAULT_STEP_SIZE = 1;

    public static void iterate(Algo algo, String resourceName, String searchTerm, int maxThreads, boolean printLine)
            throws InterruptedException {
        RunTimer runTimer = new RunTimer(algo.getAlgoName());

        for (int numThreads = DEFAULT_MIN_THREADS; numThreads <= maxThreads; numThreads += DEFAULT_STEP_SIZE) {
            runTimer.start();
            int numMatches = algo.run(resourceName, searchTerm, numThreads);
            Map<String, Object> runResult = runTimer.stop(numMatches, numThreads, true);
        }
    }
}
