package shakespearesearch.controller.resourceserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceController {

    public static List<String> readLinesFromResource(String resourceName) throws IOException {
        String content = ResourceServer.getContent();
        try (BufferedReader reader = new BufferedReader(new StringReader(content))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("Error reading resource " + resourceName + ": " + e.getMessage());
        }
    }
    
}
