package shakespearesearch.algo.algos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import shakespearesearch.algo.Algo;
import shakespearesearch.utils.eval.Evaluator;
import shakespearesearch.utils.search.ChunkUtils;
import shakespearesearch.utils.threading.Chunk;
import shakespearesearch.utils.threading.Match;

public class PureJavaLinearSearch extends Algo {

    public PureJavaLinearSearch() {
        super("Pure Java Linear Search");
    }

    @Override
    public void run(String resourceName, String searchTerm, int numThreads) {
        this.resourceName = resourceName;
        this.searchTerm = searchTerm.toLowerCase();
        this.numThreads = numThreads;
    
        Evaluator evaluator = new Evaluator(getAlgoName(), numThreads);
        evaluator.start();
    
        try {
            List<Chunk> chunks = ChunkUtils.readChunksFromResource(resourceName);
            List<Match> matches = searchChunks(chunks, searchTerm);
            int numMatches = matches.size();
            evaluator.stop(numMatches);
            evaluator.evaluate();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    @Override
    public List<Match> searchChunks(List<Chunk> chunks, String searchTerm) throws InterruptedException {
        List<Match> matches = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < chunks.size(); i++) {
            Chunk chunk = chunks.get(i);
            executor.submit(() -> searchChunk(chunk, searchTerm, matches));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Hanging threads.." + e.getMessage());
        }
        return matches;
    }

    private void searchChunk(Chunk chunk, String searchTerm, List<Match> matches) {
        int startIndex = chunk.getStartIndex();
        for (int i = 0; i < chunk.getLines().size(); i++) {
            String line = chunk.getLines().get(i);
            if (line.contains(searchTerm)) {
                int lineNumber = startIndex + i;
                matches.add(new Match(lineNumber, line));
            }
        }
    }
}
