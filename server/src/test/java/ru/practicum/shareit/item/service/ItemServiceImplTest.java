package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.mapper.BookingMapper;
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
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @MockBean
    private ItemRequestRepository itemRequestRepository;

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

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user1);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);
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

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user1);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);
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
    void testGetAllByUser() {
        when(itemRepository.findAllByOwnerId((Pageable) any(), anyLong())).thenReturn(new PageImpl<>(new ArrayList<>()));
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc((List<Long>) any())).thenReturn(new HashSet<>());
        assertTrue(itemServiceImpl.getAllByUser(1, 3, 1L).isEmpty());
        verify(itemRepository).findAllByOwnerId((Pageable) any(), anyLong());
        verify(bookingRepository).findAllByItemOwnerIdOrderByStartDesc(anyLong());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc((List<Long>) any());
    }

    @Test
    void testGetAllByUser2() {
        when(itemRepository.findAllByOwnerId((Pageable) any(), anyLong())).thenReturn(new PageImpl<>(new ArrayList<>()));
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc((List<Long>) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> itemServiceImpl.getAllByUser(1, 3, 1L));
        verify(itemRepository).findAllByOwnerId((Pageable) any(), anyLong());
        verify(bookingRepository).findAllByItemOwnerIdOrderByStartDesc(anyLong());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc((List<Long>) any());
    }

    @Test
    void testGetAllByUser3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user1);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        PageImpl<Item> pageImpl = new PageImpl<>(itemList);
        when(itemRepository.findAllByOwnerId((Pageable) any(), anyLong())).thenReturn(pageImpl);

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
        assertEquals(1, itemServiceImpl.getAllByUser(1, 3, 1L).size());
        verify(itemRepository).findAllByOwnerId((Pageable) any(), anyLong());
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

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user1);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);

        User user2 = new User();
        user2.setEmail("john.smith@example.org");
        user2.setId(2L);
        user2.setName("ru.practicum.shareit.user.model.User");

        User user3 = new User();
        user3.setEmail("john.smith@example.org");
        user3.setId(2L);
        user3.setName("ru.practicum.shareit.user.model.User");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("Description");
        itemRequest1.setId(2L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user3);

        Item item1 = new Item();
        item1.setAvailable(false);
        item1.setDescription("Description");
        item1.setId(2L);
        item1.setName("ru.practicum.shareit.item.model.Item");
        item1.setOwner(user2);
        item1.setRequest(itemRequest1);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item);
        PageImpl<Item> pageImpl = new PageImpl<>(itemList);
        when(itemRepository.findAllByOwnerId((Pageable) any(), anyLong())).thenReturn(pageImpl);

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
        assertEquals(2, itemServiceImpl.getAllByUser(1, 3, 1L).size());
        verify(itemRepository).findAllByOwnerId((Pageable) any(), anyLong());
        verify(itemMapper, atLeast(1)).toItemPlusDto((Item) any());
        verify(bookingRepository).findAllByItemOwnerIdOrderByStartDesc(anyLong());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc((List<Long>) any());
    }

    @Test
    void testCreate() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user1);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);
        when(itemRepository.save((Item) any())).thenReturn(item);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user3);

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user2);
        item1.setRequest(itemRequest1);
        ItemDto itemDto = new ItemDto();
        when(itemMapper.toItemDto((Item) any())).thenReturn(itemDto);
        when(itemMapper.toItem((ItemDto) any())).thenReturn(item1);

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setId(1L);
        user4.setName("Name");
        when(userMapper.toUser((UserDto) any())).thenReturn(user4);
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

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user1);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);
        when(itemRepository.save((Item) any())).thenReturn(item);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user3);

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user2);
        item1.setRequest(itemRequest1);
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

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user1);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);
        Optional<Item> ofResult = Optional.of(item);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user3);

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user2);
        item1.setRequest(itemRequest1);
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

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user1);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);
        Optional<Item> ofResult = Optional.of(item);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user3);

        Item item1 = new Item();
        item1.setAvailable(true);
        item1.setDescription("The characteristics of someone or something");
        item1.setId(1L);
        item1.setName("Name");
        item1.setOwner(user2);
        item1.setRequest(itemRequest1);
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
        when(itemRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(itemServiceImpl.search(1, 3, "Text").isEmpty());
        verify(itemRepository).findAll((Pageable) any());
    }

    @Test
    void testSearch2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user1);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        PageImpl<Item> pageImpl = new PageImpl<>(itemList);
        when(itemRepository.findAll((Pageable) any())).thenReturn(pageImpl);
        assertTrue(itemServiceImpl.search(1, 3, "Text").isEmpty());
        verify(itemRepository).findAll((Pageable) any());
    }

    @Test
    void testSearch3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user1);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);

        User user2 = new User();
        user2.setEmail("john.smith@example.org");
        user2.setId(2L);
        user2.setName("ru.practicum.shareit.user.model.User");

        User user3 = new User();
        user3.setEmail("john.smith@example.org");
        user3.setId(2L);
        user3.setName("ru.practicum.shareit.user.model.User");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("Description");
        itemRequest1.setId(2L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user3);

        Item item1 = new Item();
        item1.setAvailable(false);
        item1.setDescription("Description");
        item1.setId(2L);
        item1.setName("ru.practicum.shareit.item.model.Item");
        item1.setOwner(user2);
        item1.setRequest(itemRequest1);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item);
        PageImpl<Item> pageImpl = new PageImpl<>(itemList);
        when(itemRepository.findAll((Pageable) any())).thenReturn(pageImpl);
        assertTrue(itemServiceImpl.search(1, 3, "Text").isEmpty());
        verify(itemRepository).findAll((Pageable) any());
    }

    @Test
    void testCreateComment() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user1);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);
        Optional<Item> ofResult = Optional.of(item);
        when(itemRepository.findById((Long) any())).thenReturn(ofResult);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

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

        Comment comment = new Comment();
        comment.setAuthor(user2);
        comment.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setId(1L);
        comment.setItem(item1);
        comment.setText("Text");
        when(commentMapper.toComment((CommentDto) any())).thenReturn(comment);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setId(1L);
        user5.setName("Name");
        when(userMapper.toUser((UserDto) any())).thenReturn(user5);
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

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user1);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);
        Optional<Item> ofResult = Optional.of(item);
        when(itemRepository.findById((Long) any())).thenReturn(ofResult);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

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

        Comment comment = new Comment();
        comment.setAuthor(user2);
        comment.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setId(1L);
        comment.setItem(item1);
        comment.setText("Text");
        when(commentMapper.toComment((CommentDto) any())).thenReturn(comment);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setId(1L);
        user5.setName("Name");
        when(userMapper.toUser((UserDto) any())).thenReturn(user5);
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

