package shakespearesearch.PureJavaLinearSearch;

import java.util.List;

public class SearchTask {
        private Chunk chunk;
        private String searchTerm;
        private List<Match> matches;
    
        public SearchTask(Chunk chunk, String searchTerm, List<Match> matches) {
            this.chunk = chunk;
            this.searchTerm = searchTerm;
            this.matches = matches;
        }
    
        public void search() {
            //write this
        }
    }
    
