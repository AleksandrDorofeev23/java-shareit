package ru.practicum.shareit.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class AccessExceptionTest {

    @Test
    void testConstructor() {
        AccessException actualAccessException = new AccessException("foo");
        assertNull(actualAccessException.getCause());
        assertEquals(0, actualAccessException.getSuppressed().length);
        assertEquals("foo", actualAccessException.getMessage());
        assertEquals("foo", actualAccessException.getLocalizedMessage());
    }
}

