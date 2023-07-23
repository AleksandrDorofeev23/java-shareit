package ru.practicum.shareit.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ValidExceptionTest {

    @Test
    void testConstructor() {
        ValidException actualValidException = new ValidException("foo");
        assertNull(actualValidException.getCause());
        assertEquals(0, actualValidException.getSuppressed().length);
        assertEquals("foo", actualValidException.getMessage());
        assertEquals("foo", actualValidException.getLocalizedMessage());
    }
}

