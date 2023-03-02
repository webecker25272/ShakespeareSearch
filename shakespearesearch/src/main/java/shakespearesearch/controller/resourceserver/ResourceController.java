package shakespearesearch.controller.resourceserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

import shakespearesearch.controller.resourceserver.utils.Compression;;

public class ResourceController {

    public static List<String> handleRequest() throws IOException {
        //check cache for a hit
        
        //make n pagination requests to resource server
        //collate results into a List<BufferedReader> or InputStream
        //send it to the model 

        //decompress
        byte[] compressed = ResourceServer.serveContent();
        String decompressed = Compression.decompress(compressed);

        try (BufferedReader reader = new BufferedReader(new StringReader(decompressed))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("Error reading resource.  " + e.getMessage());
        }
    }
    
}
