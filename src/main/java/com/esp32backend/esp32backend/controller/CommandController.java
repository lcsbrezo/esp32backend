package com.esp32backend.esp32backend.controller;

import com.esp32backend.esp32backend.service.CommandService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/led")
public class CommandController {

    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/on")
    public String turnOn() {
        commandService.sendCommand("ON");
        return "LED encendido";
    }

    @PostMapping("/off")
    public String turnOff() {
        commandService.sendCommand("OFF");
        return "LED apagado";
    }
}
