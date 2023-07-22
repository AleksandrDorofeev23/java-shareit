package ru.practicum.shareit.booking.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class BookingTest {

    @Test
    void testConstructor() {
        Booking actualBooking = new Booking();
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        actualBooking.setBooker(user);
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        actualBooking.setEnd(ofResult);
        actualBooking.setId(1L);
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
        actualBooking.setItem(item);
        LocalDateTime ofResult1 = LocalDateTime.of(1, 1, 1, 1, 1);
        actualBooking.setStart(ofResult1);
        actualBooking.setStatus(Status.WAITING);
        String actualToStringResult = actualBooking.toString();
        User booker = actualBooking.getBooker();
        assertSame(user, booker);
        assertEquals(user1, booker);
        assertEquals(user2, booker);
        assertSame(ofResult, actualBooking.getEnd());
        assertEquals(1L, actualBooking.getId());
        assertSame(item, actualBooking.getItem());
        assertSame(ofResult1, actualBooking.getStart());
        assertEquals(Status.WAITING, actualBooking.getStatus());
        assertEquals(
                "Booking(id=1, start=0001-01-01T01:01, end=0001-01-01T01:01, item=Item(id=1, name=Name, description=The"
                        + " characteristics of someone or something, available=true, owner=User(id=1, name=Name, email=jane.doe"
                        + "@example.org), request=ItemRequest(id=1, description=The characteristics of someone or something,"
                        + " requester=User(id=1, name=Name, email=jane.doe@example.org), created=0001-01-01T01:01, items=[])),"
                        + " booker=User(id=1, name=Name, email=jane.doe@example.org), status=WAITING)",
                actualToStringResult);
    }

}

