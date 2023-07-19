package ru.practicum.shareit.request.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ItemRequestMapper.class})
@ExtendWith(SpringExtension.class)
class ItemRequestMapperTest {
    @MockBean
    private ItemMapper itemMapper;

    @Autowired
    private ItemRequestMapper itemRequestMapper;

    @Test
    void testToItemRequest() {
        ItemRequest actualToItemRequestResult = itemRequestMapper.toItemRequest(new ItemRequestDto());
        assertTrue(actualToItemRequestResult.getItems().isEmpty());
        assertNull(actualToItemRequestResult.getDescription());
    }

    @Test
    void testToItemRequest2() {
        ItemRequestDto itemRequestDto = mock(ItemRequestDto.class);
        when(itemRequestDto.getDescription()).thenReturn("The characteristics of someone or something");
        ItemRequest actualToItemRequestResult = itemRequestMapper.toItemRequest(itemRequestDto);
        assertTrue(actualToItemRequestResult.getItems().isEmpty());
        assertEquals("The characteristics of someone or something", actualToItemRequestResult.getDescription());
        verify(itemRequestDto).getDescription();
    }

    @Test
    void testToItemRequestDto() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        ArrayList<Item> itemList = new ArrayList<>();
        itemRequest.setItems(itemList);
        itemRequest.setRequester(user);
        ItemRequestDto actualToItemRequestDtoResult = itemRequestMapper.toItemRequestDto(itemRequest);
        assertEquals(itemList, actualToItemRequestDtoResult.getItems());
        assertEquals("01:01", actualToItemRequestDtoResult.getCreated().toLocalTime().toString());
        assertEquals(1L, actualToItemRequestDtoResult.getId());
        assertEquals("The characteristics of someone or something", actualToItemRequestDtoResult.getDescription());
    }

    @Test
    void testToItemRequestDto2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        ItemRequest itemRequest = mock(ItemRequest.class);
        when(itemRequest.getDescription()).thenReturn("The characteristics of someone or something");
        when(itemRequest.getCreated()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(itemRequest.getItems()).thenReturn(new ArrayList<>());
        when(itemRequest.getId()).thenReturn(1L);
        doNothing().when(itemRequest).setCreated((LocalDateTime) any());
        doNothing().when(itemRequest).setDescription((String) any());
        doNothing().when(itemRequest).setId(anyLong());
        doNothing().when(itemRequest).setItems((List<Item>) any());
        doNothing().when(itemRequest).setRequester((User) any());
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        ArrayList<Item> itemList = new ArrayList<>();
        itemRequest.setItems(itemList);
        itemRequest.setRequester(user);
        ItemRequestDto actualToItemRequestDtoResult = itemRequestMapper.toItemRequestDto(itemRequest);
        assertEquals(itemList, actualToItemRequestDtoResult.getItems());
        assertEquals("01:01", actualToItemRequestDtoResult.getCreated().toLocalTime().toString());
        assertEquals(1L, actualToItemRequestDtoResult.getId());
        assertEquals("The characteristics of someone or something", actualToItemRequestDtoResult.getDescription());
        verify(itemRequest).getDescription();
        verify(itemRequest).getCreated();
        verify(itemRequest).getItems();
        verify(itemRequest).getId();
        verify(itemRequest).setCreated((LocalDateTime) any());
        verify(itemRequest).setDescription((String) any());
        verify(itemRequest).setId(anyLong());
        verify(itemRequest).setItems((List<Item>) any());
        verify(itemRequest).setRequester((User) any());
    }

    @Test
    void testToItemRequestDto3() {
        when(itemMapper.toItemDto((Item) any())).thenReturn(new ItemDto());

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

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        ItemRequest itemRequest1 = mock(ItemRequest.class);
        when(itemRequest1.getDescription()).thenReturn("The characteristics of someone or something");
        when(itemRequest1.getCreated()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(itemRequest1.getItems()).thenReturn(itemList);
        when(itemRequest1.getId()).thenReturn(1L);
        doNothing().when(itemRequest1).setCreated((LocalDateTime) any());
        doNothing().when(itemRequest1).setDescription((String) any());
        doNothing().when(itemRequest1).setId(anyLong());
        doNothing().when(itemRequest1).setItems((List<Item>) any());
        doNothing().when(itemRequest1).setRequester((User) any());
        itemRequest1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest1.setDescription("The characteristics of someone or something");
        itemRequest1.setId(1L);
        itemRequest1.setItems(new ArrayList<>());
        itemRequest1.setRequester(user);
        ItemRequestDto actualToItemRequestDtoResult = itemRequestMapper.toItemRequestDto(itemRequest1);
        assertEquals(1, actualToItemRequestDtoResult.getItems().size());
        assertEquals("01:01", actualToItemRequestDtoResult.getCreated().toLocalTime().toString());
        assertEquals("The characteristics of someone or something", actualToItemRequestDtoResult.getDescription());
        assertEquals(1L, actualToItemRequestDtoResult.getId());
        verify(itemMapper).toItemDto((Item) any());
        verify(itemRequest1).getDescription();
        verify(itemRequest1).getCreated();
        verify(itemRequest1).getItems();
        verify(itemRequest1).getId();
        verify(itemRequest1).setCreated((LocalDateTime) any());
        verify(itemRequest1).setDescription((String) any());
        verify(itemRequest1).setId(anyLong());
        verify(itemRequest1).setItems((List<Item>) any());
        verify(itemRequest1).setRequester((User) any());
    }
}

