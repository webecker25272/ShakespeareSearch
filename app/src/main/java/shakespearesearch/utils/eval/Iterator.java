package shakespearesearch.utils.eval;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shakespearesearch.algo.Algo;

public class Iterator {
    private static final int DEFAULT_MIN_THREADS = 1;
    private static final int DEFAULT_STEP_SIZE = 1;

    public Object[] iterate(Algo algo, String resource, String searchTerm, int maxThreads, boolean printLine) throws InterruptedException {
        List<Map<String, Object>> runs = new ArrayList<>();
        RunTimer runTimer = new RunTimer(algo.getAlgoName());
    
        for (int numThreads = DEFAULT_MIN_THREADS; numThreads <= maxThreads; numThreads += DEFAULT_STEP_SIZE) {
            runTimer.start();
            int numMatches = algo.run(resource, searchTerm, numThreads);
            Map<String, Object> runResult = runTimer.stop(numMatches, numThreads, printLine);
            runs.add(runResult);
        }
    
        Object[] data = new Object[runs.size()];
        for (int i = 0; i < runs.size(); i++) {
            Map<String, Object> run = runs.get(i);
            Object[] runData = new Object[4];
            runData[0] = i + 1; // x axis
            runData[1] = run.get("totalTime"); // y axis
            runData[2] = run.get("avgTime"); // y axis
            runData[3] = run.get("usedMemory"); // y axis
            data[i] = runData;
        }
        return data;
    }
}
