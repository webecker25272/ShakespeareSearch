package shakespearesearch.utils.threading;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shakespearesearch.algo.Algo;

public class Executor {
    private static ExecutorService createExecutor(Algo algo, List<Chunk> chunks, String searchTerm, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    
        for (int i = 0; i < chunks.size(); i++) {
            Chunk chunk = chunks.get(i);
            executor.submit(() -> algo.searchChunk(chunk, searchTerm));
        }
    
        return executor;
    }
    
}
