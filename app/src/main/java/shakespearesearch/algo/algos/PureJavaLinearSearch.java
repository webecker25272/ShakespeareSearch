package shakespearesearch.algo.algos;

import shakespearesearch.algo.Algo;
import shakespearesearch.utils.search.ChunkUtils;
import shakespearesearch.utils.search.FileUtils;

import shakespearesearch.utils.search.MatchUtils;
import shakespearesearch.utils.threading.Chunk;
import shakespearesearch.utils.threading.Match;
import shakespearesearch.utils.eval.Evaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PureJavaLinearSearch extends Algo {
    private static final int CHUNK_SIZE = 1000;

    public PureJavaLinearSearch() {
        super("Pure Java Linear Search");
    }


}
