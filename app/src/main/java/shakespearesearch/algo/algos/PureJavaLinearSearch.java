package shakespearesearch.algo.algos;

import shakespearesearch.algo.Algo;
import shakespearesearch.utils.threading.Chunk;
import shakespearesearch.utils.threading.Match;

import java.util.ArrayList;
import java.util.List;

public class PureJavaLinearSearch extends Algo {

    public PureJavaLinearSearch() {
        super("Pure Java Linear Search");
    }

    @Override
    public List<Match> searchChunk(Chunk chunk, String searchTerm) {
        List<Match> matches = new ArrayList<>();
        int startIndex = chunk.getStartIndex();
        List<String> lines = chunk.getLines();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains(searchTerm)) {
                int lineNumber = startIndex + i;
                matches.add(new Match(lineNumber, line));
            }
        }
        return matches;
    }
}
