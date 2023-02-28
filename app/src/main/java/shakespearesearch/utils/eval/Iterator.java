package shakespearesearch.utils.eval;

import shakespearesearch.algo.Algo;

public class Iterator {

    public void run(String resourceName, String searchTerm, int maxThreads) {
        Evaluator evaluator = new Evaluator(getAlgoName(), maxThreads);
        evaluator.start();
        try {
            List<Chunk> chunks = ChunkUtils.readChunksFromResource(resourceName);
            List<Match> matches = searchChunks(chunks, searchTerm);
            int numMatches = matches.size();
            evaluator.stop(numMatches);
            evaluator.evaluate();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
