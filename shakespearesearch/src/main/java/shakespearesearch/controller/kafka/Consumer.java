package shakespearesearch.controller.kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import shakespearesearch.controller.resourceserver.utils.Compression;

public class Consumer {
    private KafkaConsumer<String, byte[]> consumer;

    public Consumer(String bootstrapServers, String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
    }

    public void consume() {
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(1000));
            records.forEach(record -> {
                byte[] decompressed = Compression.decompress(record.value());
                String message = new String(decompressed);
            });
        }
    }

    public void close() {
        consumer.close();
    }
}
