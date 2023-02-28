package shakespearesearch.utils.eval;

import shakespearesearch.algo.Algo;

public class Iterator {
    private static final int DEFAULT_MIN_THREADS = 1;
    private static final int DEFAULT_STEP_SIZE = 1;

    public static void iterate(Algo algo, String resourceName, String searchTerm, int maxThreads)
            throws InterruptedException {
        Evaluator evaluator = new Evaluator(algo.getAlgoName(), maxThreads);

        for (int numThreads = DEFAULT_MIN_THREADS; numThreads <= maxThreads; numThreads += DEFAULT_STEP_SIZE) {
            evaluator.start();
            int numMatches = algo.run(resourceName, searchTerm, numThreads);
            evaluator.stop(numMatches);
            System.out.println(numMatches);
            //evaluator.evaluate();
        }
    }
}
