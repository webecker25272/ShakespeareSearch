package shakespearesearch;

public class Iterator {
    private static final int DEFAULT_MIN_THREADS = 1;
    private static final int DEFAULT_MAX_THREADS = 200;
    private static final int DEFAULT_STEP_SIZE = 1;

    public static void iterate(Algo algo, String resourceName, String searchTerm) throws InterruptedException {
        iterate(algo, resourceName, searchTerm, DEFAULT_MIN_THREADS, DEFAULT_MAX_THREADS, DEFAULT_STEP_SIZE);
    }

    public static void iterate(Algo algo, String resourceName, String searchTerm, int minThreads, int maxThreads) throws InterruptedException {
        iterate(algo, resourceName, searchTerm, minThreads, maxThreads, DEFAULT_STEP_SIZE);
    }

    public static void iterate(Algo algo, String resourceName, String searchTerm, int minThreads, int maxThreads, int stepSize) throws InterruptedException {
        if (minThreads < 1 || maxThreads < 1 || stepSize < 1) {
            throw new IllegalArgumentException("Invalid args");
        }
        if (minThreads > maxThreads) {
            throw new IllegalArgumentException("Invalid args");
        }
        for (int numThreads = minThreads; numThreads <= maxThreads; numThreads += stepSize) {
            algo.run(resourceName, searchTerm, numThreads);
        }
    }
}
