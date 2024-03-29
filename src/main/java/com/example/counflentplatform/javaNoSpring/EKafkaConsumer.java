package com.example.counflentplatform.javaNoSpring;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class EKafkaConsumer {
    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.put("bootstrap-servers", "localhost:9092"); // Broker de kafka
        properties.put("group.id", "devs4j-group"); // Groupid con tiene 1 o mas consumidores de mensajes
        properties.put("enable.auto.commit", "true"); // En backogrund se realiza un commit por cada mensaje que se lee
        properties.put("auto.commit.internal.ms", "1000"); // Cada que tiempo se va a realizar el commit a esos offsets
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try(KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            // Si queremos volver al "pasado" y leer los mensajes que ya se habian leido deberemos implementar seek
            TopicPartition topicPartition = new TopicPartition("devs4j-topic", 4);

            consumer.assign(Arrays.asList(topicPartition));
            consumer.seek(topicPartition, 50);

            consumer.subscribe(Arrays.asList("devs4j-topic"));
            // subscribe es para suscribirnos al topico que vamos a leer

            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
                // ConsumerRecords son los registros consumidos por el topico
                // Pool es para conocer que tiempo se debe esperar para traer los mensajes

                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    // Recorremos los mensajes obtenidos y los mostramos en la consola
                    System.out.println("Offset = {}, Partition = {}, Key = {}, Value = {} " +
                            consumerRecord.offset()     +
                            consumerRecord.partition()  +
                            consumerRecord.key()        +
                            consumerRecord.value());
                }
            }
        }
    }
}
