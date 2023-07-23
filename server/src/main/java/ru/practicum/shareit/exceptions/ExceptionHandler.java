package ru.practicum.shareit.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundEntity(final NotFoundException e) {
        log.info(e.getMessage());
        StringBuilder message = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            message.append(System.lineSeparator()).append(stackTraceElement.toString());
        }
        log.info(message.toString());
        return Map.of("error", "Объект не был найден.",
                "message", e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotUserFoundEntity(final NotFoundUserException e) {
        log.info(e.getMessage());
        StringBuilder message = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            message.append(System.lineSeparator()).append(stackTraceElement.toString());
        }
        log.info(message.toString());
        return Map.of("error", "Пользователь не был найден.",
                "message", e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleServerError(final ValidException e) {
        log.info(e.getMessage());
        StringBuilder message = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            message.append(System.lineSeparator()).append(stackTraceElement.toString());
        }
        log.info(message.toString());
        return Map.of("error", "Некорректный запрос.",
                "message", e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleBadRequest(final Throwable e) {
        log.info(e.getMessage());
        StringBuilder message = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            message.append(System.lineSeparator()).append(stackTraceElement.toString());
        }
        log.info(message.toString());
        return Map.of("error", "Ошибка сервера.",
                "message", e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConflict(final EmailException e) {
        log.info(e.getMessage());
        StringBuilder message = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            message.append(System.lineSeparator()).append(stackTraceElement.toString());
        }
        log.info(message.toString());
        return Map.of("error", "Некорректный запрос.",
                "message", e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.info(e.getMessage());
        StringBuilder message = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            message.append(System.lineSeparator()).append(stackTraceElement.toString());
        }
        log.info(message.toString());
        return Map.of("error", "Некорректный запрос.",
                "message", e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotAvailableException(final NotAvailableException e) {
        log.info(e.getMessage());
        StringBuilder message = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            message.append(System.lineSeparator()).append(stackTraceElement.toString());
        }
        log.info(message.toString());
        return Map.of("error", "Некорректный запрос.",
                "message", e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleDateTimeException(final DateTimeException e) {
        log.info(e.getMessage());
        StringBuilder message = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            message.append(System.lineSeparator()).append(stackTraceElement.toString());
        }
        log.info(message.toString());
        return Map.of("error", "Некорректный запрос.",
                "message", e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleAccessException(final AccessException e) {
        log.info(e.getMessage());
        StringBuilder message = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            message.append(System.lineSeparator()).append(stackTraceElement.toString());
        }
        log.info(message.toString());
        return Map.of("error", "Некорректный запрос.",
                "message", e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(final IllegalArgumentException e) {
        log.info(e.getMessage());
        StringBuilder message = new StringBuilder();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            message.append(System.lineSeparator()).append(stackTraceElement.toString());
        }
        log.info(message.toString());
        return Map.of("error", "Некорректный запрос.",
                "message", e.getMessage());
    }

}