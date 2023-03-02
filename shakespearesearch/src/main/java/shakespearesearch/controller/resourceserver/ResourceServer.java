package shakespearesearch.controller.resourceserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import shakespearesearch.controller.resourceserver.utils.Compression;
import shakespearesearch.controller.resourceserver.utils.Pagination;
import shakespearesearch.controller.kafka.PaginatedData;
import shakespearesearch.controller.kafka.Producer;


@SpringBootApplication
public class ResourceServer {
    private static final int PAGESIZEKB= 50;
    private static final String RESOURCENAME = "/shakespeare.txt";
    private static final String TOPICNAME = "resource-paginated";

    public static void main(String[] args) {
        SpringApplication.run(ResourceServer.class, args);
    }

    public static void serveContent() throws IOException {
        //Read straight from the resource
        String content = readContent();
        
        //Gzip compress
        byte[] compressed = Compression.compress(content);
        
        //Cache?
    
        Producer producer = new Producer();
        List<byte[]> paginatedBytes = Pagination.paginate(compressed, PAGESIZEKB);
        for (int i = 0; i < paginatedBytes.size(); i++) {
            byte[] pageBytes = paginatedBytes.get(i);
            PaginatedData data = new PaginatedData(i, pageBytes);
            producer.produce(TOPICNAME, Integer.toString(i), data);
        }
        producer.close();
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
