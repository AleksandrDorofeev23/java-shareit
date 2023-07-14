package ru.practicum.shareit.item.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidException;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemPlusDto;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

@ContextConfiguration(classes = {ItemServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ItemServiceImplTest {
    @MockBean
    private BookingMapper bookingMapper;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private CommentMapper commentMapper;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private ItemMapper itemMapper;

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

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

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        Optional<Item> ofResult = Optional.of(item);
        when(itemRepository.findById((Long) any())).thenReturn(ofResult);

        ItemPlusDto itemPlusDto = new ItemPlusDto();
        itemPlusDto.setAvailable(true);
        itemPlusDto.setComments(new HashSet<>());
        itemPlusDto.setDescription("The characteristics of someone or something");
        itemPlusDto.setId(1L);
        itemPlusDto.setLastBooking(new BookingDto());
        itemPlusDto.setName("Name");
        itemPlusDto.setNextBooking(new BookingDto());
        itemPlusDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toItemPlusDto((Item) any())).thenReturn(itemPlusDto);
        when(bookingRepository.findByItem_IdAndStatusOrderByStartDesc(anyLong(), (Status) any()))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findByItem_IdOrderByCreatedDesc(anyLong())).thenReturn(new HashSet<>());
        ItemPlusDto actualById = itemServiceImpl.getById(1L, 1L);
        assertSame(itemPlusDto, actualById);
        assertNull(actualById.getNextBooking());
        assertTrue(actualById.getComments().isEmpty());
        assertNull(actualById.getLastBooking());
        verify(itemRepository).findById((Long) any());
        verify(itemMapper).toItemPlusDto((Item) any());
        verify(bookingRepository).findByItem_IdAndStatusOrderByStartDesc(anyLong(), (Status) any());
        verify(commentRepository).findByItem_IdOrderByCreatedDesc(anyLong());
    }

    @Test
    void testGetById2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        Optional<Item> ofResult = Optional.of(item);
        when(itemRepository.findById((Long) any())).thenReturn(ofResult);

        ItemPlusDto itemPlusDto = new ItemPlusDto();
        itemPlusDto.setAvailable(true);
        itemPlusDto.setComments(new HashSet<>());
        itemPlusDto.setDescription("The characteristics of someone or something");
        itemPlusDto.setId(1L);
        itemPlusDto.setLastBooking(new BookingDto());
        itemPlusDto.setName("Name");
        itemPlusDto.setNextBooking(new BookingDto());
        itemPlusDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toItemPlusDto((Item) any())).thenReturn(itemPlusDto);
        when(bookingRepository.findByItem_IdAndStatusOrderByStartDesc(anyLong(), (Status) any()))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findByItem_IdOrderByCreatedDesc(anyLong())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> itemServiceImpl.getById(1L, 1L));
        verify(itemRepository).findById((Long) any());
        verify(itemMapper).toItemPlusDto((Item) any());
        verify(bookingRepository).findByItem_IdAndStatusOrderByStartDesc(anyLong(), (Status) any());
        verify(commentRepository).findByItem_IdOrderByCreatedDesc(anyLong());
    }

    @Test
    void testGetById3() {
        when(itemRepository.findById((Long) any())).thenReturn(Optional.empty());

        ItemPlusDto itemPlusDto = new ItemPlusDto();
        itemPlusDto.setAvailable(true);
        itemPlusDto.setComments(new HashSet<>());
        itemPlusDto.setDescription("The characteristics of someone or something");
        itemPlusDto.setId(1L);
        itemPlusDto.setLastBooking(new BookingDto());
        itemPlusDto.setName("Name");
        itemPlusDto.setNextBooking(new BookingDto());
        itemPlusDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toItemPlusDto((Item) any())).thenReturn(itemPlusDto);
        when(bookingRepository.findByItem_IdAndStatusOrderByStartDesc(anyLong(), (Status) any()))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findByItem_IdOrderByCreatedDesc(anyLong())).thenReturn(new HashSet<>());
        assertThrows(NotFoundException.class, () -> itemServiceImpl.getById(1L, 1L));
        verify(itemRepository).findById((Long) any());
    }

    @Test
    void testGetById4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        Optional<Item> ofResult = Optional.of(item);
        when(itemRepository.findById((Long) any())).thenReturn(ofResult);
        ItemPlusDto itemPlusDto = mock(ItemPlusDto.class);
        when(itemPlusDto.getOwner()).thenReturn(new UserDto(2L, "Name", "jane.doe@example.org"));
        doNothing().when(itemPlusDto).setAvailable((Boolean) any());
        doNothing().when(itemPlusDto).setComments((Set<CommentDto>) any());
        doNothing().when(itemPlusDto).setDescription((String) any());
        doNothing().when(itemPlusDto).setId(anyLong());
        doNothing().when(itemPlusDto).setLastBooking((BookingDto) any());
        doNothing().when(itemPlusDto).setName((String) any());
        doNothing().when(itemPlusDto).setNextBooking((BookingDto) any());
        doNothing().when(itemPlusDto).setOwner((UserDto) any());
        itemPlusDto.setAvailable(true);
        itemPlusDto.setComments(new HashSet<>());
        itemPlusDto.setDescription("The characteristics of someone or something");
        itemPlusDto.setId(1L);
        itemPlusDto.setLastBooking(new BookingDto());
        itemPlusDto.setName("Name");
        itemPlusDto.setNextBooking(new BookingDto());
        itemPlusDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toItemPlusDto((Item) any())).thenReturn(itemPlusDto);
        when(bookingRepository.findByItem_IdAndStatusOrderByStartDesc(anyLong(), (Status) any()))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findByItem_IdOrderByCreatedDesc(anyLong())).thenReturn(new HashSet<>());
        itemServiceImpl.getById(1L, 1L);
        verify(itemRepository).findById((Long) any());
        verify(itemMapper).toItemPlusDto((Item) any());
        verify(itemPlusDto).getOwner();
        verify(itemPlusDto).setAvailable((Boolean) any());
        verify(itemPlusDto, atLeast(1)).setComments((Set<CommentDto>) any());
        verify(itemPlusDto).setDescription((String) any());
        verify(itemPlusDto).setId(anyLong());
        verify(itemPlusDto).setLastBooking((BookingDto) any());
        verify(itemPlusDto).setName((String) any());
        verify(itemPlusDto).setNextBooking((BookingDto) any());
        verify(itemPlusDto).setOwner((UserDto) any());
        verify(bookingRepository).findByItem_IdAndStatusOrderByStartDesc(anyLong(), (Status) any());
        verify(commentRepository).findByItem_IdOrderByCreatedDesc(anyLong());
    }

    @Test
    void testGetById5() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        Optional<Item> ofResult = Optional.of(item);
        when(itemRepository.findById((Long) any())).thenReturn(ofResult);
        ItemPlusDto itemPlusDto = mock(ItemPlusDto.class);
        when(itemPlusDto.getId()).thenReturn(1L);
        when(itemPlusDto.getOwner()).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        doNothing().when(itemPlusDto).setAvailable((Boolean) any());
        doNothing().when(itemPlusDto).setComments((Set<CommentDto>) any());
        doNothing().when(itemPlusDto).setDescription((String) any());
        doNothing().when(itemPlusDto).setId(anyLong());
        doNothing().when(itemPlusDto).setLastBooking((BookingDto) any());
        doNothing().when(itemPlusDto).setName((String) any());
        doNothing().when(itemPlusDto).setNextBooking((BookingDto) any());
        doNothing().when(itemPlusDto).setOwner((UserDto) any());
        itemPlusDto.setAvailable(true);
        itemPlusDto.setComments(new HashSet<>());
        itemPlusDto.setDescription("The characteristics of someone or something");
        itemPlusDto.setId(1L);
        itemPlusDto.setLastBooking(new BookingDto());
        itemPlusDto.setName("Name");
        itemPlusDto.setNextBooking(new BookingDto());
        itemPlusDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toItemPlusDto((Item) any())).thenReturn(itemPlusDto);
        when(bookingMapper.toBookingDto((Booking) any())).thenReturn(new BookingDto());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user2);

        Booking booking = new Booking();
        booking.setBooker(user1);
        booking.setEnd(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setId(1L);
        booking.setItem(item1);
        booking.setStart(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setStatus(Status.WAITING);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByItem_IdAndStatusOrderByStartDesc(anyLong(), (Status) any())).thenReturn(bookingList);
        when(commentRepository.findByItem_IdOrderByCreatedDesc(anyLong())).thenReturn(new HashSet<>());
        itemServiceImpl.getById(1L, 1L);
        verify(itemRepository).findById((Long) any());
        verify(itemMapper).toItemPlusDto((Item) any());
        verify(itemPlusDto, atLeast(1)).getId();
        verify(itemPlusDto).getOwner();
        verify(itemPlusDto).setAvailable((Boolean) any());
        verify(itemPlusDto, atLeast(1)).setComments((Set<CommentDto>) any());
        verify(itemPlusDto).setDescription((String) any());
        verify(itemPlusDto).setId(anyLong());
        verify(itemPlusDto, atLeast(1)).setLastBooking((BookingDto) any());
        verify(itemPlusDto).setName((String) any());
        verify(itemPlusDto, atLeast(1)).setNextBooking((BookingDto) any());
        verify(itemPlusDto).setOwner((UserDto) any());
        verify(bookingMapper).toBookingDto((Booking) any());
        verify(bookingRepository).findByItem_IdAndStatusOrderByStartDesc(anyLong(), (Status) any());
        verify(commentRepository).findByItem_IdOrderByCreatedDesc(anyLong());
    }

    @Test
    void testGetById6() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        Optional<Item> ofResult = Optional.of(item);
        when(itemRepository.findById((Long) any())).thenReturn(ofResult);
        ItemPlusDto itemPlusDto = mock(ItemPlusDto.class);
        when(itemPlusDto.getId()).thenThrow(new RuntimeException());
        when(itemPlusDto.getOwner()).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        doNothing().when(itemPlusDto).setAvailable((Boolean) any());
        doNothing().when(itemPlusDto).setComments((Set<CommentDto>) any());
        doNothing().when(itemPlusDto).setDescription((String) any());
        doNothing().when(itemPlusDto).setId(anyLong());
        doNothing().when(itemPlusDto).setLastBooking((BookingDto) any());
        doNothing().when(itemPlusDto).setName((String) any());
        doNothing().when(itemPlusDto).setNextBooking((BookingDto) any());
        doNothing().when(itemPlusDto).setOwner((UserDto) any());
        itemPlusDto.setAvailable(true);
        itemPlusDto.setComments(new HashSet<>());
        itemPlusDto.setDescription("The characteristics of someone or something");
        itemPlusDto.setId(1L);
        itemPlusDto.setLastBooking(new BookingDto());
        itemPlusDto.setName("Name");
        itemPlusDto.setNextBooking(new BookingDto());
        itemPlusDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toItemPlusDto((Item) any())).thenReturn(itemPlusDto);
        when(bookingMapper.toBookingDto((Booking) any())).thenReturn(new BookingDto());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user2);

        Booking booking = new Booking();
        booking.setBooker(user1);
        booking.setEnd(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setId(1L);
        booking.setItem(item1);
        booking.setStart(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setStatus(Status.WAITING);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByItem_IdAndStatusOrderByStartDesc(anyLong(), (Status) any())).thenReturn(bookingList);
        when(commentRepository.findByItem_IdOrderByCreatedDesc(anyLong())).thenReturn(new HashSet<>());
        assertThrows(RuntimeException.class, () -> itemServiceImpl.getById(1L, 1L));
        verify(itemRepository).findById((Long) any());
        verify(itemMapper).toItemPlusDto((Item) any());
        verify(itemPlusDto).getId();
        verify(itemPlusDto).getOwner();
        verify(itemPlusDto).setAvailable((Boolean) any());
        verify(itemPlusDto).setComments((Set<CommentDto>) any());
        verify(itemPlusDto).setDescription((String) any());
        verify(itemPlusDto).setId(anyLong());
        verify(itemPlusDto).setLastBooking((BookingDto) any());
        verify(itemPlusDto).setName((String) any());
        verify(itemPlusDto).setNextBooking((BookingDto) any());
        verify(itemPlusDto).setOwner((UserDto) any());
        verify(bookingMapper).toBookingDto((Booking) any());
        verify(bookingRepository).findByItem_IdAndStatusOrderByStartDesc(anyLong(), (Status) any());
        verify(commentRepository).findByItem_IdOrderByCreatedDesc(anyLong());
    }

    @Test
    void testGetAllByUser() {
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc((List<Long>) any())).thenReturn(new HashSet<>());
        assertTrue(itemServiceImpl.getAllByUser(1L).isEmpty());
        verify(itemRepository).findAll();
        verify(bookingRepository).findAllByItemOwnerIdOrderByStartDesc(anyLong());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc((List<Long>) any());
    }

    @Test
    void testGetAllByUser2() {
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc((List<Long>) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> itemServiceImpl.getAllByUser(1L));
        verify(itemRepository).findAll();
        verify(bookingRepository).findAllByItemOwnerIdOrderByStartDesc(anyLong());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc((List<Long>) any());
    }

    @Test
    void testGetAllByUser3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);

        ItemPlusDto itemPlusDto = new ItemPlusDto();
        itemPlusDto.setAvailable(true);
        itemPlusDto.setComments(new HashSet<>());
        itemPlusDto.setDescription("The characteristics of someone or something");
        itemPlusDto.setId(1L);
        itemPlusDto.setLastBooking(new BookingDto());
        itemPlusDto.setName("Name");
        itemPlusDto.setNextBooking(new BookingDto());
        itemPlusDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toItemPlusDto((Item) any())).thenReturn(itemPlusDto);
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc((List<Long>) any())).thenReturn(new HashSet<>());
        assertEquals(1, itemServiceImpl.getAllByUser(1L).size());
        verify(itemRepository).findAll();
        verify(itemMapper).toItemPlusDto((Item) any());
        verify(bookingRepository).findAllByItemOwnerIdOrderByStartDesc(anyLong());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc((List<Long>) any());
    }

    @Test
    void testGetAllByUser4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);

        User user1 = new User();
        user1.setEmail("john.smith@example.org");
        user1.setId(2L);
        user1.setName("ru.practicum.shareit.user.model.User");

        Item item1 = new Item();
        item1.setAvailable(false);
        item1.setDescription("Description");
        item1.setId(2L);
        item1.setName("ru.practicum.shareit.item.model.Item");
        item1.setOwner(user1);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);

        ItemPlusDto itemPlusDto = new ItemPlusDto();
        itemPlusDto.setAvailable(true);
        itemPlusDto.setComments(new HashSet<>());
        itemPlusDto.setDescription("The characteristics of someone or something");
        itemPlusDto.setId(1L);
        itemPlusDto.setLastBooking(new BookingDto());
        itemPlusDto.setName("Name");
        itemPlusDto.setNextBooking(new BookingDto());
        itemPlusDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toItemPlusDto((Item) any())).thenReturn(itemPlusDto);
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc((List<Long>) any())).thenReturn(new HashSet<>());
        assertEquals(1, itemServiceImpl.getAllByUser(1L).size());
        verify(itemRepository).findAll();
        verify(itemMapper).toItemPlusDto((Item) any());
        verify(bookingRepository).findAllByItemOwnerIdOrderByStartDesc(anyLong());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc((List<Long>) any());
    }

    @Test
    void testGetAllByUser5() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Item item = mock(Item.class);
        when(item.getOwner()).thenReturn(user1);
        doNothing().when(item).setAvailable((Boolean) any());
        doNothing().when(item).setDescription((String) any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName((String) any());
        doNothing().when(item).setOwner((User) any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);

        ItemPlusDto itemPlusDto = new ItemPlusDto();
        itemPlusDto.setAvailable(true);
        itemPlusDto.setComments(new HashSet<>());
        itemPlusDto.setDescription("The characteristics of someone or something");
        itemPlusDto.setId(1L);
        itemPlusDto.setLastBooking(new BookingDto());
        itemPlusDto.setName("Name");
        itemPlusDto.setNextBooking(new BookingDto());
        itemPlusDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toItemPlusDto((Item) any())).thenReturn(itemPlusDto);
        when(bookingMapper.toBookingDto((Booking) any())).thenReturn(new BookingDto());

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user3);

        Booking booking = new Booking();
        booking.setBooker(user2);
        booking.setEnd(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setId(1L);
        booking.setItem(item1);
        booking.setStart(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setStatus(Status.WAITING);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(bookingList);
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc((List<Long>) any())).thenReturn(new HashSet<>());
        assertEquals(1, itemServiceImpl.getAllByUser(1L).size());
        verify(itemRepository).findAll();
        verify(item).getOwner();
        verify(item).setAvailable((Boolean) any());
        verify(item).setDescription((String) any());
        verify(item).setId(anyLong());
        verify(item).setName((String) any());
        verify(item).setOwner((User) any());
        verify(itemMapper).toItemPlusDto((Item) any());
        verify(bookingMapper).toBookingDto((Booking) any());
        verify(bookingRepository).findAllByItemOwnerIdOrderByStartDesc(anyLong());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc((List<Long>) any());
    }

    @Test
    void testGetAllByUser6() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Item item = mock(Item.class);
        when(item.getOwner()).thenReturn(user1);
        doNothing().when(item).setAvailable((Boolean) any());
        doNothing().when(item).setDescription((String) any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName((String) any());
        doNothing().when(item).setOwner((User) any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        Item item1 = new Item();
        item1.setAvailable(false);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user2);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);

        ItemPlusDto itemPlusDto = new ItemPlusDto();
        itemPlusDto.setAvailable(true);
        itemPlusDto.setComments(new HashSet<>());
        itemPlusDto.setDescription("The characteristics of someone or something");
        itemPlusDto.setId(1L);
        itemPlusDto.setLastBooking(new BookingDto());
        itemPlusDto.setName("Name");
        itemPlusDto.setNextBooking(new BookingDto());
        itemPlusDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toItemPlusDto((Item) any())).thenReturn(itemPlusDto);
        when(bookingMapper.toBookingDto((Booking) any())).thenReturn(new BookingDto());

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setId(1L);
        user4.setName("Name");

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(user4);

        Booking booking = new Booking();
        booking.setBooker(user3);
        booking.setEnd(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setId(1L);
        booking.setItem(item2);
        booking.setStart(LocalDateTime.of(1, 1, 1, 1, 1));
        booking.setStatus(Status.WAITING);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(bookingList);
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc((List<Long>) any())).thenReturn(new HashSet<>());
        assertEquals(2, itemServiceImpl.getAllByUser(1L).size());
        verify(itemRepository).findAll();
        verify(item).getOwner();
        verify(item).setAvailable((Boolean) any());
        verify(item).setDescription((String) any());
        verify(item).setId(anyLong());
        verify(item).setName((String) any());
        verify(item).setOwner((User) any());
        verify(itemMapper, atLeast(1)).toItemPlusDto((Item) any());
        verify(bookingMapper).toBookingDto((Booking) any());
        verify(bookingRepository).findAllByItemOwnerIdOrderByStartDesc(anyLong());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc((List<Long>) any());
    }

    @Test
    void testCreate() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        when(itemRepository.save((Item) any())).thenReturn(item);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user1);
        ItemDto itemDto = new ItemDto();
        when(itemMapper.toItemDto((Item) any())).thenReturn(itemDto);
        when(itemMapper.toItem((ItemDto) any())).thenReturn(item1);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        when(userMapper.toUser((UserDto) any())).thenReturn(user2);
        assertSame(itemDto, itemServiceImpl.create(new ItemDto(), 1L));
        verify(itemRepository).save((Item) any());
        verify(userService).getById(anyLong());
        verify(itemMapper).toItem((ItemDto) any());
        verify(itemMapper).toItemDto((Item) any());
        verify(userMapper).toUser((UserDto) any());
    }

    @Test
    void testCreate2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        when(itemRepository.save((Item) any())).thenReturn(item);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user1);
        when(itemMapper.toItemDto((Item) any())).thenReturn(new ItemDto());
        when(itemMapper.toItem((ItemDto) any())).thenReturn(item1);
        when(userMapper.toUser((UserDto) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> itemServiceImpl.create(new ItemDto(), 1L));
        verify(userService).getById(anyLong());
        verify(itemMapper).toItem((ItemDto) any());
        verify(userMapper).toUser((UserDto) any());
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        Optional<Item> ofResult = Optional.of(item);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user1);
        when(itemRepository.save((Item) any())).thenReturn(item1);
        when(itemRepository.findById((Long) any())).thenReturn(ofResult);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        ItemDto itemDto = new ItemDto();
        when(itemMapper.toItemDto((Item) any())).thenReturn(itemDto);
        assertSame(itemDto, itemServiceImpl.update(new ItemDto(), 1L, 1L));
        verify(itemRepository).save((Item) any());
        verify(itemRepository).findById((Long) any());
        verify(userService).getById(anyLong());
        verify(itemMapper).toItemDto((Item) any());
    }

    @Test
    void testUpdate2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        Optional<Item> ofResult = Optional.of(item);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user1);
        when(itemRepository.save((Item) any())).thenReturn(item1);
        when(itemRepository.findById((Long) any())).thenReturn(ofResult);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toItemDto((Item) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> itemServiceImpl.update(new ItemDto(), 1L, 1L));
        verify(itemRepository).save((Item) any());
        verify(itemRepository).findById((Long) any());
        verify(userService).getById(anyLong());
        verify(itemMapper).toItemDto((Item) any());
    }

    @Test
    void testUpdate3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn(-1L);
        doNothing().when(user1).setEmail((String) any());
        doNothing().when(user1).setId(anyLong());
        doNothing().when(user1).setName((String) any());
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Item item = mock(Item.class);
        when(item.getOwner()).thenReturn(user1);
        doNothing().when(item).setAvailable((Boolean) any());
        doNothing().when(item).setDescription((String) any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName((String) any());
        doNothing().when(item).setOwner((User) any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        Optional<Item> ofResult = Optional.of(item);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user2);
        when(itemRepository.save((Item) any())).thenReturn(item1);
        when(itemRepository.findById((Long) any())).thenReturn(ofResult);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toItemDto((Item) any())).thenReturn(new ItemDto());
        assertThrows(NotFoundException.class, () -> itemServiceImpl.update(new ItemDto(), 1L, 1L));
        verify(itemRepository).findById((Long) any());
        verify(item).getOwner();
        verify(item).setAvailable((Boolean) any());
        verify(item).setDescription((String) any());
        verify(item).setId(anyLong());
        verify(item).setName((String) any());
        verify(item).setOwner((User) any());
        verify(user1).getId();
        verify(user1).setEmail((String) any());
        verify(user1).setId(anyLong());
        verify(user1).setName((String) any());
        verify(userService).getById(anyLong());
    }

    @Test
    void testDeleteById() {
        doNothing().when(itemRepository).deleteById((Long) any());
        itemServiceImpl.deleteById(1L);
        verify(itemRepository).deleteById((Long) any());
    }

    @Test
    void testDeleteById2() {
        doThrow(new RuntimeException()).when(itemRepository).deleteById((Long) any());
        assertThrows(RuntimeException.class, () -> itemServiceImpl.deleteById(1L));
        verify(itemRepository).deleteById((Long) any());
    }

    @Test
    void testSearch() {
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(itemServiceImpl.search("Text").isEmpty());
        verify(itemRepository).findAll();
    }

    @Test
    void testSearch2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);
        assertTrue(itemServiceImpl.search("Text").isEmpty());
        verify(itemRepository).findAll();
    }

    @Test
    void testSearch3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);

        User user1 = new User();
        user1.setEmail("john.smith@example.org");
        user1.setId(2L);
        user1.setName("ru.practicum.shareit.user.model.User");

        Item item1 = new Item();
        item1.setAvailable(false);
        item1.setDescription("Description");
        item1.setId(2L);
        item1.setName("ru.practicum.shareit.item.model.Item");
        item1.setOwner(user1);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);
        assertTrue(itemServiceImpl.search("Text").isEmpty());
        verify(itemRepository).findAll();
    }

    @Test
    void testSearch4() {
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(itemServiceImpl.search("").isEmpty());
    }

    @Test
    void testSearch5() {
        when(itemRepository.findAll()).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> itemServiceImpl.search("Text"));
        verify(itemRepository).findAll();
    }

    @Test
    void testSearch6() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Item item = mock(Item.class);
        when(item.getName()).thenThrow(new RuntimeException());
        when(item.getDescription()).thenReturn("The characteristics of someone or something");
        when(item.getAvailable()).thenReturn(true);
        doNothing().when(item).setAvailable((Boolean) any());
        doNothing().when(item).setDescription((String) any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName((String) any());
        doNothing().when(item).setOwner((User) any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);
        assertThrows(RuntimeException.class, () -> itemServiceImpl.search("Text"));
        verify(itemRepository).findAll();
        verify(item).getAvailable();
        verify(item).getDescription();
        verify(item).getName();
        verify(item).setAvailable((Boolean) any());
        verify(item).setDescription((String) any());
        verify(item).setId(anyLong());
        verify(item).setName((String) any());
        verify(item).setOwner((User) any());
    }

    @Test
    void testCreateComment() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        Optional<Item> ofResult = Optional.of(item);
        when(itemRepository.findById((Long) any())).thenReturn(ofResult);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user2);

        Comment comment = new Comment();
        comment.setAuthor(user1);
        comment.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setId(1L);
        comment.setItem(item1);
        comment.setText("Text");
        when(commentMapper.toComment((CommentDto) any())).thenReturn(comment);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");
        when(userMapper.toUser((UserDto) any())).thenReturn(user3);
        when(bookingRepository.findAllByItemIdAndBookerIdAndStatusOrderByStartDesc(anyLong(), anyLong(), (Status) any()))
                .thenReturn(Optional.of(new ArrayList<>()));

        CommentDto commentDto = new CommentDto();
        commentDto.setAuthorName("JaneDoe");
        commentDto.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        commentDto.setId(1L);
        commentDto.setText("Text");
        assertThrows(ValidException.class, () -> itemServiceImpl.createComment(1L, 1L, commentDto));
        verify(itemRepository).findById((Long) any());
        verify(userService).getById(anyLong());
        verify(commentMapper).toComment((CommentDto) any());
        verify(userMapper).toUser((UserDto) any());
        verify(bookingRepository).findAllByItemIdAndBookerIdAndStatusOrderByStartDesc(anyLong(), anyLong(),
                (Status) any());
    }

    @Test
    void testCreateComment2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        Optional<Item> ofResult = Optional.of(item);
        when(itemRepository.findById((Long) any())).thenReturn(ofResult);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user2);

        Comment comment = new Comment();
        comment.setAuthor(user1);
        comment.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setId(1L);
        comment.setItem(item1);
        comment.setText("Text");
        when(commentMapper.toComment((CommentDto) any())).thenReturn(comment);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");
        when(userMapper.toUser((UserDto) any())).thenReturn(user3);
        when(bookingRepository.findAllByItemIdAndBookerIdAndStatusOrderByStartDesc(anyLong(), anyLong(), (Status) any()))
                .thenThrow(new RuntimeException());

        CommentDto commentDto = new CommentDto();
        commentDto.setAuthorName("JaneDoe");
        commentDto.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        commentDto.setId(1L);
        commentDto.setText("Text");
        assertThrows(RuntimeException.class, () -> itemServiceImpl.createComment(1L, 1L, commentDto));
        verify(itemRepository).findById((Long) any());
        verify(userService).getById(anyLong());
        verify(commentMapper).toComment((CommentDto) any());
        verify(userMapper).toUser((UserDto) any());
        verify(bookingRepository).findAllByItemIdAndBookerIdAndStatusOrderByStartDesc(anyLong(), anyLong(),
                (Status) any());
    }

}

