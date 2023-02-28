package shakespearesearch.algo;

import shakespearesearch.utils.threading.Chunk;
import shakespearesearch.utils.threading.Match;

import java.util.List;

public abstract class Algo {
    private final String algoName;

    public Algo(String algoName) {
        this.algoName = algoName;
    }

    public abstract List<Match> searchChunks(List<Chunk> chunks, String searchTerm);

    public String getAlgoName() {
        return algoName;
    }
}
