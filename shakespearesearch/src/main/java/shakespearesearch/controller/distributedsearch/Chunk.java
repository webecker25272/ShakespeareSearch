package shakespearesearch.controller.distributedsearch;

import java.util.List;

public class Chunk {
    private final List<String> lines;
    private final int startIndex;

    public Chunk(List<String> lines, int startIndex) {
        this.lines = lines;
        this.startIndex = startIndex;
    }

    public List<String> getLines() {
        return lines;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return startIndex + lines.size() - 1;
    }
}