package shakespearesearch.controller.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import shakespearesearch.controller.resourceserver.utils.Compression;

public class Producer {
    private KafkaProducer<String, byte[]> producer;

    public Producer(String bootstrapServers) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        producer = new KafkaProducer<>(props);
    }

    public void produce(String topic, byte[] data) {
        byte[] compressed = Compression.compress(data);
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, compressed);
        producer.send(record);
    }

    public void close() {
        producer.close();
    }
}
