package shakespearesearch.controller.kafka;

public class PaginatedData {
    private final int pageNumber;
    private final byte[] pageData;

    public PaginatedData(int pageNumber, byte[] pageData) {
        this.pageNumber = pageNumber;
        this.pageData = pageData;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public byte[] getPageData() {
        return pageData;
    }
}
