// package shakespearesearch.controller.resourceserver.utils;

// import java.util.List;

// public class Pagination {
//     private final int pageSize;

//     public Pagination(int pageSize) {
//         this.pageSize = pageSize;
//     }

//     public List<String> paginate(List<String> lines, int page) {
//         int startIndex = (page - 1) * pageSize;
//         if (startIndex >= lines.size()) {
//             return List.of();
//         }
//         int endIndex = Math.min(startIndex + pageSize, lines.size());
//         return lines.subList(startIndex, endIndex);
//     }
// }
