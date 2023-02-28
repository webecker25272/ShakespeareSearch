package shakespearesearch.algo.algos;

import shakespearesearch.algo.Algo;
import shakespearesearch.utils.threading.Chunk;
import shakespearesearch.utils.threading.Match;

import java.util.List;

public class PureJavaLinearSearch extends Algo {
    private static final int CHUNK_SIZE = 1000;

    public PureJavaLinearSearch() {
        super("Pure Java Linear Search");
    }

    @Override
    public List<Match> searchChunk(Chunk chunks, String searchTerm) {
        // search each chunk for matches
        int numMatches = 0;
        for (Chunk chunk : chunks) {
            numMatches += searchChunk(chunk, searchTerm).size();
        }
        return null;
    }
}
