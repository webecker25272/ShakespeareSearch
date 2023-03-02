package shakespearesearch.controller.kafka;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class Producer {
    private final KafkaProducer<String, PaginatedData> producer;

    public Producer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "shakespearesearch.controller.resourceserver.utils.kafka.PaginatedDataSerializer");

        producer = new KafkaProducer<>(props);
    }

    public void produce(String topic, String key, PaginatedData data) {
        try {
            producer.send(new ProducerRecord<>(topic, key, data)).get(); //org.apache.kafka.clients.producer.RecordMetadata
        } catch (InterruptedException | ExecutionException e) {
            System.err.printf("Error producing record: %s%n", e.getMessage());
        }
    }

    public void close() {
        producer.close();
    }
}
