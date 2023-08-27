package ru.practicum.shareit.user.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testConstructor() {
        User actualUser = new User();
        actualUser.setEmail("jane.doe@example.org");
        actualUser.setId(1L);
        actualUser.setName("Name");
        String actualToStringResult = actualUser.toString();
        assertEquals("jane.doe@example.org", actualUser.getEmail());
        assertEquals(1L, actualUser.getId());
        assertEquals("Name", actualUser.getName());
        assertEquals("User(id=1, name=Name, email=jane.doe@example.org)", actualToStringResult);
    }

    @Test
    void testConstructor2() {
        User actualUser = new User(1L, "Name", "jane.doe@example.org");
        assertEquals("jane.doe@example.org", actualUser.getEmail());
        assertEquals("Name", actualUser.getName());
        assertEquals(1L, actualUser.getId());
    }

}

