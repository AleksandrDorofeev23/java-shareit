package ru.practicum.shareit.booking.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.mapper.BookingMapperImpl;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingInDto;
import ru.practicum.shareit.booking.model.dto.BookingOutDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookingMapperImpl.class})
@ExtendWith(SpringExtension.class)
class BookingMapperImplTest {
    @Autowired
    private BookingMapperImpl bookingMapperImpl;

    @Test
    void testToBooking() {
        Booking actualToBookingResult = bookingMapperImpl.toBooking(new BookingInDto());
        assertNull(actualToBookingResult.getStart());
        assertNull(actualToBookingResult.getEnd());
    }

    @Test
    void testToBooking2() {
        BookingInDto bookingInDto = mock(BookingInDto.class);
        when(bookingInDto.getEnd()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(bookingInDto.getStart()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        Booking actualToBookingResult = bookingMapperImpl.toBooking(bookingInDto);
        assertEquals("01:01", actualToBookingResult.getStart().toLocalTime().toString());
        assertEquals("0001-01-01", actualToBookingResult.getEnd().toLocalDate().toString());
        verify(bookingInDto).getEnd();
        verify(bookingInDto).getStart();
    }

    @Test
    void testToBookingDto() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user2);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user1);
        item.setRequest(itemRequest);

        Booking booking = new Booking();
        booking.setBooker(user);
        booking.setEnd(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setStatus(Status.WAITING);
        BookingDto actualToBookingDtoResult = bookingMapperImpl.toBookingDto(booking);
        assertEquals(1L, actualToBookingDtoResult.getBookerId());
        assertEquals(Status.WAITING, actualToBookingDtoResult.getStatus());
        assertEquals(1L, actualToBookingDtoResult.getItemId());
        assertEquals("01:01", actualToBookingDtoResult.getStart().toLocalTime().toString());
        assertEquals(1L, actualToBookingDtoResult.getId());
        assertEquals("0001-01-01", actualToBookingDtoResult.getEnd().toLocalDate().toString());
    }

    @Test
    void testToBookingDto2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user2);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user1);
        item.setRequest(itemRequest);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setId(1L);
        user4.setName("Name");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user4);

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user3);
        item1.setRequest(itemRequest1);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setId(1L);
        user5.setName("Name");
        Booking booking = mock(Booking.class);
        when(booking.getEnd()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(booking.getStart()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(booking.getId()).thenReturn(1L);
        when(booking.getStatus()).thenReturn(Status.WAITING);
        when(booking.getItem()).thenReturn(item1);
        when(booking.getBooker()).thenReturn(user5);
        doNothing().when(booking).setBooker((User) any());
        doNothing().when(booking).setEnd((LocalDateTime) any());
        doNothing().when(booking).setId(anyLong());
        doNothing().when(booking).setItem((Item) any());
        doNothing().when(booking).setStart((LocalDateTime) any());
        doNothing().when(booking).setStatus((Status) any());
        booking.setBooker(user);
        booking.setEnd(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setStatus(Status.WAITING);
        BookingDto actualToBookingDtoResult = bookingMapperImpl.toBookingDto(booking);
        assertEquals(1L, actualToBookingDtoResult.getBookerId());
        assertEquals(Status.WAITING, actualToBookingDtoResult.getStatus());
        assertEquals(1L, actualToBookingDtoResult.getItemId());
        assertEquals("01:01", actualToBookingDtoResult.getStart().toLocalTime().toString());
        assertEquals(1L, actualToBookingDtoResult.getId());
        assertEquals("0001-01-01", actualToBookingDtoResult.getEnd().toLocalDate().toString());
        verify(booking).getEnd();
        verify(booking).getStart();
        verify(booking).getId();
        verify(booking).getStatus();
        verify(booking).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker((User) any());
        verify(booking).setEnd((LocalDateTime) any());
        verify(booking).setId(anyLong());
        verify(booking).setItem((Item) any());
        verify(booking).setStart((LocalDateTime) any());
        verify(booking).setStatus((Status) any());
    }

    @Test
    void testToBookingOutDto() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user2);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user1);
        item.setRequest(itemRequest);

        Booking booking = new Booking();
        booking.setBooker(user);
        booking.setEnd(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setStatus(Status.WAITING);
        BookingOutDto actualToBookingOutDtoResult = bookingMapperImpl.toBookingOutDto(booking);
        assertEquals(Status.WAITING, actualToBookingOutDtoResult.getStatus());
        assertEquals("01:01", actualToBookingOutDtoResult.getEnd().toLocalTime().toString());
        assertEquals("01:01", actualToBookingOutDtoResult.getStart().toLocalTime().toString());
        assertEquals(1L, actualToBookingOutDtoResult.getId());
        ItemDto item1 = actualToBookingOutDtoResult.getItem();
        assertEquals("The characteristics of someone or something", item1.getDescription());
        assertTrue(item1.getAvailable());
        assertEquals("Name", item1.getName());
        UserDto booker = actualToBookingOutDtoResult.getBooker();
        assertEquals("jane.doe@example.org", booker.getEmail());
        assertEquals(1L, booker.getId());
        assertEquals("Name", booker.getName());
        assertEquals(1L, item1.getId());
    }

    @Test
    void testToBookingOutDto2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user2);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user1);
        item.setRequest(itemRequest);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setId(1L);
        user4.setName("Name");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user4);

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user3);
        item1.setRequest(itemRequest1);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setId(1L);
        user5.setName("Name");
        Booking booking = mock(Booking.class);
        when(booking.getEnd()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(booking.getStart()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(booking.getId()).thenReturn(1L);
        when(booking.getStatus()).thenReturn(Status.WAITING);
        when(booking.getItem()).thenReturn(item1);
        when(booking.getBooker()).thenReturn(user5);
        doNothing().when(booking).setBooker((User) any());
        doNothing().when(booking).setEnd((LocalDateTime) any());
        doNothing().when(booking).setId(anyLong());
        doNothing().when(booking).setItem((Item) any());
        doNothing().when(booking).setStart((LocalDateTime) any());
        doNothing().when(booking).setStatus((Status) any());
        booking.setBooker(user);
        booking.setEnd(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setStatus(Status.WAITING);
        BookingOutDto actualToBookingOutDtoResult = bookingMapperImpl.toBookingOutDto(booking);
        assertEquals(Status.WAITING, actualToBookingOutDtoResult.getStatus());
        assertEquals("01:01", actualToBookingOutDtoResult.getEnd().toLocalTime().toString());
        assertEquals("01:01", actualToBookingOutDtoResult.getStart().toLocalTime().toString());
        assertEquals(1L, actualToBookingOutDtoResult.getId());
        ItemDto item2 = actualToBookingOutDtoResult.getItem();
        assertEquals("The characteristics of someone or something", item2.getDescription());
        assertTrue(item2.getAvailable());
        assertEquals("Name", item2.getName());
        UserDto booker = actualToBookingOutDtoResult.getBooker();
        assertEquals("jane.doe@example.org", booker.getEmail());
        assertEquals(1L, booker.getId());
        assertEquals("Name", booker.getName());
        assertEquals(1L, item2.getId());
        verify(booking).getEnd();
        verify(booking).getStart();
        verify(booking).getId();
        verify(booking).getStatus();
        verify(booking).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker((User) any());
        verify(booking).setEnd((LocalDateTime) any());
        verify(booking).setId(anyLong());
        verify(booking).setItem((Item) any());
        verify(booking).setStart((LocalDateTime) any());
        verify(booking).setStatus((Status) any());
    }

}

