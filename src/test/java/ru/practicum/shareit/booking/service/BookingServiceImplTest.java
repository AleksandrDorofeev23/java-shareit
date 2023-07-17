package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.model.dto.BookingInDto;
import ru.practicum.shareit.booking.model.dto.BookingOutDto;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.AccessException;
import ru.practicum.shareit.exceptions.DateTimeException;
import ru.practicum.shareit.exceptions.StateException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookingServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BookingServiceImplTest {
    @MockBean
    private BookingMapper bookingMapper;

    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    private BookingServiceImpl bookingServiceImpl;

    @MockBean
    private ItemMapper itemMapper;

    @MockBean
    private ItemService itemService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    @Test
    void testGetById() {
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
        Optional<Booking> ofResult = Optional.of(booking);
        when(bookingRepository.findById((Long) any())).thenReturn(ofResult);
        BookingOutDto bookingOutDto = new BookingOutDto();
        when(bookingMapper.toBookingOutDto((Booking) any())).thenReturn(bookingOutDto);
        assertSame(bookingOutDto, bookingServiceImpl.getById(1L, 1L));
        verify(bookingRepository).findById((Long) any());
        verify(bookingMapper).toBookingOutDto((Booking) any());
    }

    @Test
    void testGetById2() {
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
        Optional<Booking> ofResult = Optional.of(booking);
        when(bookingRepository.findById((Long) any())).thenReturn(ofResult);
        when(bookingMapper.toBookingOutDto((Booking) any())).thenThrow(new AccessException("foo"));
        assertThrows(AccessException.class, () -> bookingServiceImpl.getById(1L, 1L));
        verify(bookingRepository).findById((Long) any());
        verify(bookingMapper).toBookingOutDto((Booking) any());
    }

    @Test
    void testGetById3() {
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
        User user5 = mock(User.class);
        when(user5.getId()).thenReturn(-1L);
        doNothing().when(user5).setEmail((String) any());
        doNothing().when(user5).setId(anyLong());
        doNothing().when(user5).setName((String) any());
        user5.setEmail("jane.doe@example.org");
        user5.setId(1L);
        user5.setName("Name");
        Item item1 = mock(Item.class);
        when(item1.getOwner()).thenReturn(user5);
        doNothing().when(item1).setAvailable((Boolean) any());
        doNothing().when(item1).setDescription((String) any());
        doNothing().when(item1).setId(anyLong());
        doNothing().when(item1).setName((String) any());
        doNothing().when(item1).setOwner((User) any());
        doNothing().when(item1).setRequest((ItemRequest) any());
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user3);
        item1.setRequest(itemRequest1);

        User user6 = new User();
        user6.setEmail("jane.doe@example.org");
        user6.setId(1L);
        user6.setName("Name");
        Booking booking = mock(Booking.class);
        when(booking.getBooker()).thenReturn(user6);
        when(booking.getItem()).thenReturn(item1);
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
        Optional<Booking> ofResult = Optional.of(booking);
        when(bookingRepository.findById((Long) any())).thenReturn(ofResult);
        BookingOutDto bookingOutDto = new BookingOutDto();
        when(bookingMapper.toBookingOutDto((Booking) any())).thenReturn(bookingOutDto);
        assertSame(bookingOutDto, bookingServiceImpl.getById(1L, 1L));
        verify(bookingRepository).findById((Long) any());
        verify(booking).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker((User) any());
        verify(booking).setEnd((LocalDateTime) any());
        verify(booking).setId(anyLong());
        verify(booking).setItem((Item) any());
        verify(booking).setStart((LocalDateTime) any());
        verify(booking).setStatus((Status) any());
        verify(item1).getOwner();
        verify(item1).setAvailable((Boolean) any());
        verify(item1).setDescription((String) any());
        verify(item1).setId(anyLong());
        verify(item1).setName((String) any());
        verify(item1).setOwner((User) any());
        verify(item1).setRequest((ItemRequest) any());
        verify(user5).getId();
        verify(user5).setEmail((String) any());
        verify(user5).setId(anyLong());
        verify(user5).setName((String) any());
        verify(bookingMapper).toBookingOutDto((Booking) any());
    }

    @Test
    void testGetByUser() {
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toUser((UserDto) any())).thenReturn(user);
        assertThrows(StateException.class, () -> bookingServiceImpl.getByUser(1, 3, 1L, "MD"));
        verify(userService).getById(anyLong());
        verify(userMapper).toUser((UserDto) any());
    }

    @Test
    void testGetByUser2() {
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(userMapper.toUser((UserDto) any())).thenThrow(new AccessException("Такого типа нет."));
        assertThrows(AccessException.class, () -> bookingServiceImpl.getByUser(1, 3, 1L, "MD"));
        verify(userService).getById(anyLong());
        verify(userMapper).toUser((UserDto) any());
    }

    @Test
    void testGetByOwner() {
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toUser((UserDto) any())).thenReturn(user);
        assertThrows(StateException.class, () -> bookingServiceImpl.getByOwner(1, 3, 1L, "MD"));
        verify(userService).getById(anyLong());
        verify(userMapper).toUser((UserDto) any());
    }

    @Test
    void testGetByOwner2() {
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(userMapper.toUser((UserDto) any())).thenThrow(new AccessException("Такого типа нет."));
        assertThrows(AccessException.class, () -> bookingServiceImpl.getByOwner(1, 3, 1L, "MD"));
        verify(userService).getById(anyLong());
        verify(userMapper).toUser((UserDto) any());
    }

    @Test
    void testCreate() {
        LocalDateTime start = LocalDateTime.of(1, 1, 1, 1, 1);
        assertThrows(DateTimeException.class,
                () -> bookingServiceImpl.create(new BookingInDto(1L, start, LocalDateTime.of(1, 1, 1, 1, 1)), 1L));
    }

    @Test
    void testCreate2() {
        BookingInDto bookingInDto = mock(BookingInDto.class);
        when(bookingInDto.getEnd()).thenReturn(LocalDateTime.of(0, 1, 1, 1, 1));
        when(bookingInDto.getStart()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        assertThrows(DateTimeException.class, () -> bookingServiceImpl.create(bookingInDto, 1L));
        verify(bookingInDto).getEnd();
        verify(bookingInDto, atLeast(1)).getStart();
    }

    @Test
    void testConfirm() {
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
        Optional<Booking> ofResult = Optional.of(booking);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setId(1L);
        user4.setName("Name");

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setId(1L);
        user5.setName("Name");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user5);

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user4);
        item1.setRequest(itemRequest1);

        Booking booking1 = new Booking();
        booking1.setBooker(user3);
        booking1.setEnd(LocalDateTime.of(1, 1, 1, 1, 1));
        booking1.setId(1L);
        booking1.setItem(item1);
        booking1.setStart(LocalDateTime.of(1, 1, 1, 1, 1));
        booking1.setStatus(Status.WAITING);
        when(bookingRepository.save((Booking) any())).thenReturn(booking1);
        when(bookingRepository.findById((Long) any())).thenReturn(ofResult);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        BookingOutDto bookingOutDto = new BookingOutDto();
        when(bookingMapper.toBookingOutDto((Booking) any())).thenReturn(bookingOutDto);

        User user6 = new User();
        user6.setEmail("jane.doe@example.org");
        user6.setId(1L);
        user6.setName("Name");
        when(userMapper.toUser((UserDto) any())).thenReturn(user6);
        assertSame(bookingOutDto, bookingServiceImpl.confirm(1L, 1L, true));
        verify(bookingRepository).save((Booking) any());
        verify(bookingRepository).findById((Long) any());
        verify(userService).getById(anyLong());
        verify(bookingMapper).toBookingOutDto((Booking) any());
        verify(userMapper).toUser((UserDto) any());
    }

    @Test
    void testConfirm2() {
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
        Optional<Booking> ofResult = Optional.of(booking);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setId(1L);
        user4.setName("Name");

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setId(1L);
        user5.setName("Name");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user5);

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user4);
        item1.setRequest(itemRequest1);

        Booking booking1 = new Booking();
        booking1.setBooker(user3);
        booking1.setEnd(LocalDateTime.of(1, 1, 1, 1, 1));
        booking1.setId(1L);
        booking1.setItem(item1);
        booking1.setStart(LocalDateTime.of(1, 1, 1, 1, 1));
        booking1.setStatus(Status.WAITING);
        when(bookingRepository.save((Booking) any())).thenReturn(booking1);
        when(bookingRepository.findById((Long) any())).thenReturn(ofResult);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(bookingMapper.toBookingOutDto((Booking) any())).thenThrow(new AccessException("foo"));

        User user6 = new User();
        user6.setEmail("jane.doe@example.org");
        user6.setId(1L);
        user6.setName("Name");
        when(userMapper.toUser((UserDto) any())).thenReturn(user6);
        assertThrows(AccessException.class, () -> bookingServiceImpl.confirm(1L, 1L, true));
        verify(bookingRepository).save((Booking) any());
        verify(bookingRepository).findById((Long) any());
        verify(userService).getById(anyLong());
        verify(bookingMapper).toBookingOutDto((Booking) any());
        verify(userMapper).toUser((UserDto) any());
    }
}

