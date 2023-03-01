package shakespearesearch.utils.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {

    public static List<String> readLinesFromResource(String resourceName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(resourceName)))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("Error reading resource " + resourceName + ": " + e.getMessage());
        }
    }
}
