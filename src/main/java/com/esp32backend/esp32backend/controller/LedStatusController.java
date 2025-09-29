package com.esp32backend.esp32backend.controller;

import com.esp32backend.esp32backend.service.LedStatusHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LedStatusController {

    @GetMapping("/api/led/status")
    public String getLedStatus() {
        return LedStatusHolder.getStatus();
    }
}
