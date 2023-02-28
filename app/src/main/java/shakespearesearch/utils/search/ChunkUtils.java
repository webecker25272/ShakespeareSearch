package shakespearesearch.utils.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import shakespearesearch.algo.Algo;
import shakespearesearch.utils.threading.Chunk;

public class ChunkUtils {
    private static final int DEFAULT_CHUNK_SIZE = 1000;

    public static List<Chunk> createChunks(List<String> lines) {
        return createChunks(lines, DEFAULT_CHUNK_SIZE);
    }

    public static List<Chunk> createChunks(List<String> lines, int chunkSize) {
        List<Chunk> chunks = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += chunkSize) {
            List<String> chunkLines = lines.subList(i, Math.min(i + chunkSize, lines.size()));
            chunks.add(new Chunk(chunkLines, i));
        }
        return chunks;
    }

    public static List<Chunk> readChunksFromResource(String resourceName) throws IOException {
        InputStreamReader isr = new InputStreamReader(Algo.class.getResourceAsStream(resourceName));
        try (BufferedReader reader = new BufferedReader(isr)) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            List<Chunk> chunks = new ArrayList<>();

            for (int i = 0; i < lines.size(); i += DEFAULT_CHUNK_SIZE) {
                List<String> chunkLines = lines.subList(i, Math.min(i + DEFAULT_CHUNK_SIZE, lines.size()));
                chunks.add(new Chunk(chunkLines, i));
            }
            return chunks;
        }
    }
}
