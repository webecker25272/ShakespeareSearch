package shakespearesearch.algo;

import shakespearesearch.utils.search.ChunkUtils;
import shakespearesearch.utils.search.FileUtils;
import shakespearesearch.utils.threading.Chunk;
import shakespearesearch.utils.threading.Match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class Algo {
    private final String algoName;

    public Algo(String algoName) {
        this.algoName = algoName;
    }

    public String getAlgoName() {
        return algoName;
    }

    public abstract List<Match> searchChunk(Chunk chunk, String searchTerm);

    public int run(String resourceName, String searchTerm, int numThreads) {
        try {
            List<String> lines = FileUtils.readLinesFromResource(resourceName);
            List<Chunk> chunks = ChunkUtils.createChunks(lines);
            ExecutorService executor = Executors.newFixedThreadPool(numThreads); //executor should be its own class
    
            //orchestrate the searchChunks method
            List<Future<List<Match>>> futures = new ArrayList<>();
            for (Chunk chunk : chunks) {
                futures.add(executor.submit(() -> searchChunk(chunk, searchTerm)));
            }
    
            int numMatches = 0;
            for (Future<List<Match>> future : futures) {
                List<Match> matches = future.get();
                numMatches += matches.size();
            }
            
            executor.shutdown();
            return numMatches;
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }
}
