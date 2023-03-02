package shakespearesearch.controller.resourceserver.utils;

import java.util.ArrayList;
import java.util.List;

public class Pagination {

    public static List<byte[]> paginate(byte[] compressed, int pageSizeKB) {
        List<byte[]> pages = new ArrayList<>();
        int pageSizeBytes = pageSizeKB * 1024;

        int position = 0;
        while (position < compressed.length) {
            int remaining = compressed.length - position;
            int pageSizeToUse = Math.min(pageSizeBytes, remaining);
            byte[] page = new byte[pageSizeToUse];
            System.arraycopy(compressed, position, page, 0, pageSizeToUse);
            pages.add(page);
            position += pageSizeToUse;
        }
    
        return pages;
    }
    
}
