package com.esp32backend.esp32backend.service;

import com.esp32backend.esp32backend.model.Command;
import com.esp32backend.esp32backend.repository.CommandRepository;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

@Service
public class CommandService {

    private final CommandRepository commandRepository;
    private final Mqtt5AsyncClient mqttClient;

    public CommandService(CommandRepository commandRepository, Mqtt5AsyncClient mqttClient) {
        this.commandRepository = commandRepository;
        this.mqttClient = mqttClient;

        // Conectar MQTT si no está conectado
        if (!mqttClient.getState().isConnected()) {
            mqttClient.connectWith()
                    .simpleAuth()
                    .username("lucas")
                    .password(StandardCharsets.UTF_8.encode("Lucas123"))
                    .applySimpleAuth()
                    .send()
                    .whenComplete((connAck, throwable) -> {
                        if (throwable != null) {
                            System.err.println("❌ Error conectando MQTT: " + throwable.getMessage());
                        } else {
                            System.out.println("✅ Cliente MQTT conectado!");
                        }
                    }).join();
        }
    }

    public void sendCommand(String command) {
        // Guardar en la base de datos
        Command c = new Command(command.toUpperCase(), "APP");
        commandRepository.save(c);

        // Publicar en MQTT solo si está conectado
        if (mqttClient.getState().isConnected()) {
            mqttClient.publishWith()
                    .topic("casa/luz")
                    .payload(command.toUpperCase().getBytes(StandardCharsets.UTF_8))
                    .send()
                    .whenComplete((pub, ex) -> {
                        if (ex != null) ex.printStackTrace();
                        else System.out.println("💡 Mensaje MQTT enviado: " + command.toUpperCase());
                    });
        } else {
            System.err.println("⚠️ Cliente MQTT no conectado, no se puede enviar: " + command.toUpperCase());
        }
    }
}