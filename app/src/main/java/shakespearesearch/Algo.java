package shakespearesearch;

public abstract class Algo {
    private final String algoName;

    public Algo(String algoName) {
        this.algoName = algoName;
    }

    public abstract void run(String resourceName, String searchTerm, int numThreads);

    public String getAlgoName() {
        return algoName;
    }
}
