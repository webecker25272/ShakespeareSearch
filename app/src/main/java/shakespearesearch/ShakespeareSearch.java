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
    private static final int CHUNK_SIZE = 1000; // should this be set to an intelligent number?
    private static String resourceName; // should probably load some text into a PG database and try to optimize
    private static String searchTerm;
    private static int numThreads;

    public ShakespeareSearch(String resourceName, String searchTerm, int numThreads) {
        ShakespeareSearch.resourceName = resourceName;
        ShakespeareSearch.searchTerm = searchTerm.toLowerCase();
        ShakespeareSearch.numThreads = numThreads;

    }

    public int search() throws InterruptedException {
        try {
            List<Chunk> chunks = readChunksFromResource(resourceName);

            // this is the output of the whole thing... need to display this in table format
            // for the FE
            // where to inject the markdown?
            List<Match> matches = searchChunks(chunks, searchTerm);

            // for (Match match : matches) {
            //     want to catch the actual matched sequence so i can bold it in the fe
            //     System.out.println("Match found on line " + (match.getLineNumber() + 1) + ": " + match.getLine());
            // }
            return matches.size();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return 0; // return an actual message saying "no matches found"? or do you just implement that in the fe?
    }

    private static List<Chunk> readChunksFromResource(String resourceName) throws IOException {
        InputStreamReader isr = new InputStreamReader(ShakespeareSearch.class.getResourceAsStream(resourceName));
        try (BufferedReader reader = new BufferedReader(isr)) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            List<Chunk> chunks = new ArrayList<>();

            for (int i = 0; i < lines.size(); i += CHUNK_SIZE) {
                // int end = i + CHUNK_SIZE;
                // if (end > lines.size()) {
                // end = lines.size();
                // }
                // List<String> chunkLines = lines.subList(i, end);
                List<String> chunkLines = lines.subList(i, Math.min(i + CHUNK_SIZE, lines.size()));
                chunks.add(new Chunk(chunkLines, i));
            }
            return chunks;
        }
        // return null;
    }

    private static List<Match> searchChunks(List<Chunk> chunks, String searchTerm) throws InterruptedException {

        List<Match> matches = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < chunks.size(); i++) {
            Chunk chunk = chunks.get(i);
            // executor.submit(new SearchTask(chunk, searchTerm, matches));
            executor.submit(() -> searchChunk(chunk, searchTerm, matches));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Hanging threads.." + e.getMessage()); // which thread?
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
