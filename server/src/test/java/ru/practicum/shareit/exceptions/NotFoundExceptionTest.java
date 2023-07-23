package ru.practicum.shareit.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class NotFoundExceptionTest {

    @Test
    void testConstructor() {
        NotFoundException actualNotFoundException = new NotFoundException("foo");
        assertNull(actualNotFoundException.getCause());
        assertEquals(0, actualNotFoundException.getSuppressed().length);
        assertEquals("foo", actualNotFoundException.getMessage());
        assertEquals("foo", actualNotFoundException.getLocalizedMessage());
    }
}

