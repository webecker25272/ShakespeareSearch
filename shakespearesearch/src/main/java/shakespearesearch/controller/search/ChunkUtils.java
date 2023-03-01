package shakespearesearch.controller.search;

import java.util.ArrayList;
import java.util.List;

import shakespearesearch.controller.threading.Chunk;

public class ChunkUtils {
    private static final int DEFAULT_CHUNK_SIZE = 1000;

    public static List<Chunk> createChunks(List<String> lines) {
        List<Chunk> chunks = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += DEFAULT_CHUNK_SIZE) {
            List<String> chunkLines = lines.subList(i, Math.min(i + DEFAULT_CHUNK_SIZE, lines.size()));
            chunks.add(new Chunk(chunkLines, i));
        }
        return chunks;
    }
    
}
