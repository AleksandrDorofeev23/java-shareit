package ru.practicum.shareit.item.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.item.mapper.ItemMapperImpl;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemPlusDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ItemMapperImpl.class})
@ExtendWith(SpringExtension.class)
class ItemMapperImplTest {
    @Autowired
    private ItemMapperImpl itemMapperImpl;

    @Test
    void testToItemDto() {
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
        ItemDto actualToItemDtoResult = itemMapperImpl.toItemDto(item);
        assertTrue(actualToItemDtoResult.getAvailable());
        assertEquals(1L, actualToItemDtoResult.getRequestId().longValue());
        assertEquals("Name", actualToItemDtoResult.getName());
        assertEquals(1L, actualToItemDtoResult.getId());
        assertEquals("The characteristics of someone or something", actualToItemDtoResult.getDescription());
    }

    @Test
    void testToItemDto2() {
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
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        when(item.getDescription()).thenReturn("The characteristics of someone or something");
        when(item.getName()).thenReturn("Name");
        when(item.getId()).thenReturn(1L);
        when(item.getRequest()).thenReturn(itemRequest1);
        doNothing().when(item).setAvailable((Boolean) any());
        doNothing().when(item).setDescription((String) any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName((String) any());
        doNothing().when(item).setOwner((User) any());
        doNothing().when(item).setRequest((ItemRequest) any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);
        ItemDto actualToItemDtoResult = itemMapperImpl.toItemDto(item);
        assertTrue(actualToItemDtoResult.getAvailable());
        assertEquals(1L, actualToItemDtoResult.getRequestId().longValue());
        assertEquals("Name", actualToItemDtoResult.getName());
        assertEquals(1L, actualToItemDtoResult.getId());
        assertEquals("The characteristics of someone or something", actualToItemDtoResult.getDescription());
        verify(item).getAvailable();
        verify(item).getDescription();
        verify(item).getName();
        verify(item).getId();
        verify(item).getRequest();
        verify(item).setAvailable((Boolean) any());
        verify(item).setDescription((String) any());
        verify(item).setId(anyLong());
        verify(item).setName((String) any());
        verify(item).setOwner((User) any());
        verify(item).setRequest((ItemRequest) any());
    }

    @Test
    void testToItemPlusDto() {
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
        ItemPlusDto actualToItemPlusDtoResult = itemMapperImpl.toItemPlusDto(item);
        assertTrue(actualToItemPlusDtoResult.getAvailable());
        assertTrue(actualToItemPlusDtoResult.getComments().isEmpty());
        assertEquals(1L, actualToItemPlusDtoResult.getId());
        assertEquals("Name", actualToItemPlusDtoResult.getName());
        assertEquals("The characteristics of someone or something", actualToItemPlusDtoResult.getDescription());
        UserDto owner = actualToItemPlusDtoResult.getOwner();
        assertEquals("Name", owner.getName());
        assertEquals(1L, owner.getId());
        assertEquals("jane.doe@example.org", owner.getEmail());
    }

    @Test
    void testToItemPlusDto2() {
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

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        when(item.getDescription()).thenReturn("The characteristics of someone or something");
        when(item.getName()).thenReturn("Name");
        when(item.getId()).thenReturn(1L);
        when(item.getOwner()).thenReturn(user2);
        doNothing().when(item).setAvailable((Boolean) any());
        doNothing().when(item).setDescription((String) any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName((String) any());
        doNothing().when(item).setOwner((User) any());
        doNothing().when(item).setRequest((ItemRequest) any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user);
        item.setRequest(itemRequest);
        ItemPlusDto actualToItemPlusDtoResult = itemMapperImpl.toItemPlusDto(item);
        assertTrue(actualToItemPlusDtoResult.getAvailable());
        assertTrue(actualToItemPlusDtoResult.getComments().isEmpty());
        assertEquals(1L, actualToItemPlusDtoResult.getId());
        assertEquals("Name", actualToItemPlusDtoResult.getName());
        assertEquals("The characteristics of someone or something", actualToItemPlusDtoResult.getDescription());
        UserDto owner = actualToItemPlusDtoResult.getOwner();
        assertEquals("Name", owner.getName());
        assertEquals(1L, owner.getId());
        assertEquals("jane.doe@example.org", owner.getEmail());
        verify(item).getAvailable();
        verify(item).getDescription();
        verify(item).getName();
        verify(item).getId();
        verify(item).getOwner();
        verify(item).setAvailable((Boolean) any());
        verify(item).setDescription((String) any());
        verify(item).setId(anyLong());
        verify(item).setName((String) any());
        verify(item).setOwner((User) any());
        verify(item).setRequest((ItemRequest) any());
    }

    @Test
    void testToItem() {
        Item actualToItemResult = itemMapperImpl.toItem(new ItemDto());
        assertNull(actualToItemResult.getAvailable());
        assertNull(actualToItemResult.getName());
        assertEquals(0L, actualToItemResult.getId());
        assertNull(actualToItemResult.getDescription());
    }

    @Test
    void testToItem2() {
        ItemDto itemDto = mock(ItemDto.class);
        when(itemDto.getAvailable()).thenReturn(true);
        when(itemDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(itemDto.getName()).thenReturn("Name");
        when(itemDto.getId()).thenReturn(1L);
        Item actualToItemResult = itemMapperImpl.toItem(itemDto);
        assertTrue(actualToItemResult.getAvailable());
        assertEquals("Name", actualToItemResult.getName());
        assertEquals(1L, actualToItemResult.getId());
        assertEquals("The characteristics of someone or something", actualToItemResult.getDescription());
        verify(itemDto).getAvailable();
        verify(itemDto).getDescription();
        verify(itemDto).getName();
        verify(itemDto).getId();
    }

    @Test
    void testToItem3() {
        ItemPlusDto itemPlusDto = new ItemPlusDto();
        itemPlusDto.setAvailable(true);
        itemPlusDto.setComments(new HashSet<>());
        itemPlusDto.setDescription("The characteristics of someone or something");
        itemPlusDto.setId(1L);
        itemPlusDto.setLastBooking(new BookingDto());
        itemPlusDto.setName("Name");
        itemPlusDto.setNextBooking(new BookingDto());
        itemPlusDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        Item actualToItemResult = itemMapperImpl.toItem(itemPlusDto);
        assertTrue(actualToItemResult.getAvailable());
        assertEquals("Name", actualToItemResult.getName());
        assertEquals("The characteristics of someone or something", actualToItemResult.getDescription());
        assertEquals(1L, actualToItemResult.getId());
        User owner = actualToItemResult.getOwner();
        assertEquals("jane.doe@example.org", owner.getEmail());
        assertEquals(1L, owner.getId());
        assertEquals("Name", owner.getName());
    }

    @Test
    void testToItem4() {
        ItemPlusDto itemPlusDto = mock(ItemPlusDto.class);
        when(itemPlusDto.getAvailable()).thenReturn(true);
        when(itemPlusDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(itemPlusDto.getName()).thenReturn("Name");
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
        Item actualToItemResult = itemMapperImpl.toItem(itemPlusDto);
        assertTrue(actualToItemResult.getAvailable());
        assertEquals("Name", actualToItemResult.getName());
        assertEquals("The characteristics of someone or something", actualToItemResult.getDescription());
        assertEquals(1L, actualToItemResult.getId());
        User owner = actualToItemResult.getOwner();
        assertEquals("jane.doe@example.org", owner.getEmail());
        assertEquals(1L, owner.getId());
        assertEquals("Name", owner.getName());
        verify(itemPlusDto).getAvailable();
        verify(itemPlusDto).getDescription();
        verify(itemPlusDto).getName();
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
    }

}

