package ru.practicum.shareit.item.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testConstructor() {
        Item actualItem = new Item();
        actualItem.setAvailable(true);
        actualItem.setDescription("The characteristics of someone or something");
        actualItem.setId(1L);
        actualItem.setName("Name");
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        actualItem.setOwner(user);
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
        actualItem.setRequest(itemRequest);
        String actualToStringResult = actualItem.toString();
        assertTrue(actualItem.getAvailable());
        assertEquals("The characteristics of someone or something", actualItem.getDescription());
        assertEquals(1L, actualItem.getId());
        assertEquals("Name", actualItem.getName());
        User owner = actualItem.getOwner();
        assertSame(user, owner);
        assertEquals(user1, owner);
        assertSame(itemRequest, actualItem.getRequest());
        assertEquals("Item(id=1, name=Name, description=The characteristics of someone or something, available=true,"
                + " owner=User(id=1, name=Name, email=jane.doe@example.org), request=ItemRequest(id=1, description=The"
                + " characteristics of someone or something, requester=User(id=1, name=Name, email=jane.doe@example.org),"
                + " created=0001-01-01T01:01, items=[]))", actualToStringResult);
    }

}

