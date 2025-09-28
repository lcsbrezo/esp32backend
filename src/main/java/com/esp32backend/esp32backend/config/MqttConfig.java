package com.esp32backend.esp32backend.config;

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
                .sslWithDefaultConfig() // TLS
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
                    }
                }).join();

        return client;
    }
}

