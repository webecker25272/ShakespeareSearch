package shakespearesearch.algo.algos;

import shakespearesearch.algo.Algo;
import shakespearesearch.utils.eval.Evaluator;
import shakespearesearch.utils.search.ChunkUtils;
import shakespearesearch.utils.threading.Chunk;
import shakespearesearch.utils.threading.Match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PureJavaLinearSearch extends Algo {

    public PureJavaLinearSearch() {
        super("Pure Java Linear Search");
    }

    @Override
    public List<Match> searchChunks(List<Chunk> chunks, String searchTerm) {
        List<Match> matches = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(chunks.size());

        for (Chunk chunk : chunks) {
            executor.submit(() -> searchChunk(chunk, searchTerm, matches));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Hanging threads: " + e.getMessage());
        }

        return matches;
    }

    @Override
    public List<Match> searchChunk(List<Chunk> chunks, String searchTerm) {
        List<Match> matches = new ArrayList<>();
        for (Chunk chunk : chunks) {
            searchChunk(chunk, searchTerm, matches);
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

    @Override
    public int run(String resourceName, String searchTerm, int numThreads) {
        List<Chunk> chunks;
        try {
            chunks = ChunkUtils.readChunksFromResource(resourceName);
        } catch (IOException e) {
            System.err.println("Error reading chunks from resource: " + e.getMessage());
            return -1;
        }

        List<Match> matches = searchChunks(chunks, searchTerm);
        int numMatches = matches.size();

        Evaluator evaluator = new Evaluator(getAlgoName(), numThreads);
        evaluator.start();
        evaluator.stop(numMatches);
        evaluator.evaluate();

        return numMatches;
    }

}
