package shakespearesearch.utils.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

import shakespearesearch.backendserver.BackendServer;

public class ResourceReader {

    public static List<String> readLinesFromResource(String resourceName) throws IOException {
        String content = BackendServer.getContent();
        try (BufferedReader reader = new BufferedReader(new StringReader(content))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("Error reading resource " + resourceName + ": " + e.getMessage());
        }
    }
    
}
