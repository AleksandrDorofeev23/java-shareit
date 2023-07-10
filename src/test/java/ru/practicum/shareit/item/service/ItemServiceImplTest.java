package ru.practicum.shareit.item.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

@ContextConfiguration(classes = {ItemServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ItemServiceImplTest {
    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @MockBean
    private ItemStorage itemStorage;

    @MockBean
    private UserStorage userStorage;

    @Test
    void testGetById() {
        User owner = new User(1L, "Name", "jane.doe@example.org");

        when(itemStorage.getById(anyLong())).thenReturn(
                new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest()));
        ItemDto actualById = itemServiceImpl.getById(1L);
        assertTrue(actualById.getAvailable());
        assertEquals("Name", actualById.getName());
        assertEquals(1L, actualById.getId());
        assertEquals("The characteristics of someone or something", actualById.getDescription());
        verify(itemStorage).getById(anyLong());
    }

    @Test
    void testGetById2() {
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        when(item.getDescription()).thenReturn("The characteristics of someone or something");
        when(item.getName()).thenReturn("Name");
        when(item.getId()).thenReturn(1L);
        when(itemStorage.getById(anyLong())).thenReturn(item);
        ItemDto actualById = itemServiceImpl.getById(1L);
        assertTrue(actualById.getAvailable());
        assertEquals("Name", actualById.getName());
        assertEquals(1L, actualById.getId());
        assertEquals("The characteristics of someone or something", actualById.getDescription());
        verify(itemStorage).getById(anyLong());
        verify(item).getAvailable();
        verify(item).getDescription();
        verify(item).getName();
        verify(item).getId();
    }

    @Test
    void testGetAllByUser() {
        when(itemStorage.getAllByUser()).thenReturn(new ArrayList<>());
        assertTrue(itemServiceImpl.getAllByUser(1L).isEmpty());
        verify(itemStorage).getAllByUser();
    }

    @Test
    void testGetAllByUser2() {
        ArrayList<Item> itemList = new ArrayList<>();
        User owner = new User(1L, "Name", "jane.doe@example.org");

        itemList.add(new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest()));
        when(itemStorage.getAllByUser()).thenReturn(itemList);
        Collection<ItemDto> actualAllByUser = itemServiceImpl.getAllByUser(1L);
        assertEquals(1, actualAllByUser.size());
        ItemDto getResult = ((List<ItemDto>) actualAllByUser).get(0);
        assertTrue(getResult.getAvailable());
        assertEquals("Name", getResult.getName());
        assertEquals(1L, getResult.getId());
        assertEquals("The characteristics of someone or something", getResult.getDescription());
        verify(itemStorage).getAllByUser();
    }

    @Test
    void testGetAllByUser3() {
        ArrayList<Item> itemList = new ArrayList<>();
        User owner = new User(1L, "Name", "jane.doe@example.org");

        itemList.add(new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest()));
        User owner1 = new User(1L, "Name", "jane.doe@example.org");

        itemList
                .add(new Item(1L, "Name", "The characteristics of someone or something", true, owner1, new ItemRequest()));
        when(itemStorage.getAllByUser()).thenReturn(itemList);
        Collection<ItemDto> actualAllByUser = itemServiceImpl.getAllByUser(1L);
        assertEquals(2, actualAllByUser.size());
        ItemDto getResult = ((List<ItemDto>) actualAllByUser).get(0);
        assertEquals("Name", getResult.getName());
        ItemDto getResult1 = ((List<ItemDto>) actualAllByUser).get(1);
        assertEquals("Name", getResult1.getName());
        assertEquals(1L, getResult1.getId());
        assertEquals("The characteristics of someone or something", getResult1.getDescription());
        assertTrue(getResult1.getAvailable());
        assertEquals(1L, getResult.getId());
        assertEquals("The characteristics of someone or something", getResult.getDescription());
        assertTrue(getResult.getAvailable());
        verify(itemStorage).getAllByUser();
    }

    @Test
    void testGetAllByUser4() {
        when(itemStorage.getAllByUser()).thenThrow(new NotFoundException("foo"));
        assertThrows(NotFoundException.class, () -> itemServiceImpl.getAllByUser(1L));
        verify(itemStorage).getAllByUser();
    }

    @Test
    void testGetAllByUser5() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(-1L);
        Item e = new Item(1L, "Name", "The characteristics of someone or something", true, user, new ItemRequest());

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(e);
        when(itemStorage.getAllByUser()).thenReturn(itemList);
        assertTrue(itemServiceImpl.getAllByUser(1L).isEmpty());
        verify(itemStorage).getAllByUser();
        verify(user).getId();
    }

    @Test
    void testGetAllByUser6() {
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        when(item.getDescription()).thenReturn("The characteristics of someone or something");
        when(item.getName()).thenReturn("Name");
        when(item.getId()).thenReturn(1L);
        when(item.getOwner()).thenReturn(new User(1L, "Name", "jane.doe@example.org"));

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemStorage.getAllByUser()).thenReturn(itemList);
        Collection<ItemDto> actualAllByUser = itemServiceImpl.getAllByUser(1L);
        assertEquals(1, actualAllByUser.size());
        ItemDto getResult = ((List<ItemDto>) actualAllByUser).get(0);
        assertTrue(getResult.getAvailable());
        assertEquals("Name", getResult.getName());
        assertEquals(1L, getResult.getId());
        assertEquals("The characteristics of someone or something", getResult.getDescription());
        verify(itemStorage).getAllByUser();
        verify(item).getAvailable();
        verify(item).getDescription();
        verify(item).getName();
        verify(item).getId();
        verify(item).getOwner();
    }

    @Test
    void testCreate() {
        User owner = new User(1L, "Name", "jane.doe@example.org");

        when(itemStorage.create((Item) any())).thenReturn(
                new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest()));
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        ItemDto actualCreateResult = itemServiceImpl
                .create(new ItemDto(1L, "Name", "The characteristics of someone or something", true), 1L);
        assertTrue(actualCreateResult.getAvailable());
        assertEquals("Name", actualCreateResult.getName());
        assertEquals(1L, actualCreateResult.getId());
        assertEquals("The characteristics of someone or something", actualCreateResult.getDescription());
        verify(itemStorage).create((Item) any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testCreate2() {
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        when(item.getDescription()).thenReturn("The characteristics of someone or something");
        when(item.getName()).thenReturn("Name");
        when(item.getId()).thenReturn(1L);
        when(itemStorage.create((Item) any())).thenReturn(item);
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        ItemDto actualCreateResult = itemServiceImpl
                .create(new ItemDto(1L, "Name", "The characteristics of someone or something", true), 1L);
        assertTrue(actualCreateResult.getAvailable());
        assertEquals("Name", actualCreateResult.getName());
        assertEquals(1L, actualCreateResult.getId());
        assertEquals("The characteristics of someone or something", actualCreateResult.getDescription());
        verify(itemStorage).create((Item) any());
        verify(item).getAvailable();
        verify(item).getDescription();
        verify(item).getName();
        verify(item).getId();
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testCreate3() {
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        when(item.getDescription()).thenReturn("The characteristics of someone or something");
        when(item.getName()).thenReturn("Name");
        when(item.getId()).thenReturn(1L);
        when(itemStorage.create((Item) any())).thenReturn(item);
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        ItemDto itemDto = mock(ItemDto.class);
        when(itemDto.getAvailable()).thenReturn(true);
        when(itemDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(itemDto.getName()).thenReturn("Name");
        when(itemDto.getId()).thenReturn(1L);
        ItemDto actualCreateResult = itemServiceImpl.create(itemDto, 1L);
        assertTrue(actualCreateResult.getAvailable());
        assertEquals("Name", actualCreateResult.getName());
        assertEquals(1L, actualCreateResult.getId());
        assertEquals("The characteristics of someone or something", actualCreateResult.getDescription());
        verify(itemStorage).create((Item) any());
        verify(item).getAvailable();
        verify(item).getDescription();
        verify(item).getName();
        verify(item).getId();
        verify(userStorage).getById(anyLong());
        verify(itemDto).getAvailable();
        verify(itemDto).getDescription();
        verify(itemDto).getName();
        verify(itemDto).getId();
    }

    @Test
    void testUpdate() {
        User owner = new User(1L, "Name", "jane.doe@example.org");

        when(itemStorage.update((Item) any())).thenReturn(
                new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest()));
        User owner1 = new User(1L, "Name", "jane.doe@example.org");

        when(itemStorage.getById(anyLong())).thenReturn(
                new Item(1L, "Name", "The characteristics of someone or something", true, owner1, new ItemRequest()));
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        User owner2 = new User(1L, "Name", "jane.doe@example.org");

        ItemDto actualUpdateResult = itemServiceImpl.update(
                new ItemDto(1L, "Name", "The characteristics of someone or something", true), 1L, 1L);
        assertTrue(actualUpdateResult.getAvailable());
        assertEquals("Name", actualUpdateResult.getName());
        assertEquals(1L, actualUpdateResult.getId());
        assertEquals("The characteristics of someone or something", actualUpdateResult.getDescription());
        verify(itemStorage).getById(anyLong());
        verify(itemStorage).update((Item) any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testUpdate2() {
        User owner = new User(1L, "Name", "jane.doe@example.org");

        when(itemStorage.update((Item) any())).thenReturn(
                new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest()));
        User owner1 = new User(1L, "Name", "jane.doe@example.org");

        when(itemStorage.getById(anyLong())).thenReturn(
                new Item(1L, "Name", "The characteristics of someone or something", true, owner1, new ItemRequest()));
        when(userStorage.getById(anyLong())).thenThrow(new NotFoundException("foo"));
        User owner2 = new User(1L, "Name", "jane.doe@example.org");

        assertThrows(NotFoundException.class,
                () -> itemServiceImpl.update(
                        new ItemDto(1L, "Name", "The characteristics of someone or something", true), 1L,
                        1L));
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testUpdate3() {
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        when(item.getDescription()).thenReturn("The characteristics of someone or something");
        when(item.getName()).thenReturn("Name");
        when(item.getId()).thenReturn(1L);
        when(itemStorage.update((Item) any())).thenReturn(item);
        User owner = new User(1L, "Name", "jane.doe@example.org");

        when(itemStorage.getById(anyLong())).thenReturn(
                new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest()));
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        User owner1 = new User(1L, "Name", "jane.doe@example.org");

        ItemDto actualUpdateResult = itemServiceImpl.update(
                new ItemDto(1L, "Name", "The characteristics of someone or something", true), 1L, 1L);
        assertTrue(actualUpdateResult.getAvailable());
        assertEquals("Name", actualUpdateResult.getName());
        assertEquals(1L, actualUpdateResult.getId());
        assertEquals("The characteristics of someone or something", actualUpdateResult.getDescription());
        verify(itemStorage).getById(anyLong());
        verify(itemStorage).update((Item) any());
        verify(item).getAvailable();
        verify(item).getDescription();
        verify(item).getName();
        verify(item).getId();
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testUpdate4() {
        when(itemStorage.update((Item) any())).thenReturn(mock(Item.class));
        User owner = new User(2L, "Name", "jane.doe@example.org");

        when(itemStorage.getById(anyLong())).thenReturn(
                new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest()));
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        User owner1 = new User(1L, "Name", "jane.doe@example.org");

        assertThrows(NotFoundException.class,
                () -> itemServiceImpl.update(
                        new ItemDto(1L, "Name", "The characteristics of someone or something", true), 1L,
                        1L));
        verify(itemStorage).getById(anyLong());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testUpdate5() {
        Item item = mock(Item.class);
        doNothing().when(item).setAvailable((Boolean) any());
        doNothing().when(item).setDescription((String) any());
        doNothing().when(item).setName((String) any());
        when(item.getOwner()).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        Item item1 = mock(Item.class);
        when(item1.getAvailable()).thenReturn(true);
        when(item1.getDescription()).thenReturn("The characteristics of someone or something");
        when(item1.getName()).thenReturn("Name");
        when(item1.getId()).thenReturn(1L);
        when(itemStorage.update((Item) any())).thenReturn(item1);
        when(itemStorage.getById(anyLong())).thenReturn(item);
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        User owner = new User(1L, "Name", "jane.doe@example.org");

        ItemDto actualUpdateResult = itemServiceImpl.update(
                new ItemDto(1L, "Name", "The characteristics of someone or something", true), 1L, 1L);
        assertTrue(actualUpdateResult.getAvailable());
        assertEquals("Name", actualUpdateResult.getName());
        assertEquals(1L, actualUpdateResult.getId());
        assertEquals("The characteristics of someone or something", actualUpdateResult.getDescription());
        verify(itemStorage).getById(anyLong());
        verify(itemStorage).update((Item) any());
        verify(item1).getAvailable();
        verify(item1).getDescription();
        verify(item1).getName();
        verify(item1).getId();
        verify(item).getOwner();
        verify(item).setAvailable((Boolean) any());
        verify(item).setDescription((String) any());
        verify(item).setName((String) any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testUpdate6() {
        Item item = mock(Item.class);
        doThrow(new NotFoundException("foo")).when(item).setAvailable((Boolean) any());
        doThrow(new NotFoundException("foo")).when(item).setDescription((String) any());
        doThrow(new NotFoundException("foo")).when(item).setName((String) any());
        when(item.getOwner()).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        when(itemStorage.update((Item) any())).thenReturn(mock(Item.class));
        when(itemStorage.getById(anyLong())).thenReturn(item);
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        User owner = new User(1L, "Name", "jane.doe@example.org");

        assertThrows(NotFoundException.class,
                () -> itemServiceImpl.update(
                        new ItemDto(1L, "Name", "The characteristics of someone or something", true), 1L,
                        1L));
        verify(itemStorage).getById(anyLong());
        verify(item).getOwner();
        verify(item).setName((String) any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testDeleteById() {
        doNothing().when(itemStorage).deleteById(anyLong());
        itemServiceImpl.deleteById(1L);
        verify(itemStorage).deleteById(anyLong());
    }

    @Test
    void testDeleteById2() {
        doThrow(new NotFoundException("foo")).when(itemStorage).deleteById(anyLong());
        assertThrows(NotFoundException.class, () -> itemServiceImpl.deleteById(1L));
        verify(itemStorage).deleteById(anyLong());
    }

    @Test
    void testSearch() {
        when(itemStorage.getAllByUser()).thenReturn(new ArrayList<>());
        assertTrue(itemServiceImpl.search("Text").isEmpty());
        verify(itemStorage).getAllByUser();
    }

    @Test
    void testSearch2() {
        ArrayList<Item> itemList = new ArrayList<>();
        User owner = new User(1L, "Name", "jane.doe@example.org");

        itemList.add(new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest()));
        when(itemStorage.getAllByUser()).thenReturn(itemList);
        assertTrue(itemServiceImpl.search("Text").isEmpty());
        verify(itemStorage).getAllByUser();
    }

    @Test
    void testSearch3() {
        ArrayList<Item> itemList = new ArrayList<>();
        User owner = new User(1L, "Name", "jane.doe@example.org");

        itemList.add(new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest()));
        User owner1 = new User(1L, "Name", "jane.doe@example.org");

        itemList
                .add(new Item(1L, "Name", "The characteristics of someone or something", true, owner1, new ItemRequest()));
        when(itemStorage.getAllByUser()).thenReturn(itemList);
        assertTrue(itemServiceImpl.search("Text").isEmpty());
        verify(itemStorage).getAllByUser();
    }

    @Test
    void testSearch4() {
        when(itemStorage.getAllByUser()).thenReturn(new ArrayList<>());
        assertTrue(itemServiceImpl.search("").isEmpty());
    }

    @Test
    void testSearch5() {
        when(itemStorage.getAllByUser()).thenThrow(new NotFoundException("foo"));
        assertThrows(NotFoundException.class, () -> itemServiceImpl.search("Text"));
        verify(itemStorage).getAllByUser();
    }

}

