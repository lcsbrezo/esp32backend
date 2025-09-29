package com.esp32backend.esp32backend.service;

public class LedStatusHolder {
    private static volatile String status = "UNKNOWN";

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String newStatus) {
        status = newStatus;
    }
}
