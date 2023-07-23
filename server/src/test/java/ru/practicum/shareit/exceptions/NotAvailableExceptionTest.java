package ru.practicum.shareit.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class NotAvailableExceptionTest {

    @Test
    void testConstructor() {
        NotAvailableException actualNotAvailableException = new NotAvailableException("foo");
        assertNull(actualNotAvailableException.getCause());
        assertEquals(0, actualNotAvailableException.getSuppressed().length);
        assertEquals("foo", actualNotAvailableException.getMessage());
        assertEquals("foo", actualNotAvailableException.getLocalizedMessage());
    }
}

