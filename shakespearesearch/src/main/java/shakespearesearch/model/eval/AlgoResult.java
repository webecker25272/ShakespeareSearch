package shakespearesearch.utils.eval;

public class AlgoResult {
    private String algoName;
    private Object[] runData;

    public AlgoResult(String algoName, Object[] runData) {
        this.algoName = algoName;
        this.runData = runData;
    }

    public String getAlgoName() {
        return algoName;
    }

    public Object[] getRunData() {
        return runData;
    }
}
