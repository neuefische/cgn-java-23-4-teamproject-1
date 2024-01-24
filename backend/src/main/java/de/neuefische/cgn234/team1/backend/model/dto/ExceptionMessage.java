package de.neuefische.cgn234.team1.backend.model.dto;

import java.time.Instant;

public record ExceptionMessage(
        String errorMessage,
        Instant errorTime
) {
    public ExceptionMessage(String errorMessage) {
        this(errorMessage, Instant.now());
    }
}
