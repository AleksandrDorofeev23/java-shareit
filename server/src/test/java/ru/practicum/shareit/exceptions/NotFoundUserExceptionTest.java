package ru.practicum.shareit.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class NotFoundUserExceptionTest {

    @Test
    void testConstructor() {
        NotFoundUserException actualNotFoundUserException = new NotFoundUserException("foo");
        assertNull(actualNotFoundUserException.getCause());
        assertEquals(0, actualNotFoundUserException.getSuppressed().length);
        assertEquals("foo", actualNotFoundUserException.getMessage());
        assertEquals("foo", actualNotFoundUserException.getLocalizedMessage());
    }
}

