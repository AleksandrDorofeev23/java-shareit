package ru.practicum.shareit.exceptions;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(String s) {
        super(s);
    }
}
