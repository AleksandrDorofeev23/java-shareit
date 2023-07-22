package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleStateException(final StateException e) {
        log.info(e.getMessage());
        StringBuilder message = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            message.append(System.lineSeparator()).append(stackTraceElement.toString());
        }
        log.info(message.toString());
        return Map.of("error", "Unknown state: UNSUPPORTED_STATUS",
                "message", e.getMessage());
    }
}
