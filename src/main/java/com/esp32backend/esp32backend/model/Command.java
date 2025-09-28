package com.esp32backend.esp32backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commands")
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String command;
    private String source;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Command() {}
    public Command(String command, String source) {
        this.command = command;
        this.source = source;
    }

    public Long getId() { return id; }
    public String getCommand() { return command; }
    public void setCommand(String command) { this.command = command; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
