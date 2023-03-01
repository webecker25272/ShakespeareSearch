package shakespearesearch.utils.eval;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shakespearesearch.algo.Algo;

public class Iterator {
    private static final int DEFAULT_MIN_THREADS = 1;
    private static final int DEFAULT_STEP_SIZE = 1;

    public List<AlgoResult> iterate(Algo algo, String resource, String searchTerm, int maxThreads, boolean printLine) throws InterruptedException {
        List<AlgoResult> results = new ArrayList<>();
        RunTimer runTimer = new RunTimer(algo.getAlgoName());
    
        for (int numThreads = DEFAULT_MIN_THREADS; numThreads <= maxThreads; numThreads += DEFAULT_STEP_SIZE) {
            runTimer.start();
            int numMatches = algo.run(resource, searchTerm, numThreads);
            Map<String, Object> runResult = runTimer.stop(numMatches, numThreads, printLine);
            Object[] runData = new Object[4];
            runData[0] = numThreads; // x axis
            runData[1] = runResult.get("totalTime"); // y axis
            runData[2] = runResult.get("avgTime"); // y axis
            runData[3] = runResult.get("usedMemory"); // y axis
            results.add(new AlgoResult(algo.getAlgoName(), runData));
        }
    
        return results;
    }
    
}
