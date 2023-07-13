package ru.practicum.shareit.booking.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.model.dto.BookingInDto;
import ru.practicum.shareit.booking.model.dto.BookingOutDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.service.BookingServiceImpl;


@ContextConfiguration(classes = {BookingController.class})
@ExtendWith(SpringExtension.class)
class BookingControllerTest {
    @Autowired
    private BookingController bookingController;

    @MockBean
    private BookingService bookingService;

    @Test
    void testGetByUser() throws Exception {
        when(bookingService.getByUser(anyLong(), (String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookings")
                .param("state", "foo")
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetByOwner() throws Exception {
        when(bookingService.getByOwner(anyLong(), (String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookings/owner")
                .param("state", "foo")
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testCreate() {
        BookingServiceImpl bookingServiceImpl = mock(BookingServiceImpl.class);
        BookingOutDto bookingOutDto = new BookingOutDto();
        when(bookingServiceImpl.create((BookingInDto) any(), anyLong())).thenReturn(bookingOutDto);
        BookingController bookingController = new BookingController(bookingServiceImpl);
        assertSame(bookingOutDto, bookingController.create(1L, new BookingInDto()));
        verify(bookingServiceImpl).create((BookingInDto) any(), anyLong());
    }

    @Test
    void testConfirm() throws Exception {
        when(bookingService.confirm(anyLong(), anyLong(), anyBoolean())).thenReturn(new BookingOutDto());
        MockHttpServletRequestBuilder patchResult = MockMvcRequestBuilders.patch("/bookings/{id}", 1L);
        MockHttpServletRequestBuilder requestBuilder = patchResult.param("approved", String.valueOf(true))
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":0,\"start\":null,\"end\":null,\"status\":null,\"item\":null,\"booker\":null}"));
    }

    @Test
    void testGetByID() throws Exception {
        when(bookingService.getById(anyLong(), anyLong())).thenReturn(new BookingOutDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookings/{id}", 1L)
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":0,\"start\":null,\"end\":null,\"status\":null,\"item\":null,\"booker\":null}"));
    }

    @Test
    void testGetByID2() throws Exception {
        when(bookingService.getByUser(anyLong(), (String) any())).thenReturn(new ArrayList<>());
        when(bookingService.getById(anyLong(), anyLong())).thenReturn(new BookingOutDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookings/{id}", "", "Uri Variables")
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

