package shakespearesearch.controller.resourceserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResourceServer {

    private static final String RESOURCENAME = "/shakespeare.txt";

    public static void main(String[] args) {
        SpringApplication.run(ResourceServer.class, args);
    }

    public static String getContent() throws IOException {
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
