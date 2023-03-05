package shakespearesearch.controller.resourceserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import shakespearesearch.controller.kafka.Consumer;
import shakespearesearch.controller.kafka.PaginatedData;
import shakespearesearch.controller.resourceserver.utils.Compression;

public class ResourceController {

    private static final String TOPICNAME = "resource-paginated";

    public static List<String> handleRequest() throws IOException {

        Consumer consumer = new Consumer("localhost:9092", TOPICNAME);
        consumer.start();

        // decompress and collect the data into a List<String>
        List<String> result = new ArrayList<>();
        List<PaginatedData> paginatedDataList = consumer.getPaginatedDataList();
        for (PaginatedData paginatedData : paginatedDataList) {
            byte[] compressedData = paginatedData.getData();
            String decompressedData = Compression.decompress(compressedData);
            try (BufferedReader reader = new BufferedReader(new StringReader(decompressedData))) {
                result.addAll(reader.lines().collect(Collectors.toList()));
            }
        }

        consumer.close();
        return result;
    }
}
