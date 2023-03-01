package shakespearesearch.controller.resourceserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import shakespearesearch.controller.resourceserver.utils.Caching;
import shakespearesearch.controller.resourceserver.utils.Compression;
//import shakespearesearch.controller.resourceserver.utils.Pagination;

@SpringBootApplication
public class ResourceServer {

    private static final String RESOURCENAME = "/shakespeare.txt";

    public static void main(String[] args) {
        SpringApplication.run(ResourceServer.class, args);
    }

    public static String getContent() throws IOException {
        //Read straight from the resource
        String content = readContent();
        
        //Compress
        content = Compression.compress(content);
        
        //Cache
        Caching.cache(content);
        

        
        return content;
    }
    
    
    private static String readContent() throws IOException {
        InputStream inputStream = ResourceServer.class.getResourceAsStream(RESOURCENAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }
}
