package shakespearesearch.controller.distributedsearch;


public class Match {
    private final int lineNumber;
    private final String line;

    public Match(int lineNumber, String line) {
        this.lineNumber = lineNumber;
        this.line = line;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getLine() {
        return line;
    }
}
