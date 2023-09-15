package com.example.counflentplatform;

import com.example.counflentplatform.configuration.KafkaConfiguration;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

@SpringBootApplication
@Log4j2
@Import(KafkaConfiguration.class)
public class CounflentPlatformApplication {

	/* Con esta configuracion se lee el batch de mensajes en grupos de 10, con un intervalos de 60000 ms,
	donde para el containerFactory se hace referencia al metodo ConcurrentKafkaListenerContainerFactory */
	@KafkaListener(topics ="devs4j-topic", containerFactory = "listenerContainerFactory", groupId = "devs4j-group",
			properties = {"max.poll.interval.ms:60000", "max.poll.records:10" })
	public void listen(List<ConsumerRecord<String, String>> message) { // Con este metodo leemos los mensajes
		// Ejemplo sencillo
		/*log.info("Mensaje recibido: ", message);

		for (String mensaje: message) {
			log.info("Mensaje recibido = {} ", mensaje);
		}
		log.info("Batch compleado");*/

		// Ejemplo mas completo
		log.info("Comienza a leer mensajes.....");

		for (ConsumerRecord<String, String>  mensaje: message) {
			log.info("Mensaje recibido = {} ", mensaje);
		}
		log.info("Batch compleado");
	}

	public static void main(String[] args) {
		SpringApplication.run(CounflentPlatformApplication.class, args);
	}

}
