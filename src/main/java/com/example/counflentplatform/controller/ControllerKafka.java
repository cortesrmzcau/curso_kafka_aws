package com.example.counflentplatform.controller;

import com.example.counflentplatform.service.IKafka;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@RestController
@Log4j2
public class ControllerKafka {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private final IKafka iKafka;

    public ControllerKafka(IKafka iKafka) {
        this.iKafka = iKafka;
    }

    // Spring
    @GetMapping
    public CompletableFuture<ResponseEntity<HashMap>> enviaMensaje() {
        return iKafka.enviarMensaje("Mensaje de prueba");
    }

    // Producer
    @GetMapping(value = "/producer")
    public void producer() {
        log.info("Enviado...");
        // Enviar mensajes de forma sencilla
        for (int i = 0; i < 100; i++ ) {
            kafkaTemplate.send("devs4j-topic", String.valueOf(i), String.format("Mensaje desde el controlador " + i));
        }

        // Con callbacks asíncrono
        /*HashMap<String, String> hashMap = new HashMap<>();

        CompletableFuture<SendResult<String, String>> future =
                kafkaTemplate.send("devs4j-topic", "2222", "Ya se envio");

        return future.thenApply(result -> {
                    hashMap.put("Mensaje", "Se fue");
                    return ResponseEntity.ok(hashMap);
                }).exceptionally(ex -> {
                    hashMap.put("Mensaje", "No Se fue");
                    return ResponseEntity.status(500).body(hashMap);
                });*/
    }

}
