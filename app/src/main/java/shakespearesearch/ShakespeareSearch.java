package shakespearesearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShakespeareSearch {
    private static final int CHUNK_SIZE = 1000;

    private final String fileName;
    private final String searchTerm;
    private final int numThreads;

    public ShakespeareSearch(String fileName, String searchTerm, int numThreads) {
        this.fileName = fileName;
        this.searchTerm = searchTerm;
        this.numThreads = numThreads;
    }

    public List<Match> search() throws IOException, InterruptedException {
        List<String> lines = readLinesFromFile(fileName);
        List<List<String>> chunks = splitIntoChunks(lines, CHUNK_SIZE);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < chunks.size(); i++) {
            List<String> chunk = chunks.get(i);
            final int offset = i * CHUNK_SIZE;
            executor.submit(() -> searchChunk(chunk, offset, matches));
        }
        
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        return matches;
    }

    private List<String> readLinesFromFile(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private List<List<String>> splitIntoChunks(List<String> lines, int chunkSize) {
        List<List<String>> chunks = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += chunkSize) {
            int toIndex = Math.min(i + chunkSize, lines.size());
            chunks.add(lines.subList(i, toIndex));
        }
        return chunks;
    }

    private void searchChunk(List<String> lines, int offset, List<Match> matches) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains(searchTerm)) {
                int lineNumber = offset + i + 1;
                matches.add(new Match(lineNumber, line));
            }
        }
    }

    public static class Match {
        private final int lineNumber;
        private final String lineText;

        public Match(int lineNumber, String lineText) {
            this.lineNumber = lineNumber;
            this.lineText = lineText;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public String getLineText() {
            return lineText;
        }
    }
}
