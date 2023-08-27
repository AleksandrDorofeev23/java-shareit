package ru.practicum.shareit.request.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ItemRequestTest {

    @Test
    void testConstructor() {
        ItemRequest actualItemRequest = new ItemRequest();
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        actualItemRequest.setCreated(ofResult);
        actualItemRequest.setDescription("The characteristics of someone or something");
        actualItemRequest.setId(1L);
        ArrayList<Item> itemList = new ArrayList<>();
        actualItemRequest.setItems(itemList);
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        actualItemRequest.setRequester(user);
        String actualToStringResult = actualItemRequest.toString();
        assertSame(ofResult, actualItemRequest.getCreated());
        assertEquals("The characteristics of someone or something", actualItemRequest.getDescription());
        assertEquals(1L, actualItemRequest.getId());
        assertSame(itemList, actualItemRequest.getItems());
        assertSame(user, actualItemRequest.getRequester());
        assertEquals("ItemRequest(id=1, description=The characteristics of someone or something, requester=User(id=1,"
                + " name=Name, email=jane.doe@example.org), created=0001-01-01T01:01, items=[])", actualToStringResult);
    }

}

