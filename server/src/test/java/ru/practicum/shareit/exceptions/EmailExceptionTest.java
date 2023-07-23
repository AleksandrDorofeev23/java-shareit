package ru.practicum.shareit.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class EmailExceptionTest {

    @Test
    void testConstructor() {
        EmailException actualEmailException = new EmailException("foo");
        assertNull(actualEmailException.getCause());
        assertEquals(0, actualEmailException.getSuppressed().length);
        assertEquals("foo", actualEmailException.getMessage());
        assertEquals("foo", actualEmailException.getLocalizedMessage());
    }
}

