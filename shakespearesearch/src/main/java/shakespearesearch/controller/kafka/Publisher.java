package shakespearesearch.controller.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Publisher {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC_NAME = "my-topic";

    private Producer<String, String> producer;

    public Publisher() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.setProperty("key.serializer", StringSerializer.class.getName());
        props.setProperty("value.serializer", StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);
    } 

    public void publish(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, message);
        producer.send(record);
    }

    public void close() {
        producer.close();
    }
}