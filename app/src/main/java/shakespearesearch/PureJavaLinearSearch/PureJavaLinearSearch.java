package shakespearesearch.PureJavaLinearSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import shakespearesearch.*;

public class PureJavaLinearSearch extends Algo {
    private static final int CHUNK_SIZE = 1000;
    private String resourceName;
    private String searchTerm;
    private int numThreads;

    public PureJavaLinearSearch() {
        super("Pure Java Linear Search");
    }

    @Override
    public void run(String resourceName, String searchTerm, int numThreads) {
        this.resourceName = resourceName;
        this.searchTerm = searchTerm.toLowerCase();
        this.numThreads = numThreads;

        try {
            List<Chunk> chunks = readChunksFromResource(resourceName);
            List<Match> matches = searchChunks(chunks, searchTerm);

            int numMatches = matches.size();
            Evaluator timer = new Evaluator(getAlgoName(), numThreads);
            timer.start();
            timer.stop(numMatches);
            timer.evaluate();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private List<Chunk> readChunksFromResource(String resourceName) throws IOException {
        InputStreamReader isr = new InputStreamReader(PureJavaLinearSearch.class.getResourceAsStream(resourceName));
        try (BufferedReader reader = new BufferedReader(isr)) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            List<Chunk> chunks = new ArrayList<>();

            for (int i = 0; i < lines.size(); i += CHUNK_SIZE) {
                List<String> chunkLines = lines.subList(i, Math.min(i + CHUNK_SIZE, lines.size()));
                chunks.add(new Chunk(chunkLines, i));
            }
            return chunks;
        }
    }

    private List<Match> searchChunks(List<Chunk> chunks, String searchTerm) throws InterruptedException {
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
