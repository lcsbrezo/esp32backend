package com.esp32backend.esp32backend.config;

import com.esp32backend.esp32backend.service.LedStatusHolder;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class MqttConfig {

    private final String host = "dc899ade1e5d419790b1a9ace5c8c090.s1.eu.hivemq.cloud";
    private final int port = 8883;
    private final String username = "lucas";
    private final String password = "Lucas123";

    @Bean
    public Mqtt5AsyncClient mqttClient() {
        Mqtt5AsyncClient client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(port)
                .sslWithDefaultConfig()
                .automaticReconnectWithDefaultConfig() // 🔄 reconexión automática
                .buildAsync();

        client.connectWith()
                .simpleAuth()
                .username(username)
                .password(StandardCharsets.UTF_8.encode(password))
                .applySimpleAuth()
                .send()
                .whenComplete((connAck, throwable) -> {
                    if (throwable != null) {
                        System.err.println("❌ Error conectando MQTT: " + throwable.getMessage());
                    } else {
                        System.out.println("✅ Cliente MQTT conectado!");

                        // 🔹 Suscribirse al estado del LED
                        client.subscribeWith()
                                .topicFilter("casa/luz/estado")
                                .callback(publish -> {
                                    String estado = new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8);
                                    System.out.println("📩 Estado recibido del LED: " + estado);
                                    LedStatusHolder.setStatus(estado); // Guardar estado
                                })
                                .send();
                    }
                }).join();

        return client;
    }
}

