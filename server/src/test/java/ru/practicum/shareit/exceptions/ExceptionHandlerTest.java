package ru.practicum.shareit.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExceptionHandlerTest {

    @Test
    void testHandleNotFoundEntity() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        Map<String, String> actualHandleNotFoundEntityResult = exceptionHandler
                .handleNotFoundEntity(new NotFoundException("foo"));
        assertEquals(2, actualHandleNotFoundEntityResult.size());
        assertEquals("Объект не был найден.", actualHandleNotFoundEntityResult.get("error"));
        assertEquals("foo", actualHandleNotFoundEntityResult.get("message"));
    }

    @Test
    void testHandleServerError() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        Map<String, String> actualHandleServerErrorResult = exceptionHandler.handleServerError(new ValidException("foo"));
        assertEquals(2, actualHandleServerErrorResult.size());
        assertEquals("Некорректный запрос.", actualHandleServerErrorResult.get("error"));
        assertEquals("foo", actualHandleServerErrorResult.get("message"));
    }

    @Test
    void testHandleBadRequest() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        Map<String, String> actualHandleBadRequestResult = exceptionHandler
                .handleBadRequest(new IOException("An error occurred"));
        assertEquals(2, actualHandleBadRequestResult.size());
        assertEquals("Ошибка сервера.", actualHandleBadRequestResult.get("error"));
        assertEquals("An error occurred", actualHandleBadRequestResult.get("message"));
    }

    @Test
    void testHandleConflict() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        Map<String, String> actualHandleConflictResult = exceptionHandler.handleConflict(new EmailException("foo"));
        assertEquals(2, actualHandleConflictResult.size());
        assertEquals("Некорректный запрос.", actualHandleConflictResult.get("error"));
        assertEquals("foo", actualHandleConflictResult.get("message"));
    }

    @Test
    void testHandleMethodArgumentNotValidException() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValidException.getStackTrace())
                .thenReturn(new StackTraceElement[]{new StackTraceElement("Declaring Class", "Method Name", "foo.txt", 2)});
        when(methodArgumentNotValidException.getMessage()).thenReturn("An error occurred");
        Map<String, String> actualHandleMethodArgumentNotValidExceptionResult = exceptionHandler
                .handleMethodArgumentNotValidException(methodArgumentNotValidException);
        assertEquals(2, actualHandleMethodArgumentNotValidExceptionResult.size());
        assertEquals("Некорректный запрос.", actualHandleMethodArgumentNotValidExceptionResult.get("error"));
        assertEquals("An error occurred", actualHandleMethodArgumentNotValidExceptionResult.get("message"));
        verify(methodArgumentNotValidException).getStackTrace();
        verify(methodArgumentNotValidException, atLeast(1)).getMessage();
    }

    @Test
    void testHandleNotAvailableException() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        Map<String, String> actualHandleNotAvailableExceptionResult = exceptionHandler
                .handleNotAvailableException(new NotAvailableException("foo"));
        assertEquals(2, actualHandleNotAvailableExceptionResult.size());
        assertEquals("Некорректный запрос.", actualHandleNotAvailableExceptionResult.get("error"));
        assertEquals("foo", actualHandleNotAvailableExceptionResult.get("message"));
    }

    @Test
    void testHandleDateTimeException() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        Map<String, String> actualHandleDateTimeExceptionResult = exceptionHandler
                .handleDateTimeException(new DateTimeException("foo"));
        assertEquals(2, actualHandleDateTimeExceptionResult.size());
        assertEquals("Некорректный запрос.", actualHandleDateTimeExceptionResult.get("error"));
        assertEquals("foo", actualHandleDateTimeExceptionResult.get("message"));
    }

    @Test
    void testHandleAccessException() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        Map<String, String> actualHandleAccessExceptionResult = exceptionHandler
                .handleAccessException(new AccessException("foo"));
        assertEquals(2, actualHandleAccessExceptionResult.size());
        assertEquals("Некорректный запрос.", actualHandleAccessExceptionResult.get("error"));
        assertEquals("foo", actualHandleAccessExceptionResult.get("message"));
    }

    @Test
    void testHandleIllegalArgumentException() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        Map<String, String> actualHandleIllegalArgumentExceptionResult = exceptionHandler
                .handleIllegalArgumentException(new IllegalArgumentException("error"));
        assertEquals(2, actualHandleIllegalArgumentExceptionResult.size());
        assertEquals("Некорректный запрос.", actualHandleIllegalArgumentExceptionResult.get("error"));
        assertEquals("error", actualHandleIllegalArgumentExceptionResult.get("message"));
    }

    @Test
    void testHandleStateException() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        Map<String, String> actualHandleStateExceptionResult = exceptionHandler
                .handleStateException(new StateException("foo"));
        assertEquals(2, actualHandleStateExceptionResult.size());
        assertEquals("Unknown state: UNSUPPORTED_STATUS", actualHandleStateExceptionResult.get("error"));
        assertEquals("foo", actualHandleStateExceptionResult.get("message"));
    }

}

