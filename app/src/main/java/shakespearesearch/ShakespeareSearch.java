package shakespearesearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ShakespeareSearch {
    private static final int CHUNK_SIZE = 1000;
    private static final int NUM_THREADS = 4;

    public static void main(String[] args) throws InterruptedException {
        String resourceName = "/shakespeare.txt"; // eventually needs to be a PG database or dynamodb or s3?
        String searchTerm = "romeo";

        try {
            List<Chunk> chunks = readChunksFromResource(resourceName);

            List<Match> matches = searchChunks(chunks, searchTerm);

            for (Match match : matches) {
                System.out.println("Match found on line " + (match.getLineNumber() + 1) + ": " + match.getLine());
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static List<Chunk> readChunksFromResource(String resourceName) throws IOException {
        InputStreamReader isr = new InputStreamReader(ShakespeareSearch.class.getResourceAsStream(resourceName));
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

    private static List<Match> searchChunks(List<Chunk> chunks, String searchTerm) throws InterruptedException {
        List<Match> matches = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < chunks.size(); i++) {
            Chunk chunk = chunks.get(i);
            executor.submit(() -> searchChunk(chunk, searchTerm, matches)); 
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Hanging threads. " + e.getMessage()); // which thread?
        }

        return matches;
    }

    private static void searchChunk(Chunk chunk, String searchTerm, List<Match> matches) {
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
