package ru.practicum.shareit.request.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.NotFoundUserException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ItemRequestServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ItemRequestServiceImplTest {
    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private ItemRequestMapper itemRequestMapper;

    @MockBean
    private ItemRequestRepository itemRequestRepository;

    @Autowired
    private ItemRequestServiceImpl itemRequestServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testCreate() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);
        when(itemRequestRepository.save((ItemRequest) any())).thenReturn(itemRequest);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user2);
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(itemRequestDto);
        when(itemRequestMapper.toItemRequest((ItemRequestDto) any())).thenReturn(itemRequest1);
        assertSame(itemRequestDto, itemRequestServiceImpl.create(new ItemRequestDto(), 1L));
        verify(itemRequestRepository).save((ItemRequest) any());
        verify(userRepository).findById((Long) any());
        verify(itemRequestMapper).toItemRequest((ItemRequestDto) any());
        verify(itemRequestMapper).toItemRequestDto((ItemRequest) any());
    }

    @Test
    void testCreate2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);
        when(itemRequestRepository.save((ItemRequest) any())).thenReturn(itemRequest);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user2);
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenThrow(new NotFoundException("foo"));
        when(itemRequestMapper.toItemRequest((ItemRequestDto) any())).thenReturn(itemRequest1);
        assertThrows(NotFoundException.class, () -> itemRequestServiceImpl.create(new ItemRequestDto(), 1L));
        verify(itemRequestRepository).save((ItemRequest) any());
        verify(userRepository).findById((Long) any());
        verify(itemRequestMapper).toItemRequest((ItemRequestDto) any());
        verify(itemRequestMapper).toItemRequestDto((ItemRequest) any());
    }

    @Test
    void testCreate3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);
        when(itemRequestRepository.save((ItemRequest) any())).thenReturn(itemRequest);
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user1);
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        when(itemRequestMapper.toItemRequest((ItemRequestDto) any())).thenReturn(itemRequest1);
        assertThrows(NotFoundException.class, () -> itemRequestServiceImpl.create(new ItemRequestDto(), 1L));
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testGetByOwner() {
        when(itemRequestRepository.findAllByRequesterId((Pageable) any(), (Long) any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(new ArrayList<>());
        assertTrue(itemRequestServiceImpl.getByOwner(1, 3, 1L).isEmpty());
        verify(itemRequestRepository).findAllByRequesterId((Pageable) any(), (Long) any());
        verify(userRepository).findById((Long) any());
        verify(itemRepository).findAllByRequestId((List<Long>) any());
    }

    @Test
    void testGetByOwner2() {
        when(itemRequestRepository.findAllByRequesterId((Pageable) any(), (Long) any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenThrow(new NotFoundException("created"));
        assertThrows(NotFoundException.class, () -> itemRequestServiceImpl.getByOwner(1, 3, 1L));
        verify(itemRequestRepository).findAllByRequesterId((Pageable) any(), (Long) any());
        verify(userRepository).findById((Long) any());
        verify(itemRepository).findAllByRequestId((List<Long>) any());
    }

    @Test
    void testGetByOwner3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("created");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);

        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest);
        when(itemRequestRepository.findAllByRequesterId((Pageable) any(), (Long) any())).thenReturn(itemRequestList);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(new ArrayList<>());
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        assertEquals(1, itemRequestServiceImpl.getByOwner(1, 3, 1L).size());
        verify(itemRequestRepository).findAllByRequesterId((Pageable) any(), (Long) any());
        verify(userRepository).findById((Long) any());
        verify(itemRepository).findAllByRequestId((List<Long>) any());
        verify(itemRequestMapper).toItemRequestDto((ItemRequest) any());
    }

    @Test
    void testGetByOwner4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("created");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);

        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest);
        when(itemRequestRepository.findAllByRequesterId((Pageable) any(), (Long) any())).thenReturn(itemRequestList);
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(new ArrayList<>());
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        assertThrows(NotFoundException.class, () -> itemRequestServiceImpl.getByOwner(1, 3, 1L));
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testGetByOwner5() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("created");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);

        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest);
        when(itemRequestRepository.findAllByRequesterId((Pageable) any(), (Long) any())).thenReturn(itemRequestList);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("created");

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("created");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user3);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("created");
        item.setOwner(user2);
        item.setRequest(itemRequest1);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(itemList);
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        assertEquals(1, itemRequestServiceImpl.getByOwner(1, 3, 1L).size());
        verify(itemRequestRepository).findAllByRequesterId((Pageable) any(), (Long) any());
        verify(userRepository).findById((Long) any());
        verify(itemRepository).findAllByRequestId((List<Long>) any());
        verify(itemRequestMapper).toItemRequestDto((ItemRequest) any());
    }

    @Test
    void testGetByOwner6() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("created");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);

        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest);
        when(itemRequestRepository.findAllByRequesterId((Pageable) any(), (Long) any())).thenReturn(itemRequestList);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("created");

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("created");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user3);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("created");
        item.setOwner(user2);
        item.setRequest(itemRequest1);

        User user4 = new User();
        user4.setEmail("john.smith@example.org");
        user4.setId(2L);
        user4.setName("Name");

        User user5 = new User();
        user5.setEmail("john.smith@example.org");
        user5.setId(2L);
        user5.setName("Name");

        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest2.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest2.setDescription("created");
        itemRequest2.setId(2L);
        itemRequest2.setItems(new ArrayList<>());
        itemRequest2.setRequester(user5);

        Item item1 = new Item();
        item1.setAvailable(false);
        item1.setDescription("created");
        item1.setId(2L);
        item1.setName("Name");
        item1.setOwner(user4);
        item1.setRequest(itemRequest2);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(itemList);
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        assertEquals(1, itemRequestServiceImpl.getByOwner(1, 3, 1L).size());
        verify(itemRequestRepository).findAllByRequesterId((Pageable) any(), (Long) any());
        verify(userRepository).findById((Long) any());
        verify(itemRepository).findAllByRequestId((List<Long>) any());
        verify(itemRequestMapper).toItemRequestDto((ItemRequest) any());
    }

    @Test
    void testGetByOwner7() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("created");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);

        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest);
        when(itemRequestRepository.findAllByRequesterId((Pageable) any(), (Long) any())).thenReturn(itemRequestList);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(new ArrayList<>());
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        assertThrows(ArithmeticException.class, () -> itemRequestServiceImpl.getByOwner(1, 0, 1L));
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testGetByOwner8() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("created");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);

        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest);
        when(itemRequestRepository.findAllByRequesterId((Pageable) any(), (Long) any())).thenReturn(itemRequestList);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(new ArrayList<>());
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenThrow(new NotFoundException("created"));
        assertThrows(NotFoundException.class, () -> itemRequestServiceImpl.getByOwner(1, 3, 1L));
        verify(itemRequestRepository).findAllByRequesterId((Pageable) any(), (Long) any());
        verify(userRepository).findById((Long) any());
        verify(itemRepository).findAllByRequestId((List<Long>) any());
        verify(itemRequestMapper).toItemRequestDto((ItemRequest) any());
    }

    @Test
    void testGetALL() {
        when(itemRequestRepository.findAllByRequesterIdNot((Pageable) any(), (Long) any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(new ArrayList<>());
        assertTrue(itemRequestServiceImpl.getALL(1, 3, 1L).isEmpty());
        verify(itemRequestRepository).findAllByRequesterIdNot((Pageable) any(), (Long) any());
        verify(userRepository).findById((Long) any());
        verify(itemRepository).findAllByRequestId((List<Long>) any());
    }

    @Test
    void testGetALL2() {
        when(itemRequestRepository.findAllByRequesterIdNot((Pageable) any(), (Long) any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenThrow(new NotFoundException("created"));
        assertThrows(NotFoundException.class, () -> itemRequestServiceImpl.getALL(1, 3, 1L));
        verify(itemRequestRepository).findAllByRequesterIdNot((Pageable) any(), (Long) any());
        verify(userRepository).findById((Long) any());
        verify(itemRepository).findAllByRequestId((List<Long>) any());
    }

    @Test
    void testGetALL3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("created");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);

        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest);
        when(itemRequestRepository.findAllByRequesterIdNot((Pageable) any(), (Long) any())).thenReturn(itemRequestList);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(new ArrayList<>());
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        assertEquals(1, itemRequestServiceImpl.getALL(1, 3, 1L).size());
        verify(itemRequestRepository).findAllByRequesterIdNot((Pageable) any(), (Long) any());
        verify(userRepository).findById((Long) any());
        verify(itemRepository).findAllByRequestId((List<Long>) any());
        verify(itemRequestMapper).toItemRequestDto((ItemRequest) any());
    }

    @Test
    void testGetALL4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("created");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);

        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest);
        when(itemRequestRepository.findAllByRequesterIdNot((Pageable) any(), (Long) any())).thenReturn(itemRequestList);
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(new ArrayList<>());
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        assertThrows(NotFoundException.class, () -> itemRequestServiceImpl.getALL(1, 3, 1L));
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testGetALL5() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("created");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);

        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest);
        when(itemRequestRepository.findAllByRequesterIdNot((Pageable) any(), (Long) any())).thenReturn(itemRequestList);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("created");

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("created");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user3);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("created");
        item.setOwner(user2);
        item.setRequest(itemRequest1);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(itemList);
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        assertEquals(1, itemRequestServiceImpl.getALL(1, 3, 1L).size());
        verify(itemRequestRepository).findAllByRequesterIdNot((Pageable) any(), (Long) any());
        verify(userRepository).findById((Long) any());
        verify(itemRepository).findAllByRequestId((List<Long>) any());
        verify(itemRequestMapper).toItemRequestDto((ItemRequest) any());
    }

    @Test
    void testGetALL6() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("created");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);

        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest);
        when(itemRequestRepository.findAllByRequesterIdNot((Pageable) any(), (Long) any())).thenReturn(itemRequestList);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("created");

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("created");

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user3);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("created");
        item.setOwner(user2);
        item.setRequest(itemRequest1);

        User user4 = new User();
        user4.setEmail("john.smith@example.org");
        user4.setId(2L);
        user4.setName("Name");

        User user5 = new User();
        user5.setEmail("john.smith@example.org");
        user5.setId(2L);
        user5.setName("Name");

        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest2.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest2.setDescription("created");
        itemRequest2.setId(2L);
        itemRequest2.setItems(new ArrayList<>());
        itemRequest2.setRequester(user5);

        Item item1 = new Item();
        item1.setAvailable(false);
        item1.setDescription("created");
        item1.setId(2L);
        item1.setName("Name");
        item1.setOwner(user4);
        item1.setRequest(itemRequest2);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(itemList);
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        assertEquals(1, itemRequestServiceImpl.getALL(1, 3, 1L).size());
        verify(itemRequestRepository).findAllByRequesterIdNot((Pageable) any(), (Long) any());
        verify(userRepository).findById((Long) any());
        verify(itemRepository).findAllByRequestId((List<Long>) any());
        verify(itemRequestMapper).toItemRequestDto((ItemRequest) any());
    }

    @Test
    void testGetALL7() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("created");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);

        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest);
        when(itemRequestRepository.findAllByRequesterIdNot((Pageable) any(), (Long) any())).thenReturn(itemRequestList);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(new ArrayList<>());
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        assertThrows(ArithmeticException.class, () -> itemRequestServiceImpl.getALL(1, 0, 1L));
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testGetALL8() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("created");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);

        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest);
        when(itemRequestRepository.findAllByRequesterIdNot((Pageable) any(), (Long) any())).thenReturn(itemRequestList);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(itemRepository.findAllByRequestId((List<Long>) any())).thenReturn(new ArrayList<>());
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenThrow(new NotFoundException("created"));
        assertThrows(NotFoundException.class, () -> itemRequestServiceImpl.getALL(1, 3, 1L));
        verify(itemRequestRepository).findAllByRequesterIdNot((Pageable) any(), (Long) any());
        verify(userRepository).findById((Long) any());
        verify(itemRepository).findAllByRequestId((List<Long>) any());
        verify(itemRequestMapper).toItemRequestDto((ItemRequest) any());
    }

    @Test
    void testGetByID() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);
        Optional<ItemRequest> ofResult = Optional.of(itemRequest);
        when(itemRequestRepository.findById((Long) any())).thenReturn(ofResult);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        when(userRepository.existsById((Long) any())).thenReturn(true);
        when(itemRepository.findAllByRequestId(anyLong())).thenReturn(new ArrayList<>());
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(itemRequestDto);
        assertSame(itemRequestDto, itemRequestServiceImpl.getByID(1L, 1L));
        verify(itemRequestRepository).findById((Long) any());
        verify(userRepository).existsById((Long) any());
        verify(itemRepository).findAllByRequestId(anyLong());
        verify(itemRequestMapper).toItemRequestDto((ItemRequest) any());
    }

    @Test
    void testGetByID2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);
        Optional<ItemRequest> ofResult = Optional.of(itemRequest);
        when(itemRequestRepository.findById((Long) any())).thenReturn(ofResult);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        when(userRepository.existsById((Long) any())).thenReturn(true);
        when(itemRepository.findAllByRequestId(anyLong())).thenReturn(new ArrayList<>());
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenThrow(new NotFoundException("foo"));
        assertThrows(NotFoundException.class, () -> itemRequestServiceImpl.getByID(1L, 1L));
        verify(itemRequestRepository).findById((Long) any());
        verify(userRepository).existsById((Long) any());
        verify(itemRepository).findAllByRequestId(anyLong());
        verify(itemRequestMapper).toItemRequestDto((ItemRequest) any());
    }

    @Test
    void testGetByID3() {
        when(itemRequestRepository.findById((Long) any())).thenReturn(Optional.empty());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userRepository.existsById((Long) any())).thenReturn(true);
        when(itemRepository.findAllByRequestId(anyLong())).thenReturn(new ArrayList<>());
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        assertThrows(NotFoundException.class, () -> itemRequestServiceImpl.getByID(1L, 1L));
        verify(itemRequestRepository).findById((Long) any());
        verify(userRepository).existsById((Long) any());
    }

    @Test
    void testGetByID4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user);
        Optional<ItemRequest> ofResult = Optional.of(itemRequest);
        when(itemRequestRepository.findById((Long) any())).thenReturn(ofResult);
        when(userRepository.existsById((Long) any())).thenReturn(false);
        when(itemRepository.findAllByRequestId(anyLong())).thenReturn(new ArrayList<>());
        when(itemRequestMapper.toItemRequestDto((ItemRequest) any())).thenReturn(new ItemRequestDto());
        assertThrows(NotFoundUserException.class, () -> itemRequestServiceImpl.getByID(1L, 1L));
        verify(userRepository).existsById((Long) any());
    }
}

