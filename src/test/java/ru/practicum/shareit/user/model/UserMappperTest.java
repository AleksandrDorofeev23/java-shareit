package ru.practicum.shareit.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.model.dto.UserDto;

class UserMappperTest {

    @Test
    void testToUserDto() {
        UserDto actualToUserDtoResult = UserMappper.toUserDto(new User(1L, "Name", "jane.doe@example.org"));
        assertEquals("jane.doe@example.org", actualToUserDtoResult.getEmail());
        assertEquals("Name", actualToUserDtoResult.getName());
        assertEquals(1L, actualToUserDtoResult.getId());
    }

    @Test
    void testToUser() {
        User actualToUserResult = UserMappper.toUser(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertEquals("jane.doe@example.org", actualToUserResult.getEmail());
        assertEquals("Name", actualToUserResult.getName());
        assertEquals(-1L, actualToUserResult.getId());
    }

}

