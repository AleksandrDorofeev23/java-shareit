package ru.practicum.shareit.item.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class CommentTest {

    @Test
    void testConstructor() {
        Comment actualComment = new Comment();
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        actualComment.setAuthor(user);
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        actualComment.setCreated(ofResult);
        actualComment.setId(1L);
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
        actualComment.setItem(item);
        actualComment.setText("Text");
        String actualToStringResult = actualComment.toString();
        User author = actualComment.getAuthor();
        assertSame(user, author);
        assertEquals(user1, author);
        assertEquals(user2, author);
        assertSame(ofResult, actualComment.getCreated());
        assertEquals(1L, actualComment.getId());
        assertSame(item, actualComment.getItem());
        assertEquals("Text", actualComment.getText());
        assertEquals("Comment(id=1, text=Text, item=Item(id=1, name=Name, description=The characteristics of someone or"
                + " something, available=true, owner=User(id=1, name=Name, email=jane.doe@example.org), request=ItemRequest"
                + "(id=1, description=The characteristics of someone or something, requester=User(id=1, name=Name,"
                + " email=jane.doe@example.org), created=0001-01-01T01:01, items=[])), author=User(id=1, name=Name,"
                + " email=jane.doe@example.org), created=0001-01-01T01:01)", actualToStringResult);
    }

}

