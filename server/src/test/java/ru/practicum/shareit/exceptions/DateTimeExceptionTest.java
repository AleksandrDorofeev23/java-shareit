package ru.practicum.shareit.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class DateTimeExceptionTest {

    @Test
    void testConstructor() {
        DateTimeException actualDateTimeException = new DateTimeException("foo");
        assertNull(actualDateTimeException.getCause());
        assertEquals(0, actualDateTimeException.getSuppressed().length);
        assertEquals("foo", actualDateTimeException.getMessage());
        assertEquals("foo", actualDateTimeException.getLocalizedMessage());
    }
}

