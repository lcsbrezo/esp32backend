package com.esp32backend.esp32backend.repository;

import com.esp32backend.esp32backend.model.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {}
