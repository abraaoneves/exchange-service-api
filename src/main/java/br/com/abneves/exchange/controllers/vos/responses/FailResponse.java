package br.com.abneves.exchange.controllers.vos.responses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FailResponse {
    LocalDateTime timestamp;
    String message;
    List<String> details;

    public static FailResponse of(final String message, final List<String> details) {
        return new FailResponse(LocalDateTime.now(), message, details);
    }
}
