package ru.practicum.shareit.user.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.user.mapper.UserMapperImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserMapperImpl.class})
@ExtendWith(SpringExtension.class)
class UserMapperImplTest {
    @Autowired
    private UserMapperImpl userMapperImpl;

    @Test
    void testToUserDto() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserDto actualToUserDtoResult = userMapperImpl.toUserDto(user);
        assertEquals("jane.doe@example.org", actualToUserDtoResult.getEmail());
        assertEquals("Name", actualToUserDtoResult.getName());
        assertEquals(1L, actualToUserDtoResult.getId());
    }

    @Test
    void testToUserDto2() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getName()).thenReturn("Name");
        when(user.getId()).thenReturn(1L);
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setId(anyLong());
        doNothing().when(user).setName((String) any());
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserDto actualToUserDtoResult = userMapperImpl.toUserDto(user);
        assertEquals("jane.doe@example.org", actualToUserDtoResult.getEmail());
        assertEquals("Name", actualToUserDtoResult.getName());
        assertEquals(1L, actualToUserDtoResult.getId());
        verify(user).getEmail();
        verify(user).getName();
        verify(user).getId();
        verify(user).setEmail((String) any());
        verify(user).setId(anyLong());
        verify(user).setName((String) any());
    }

    @Test
    void testToUser() {
        User actualToUserResult = userMapperImpl.toUser(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertEquals("jane.doe@example.org", actualToUserResult.getEmail());
        assertEquals("Name", actualToUserResult.getName());
        assertEquals(1L, actualToUserResult.getId());
    }

    @Test
    void testToUser2() {
        assertNull(userMapperImpl.toUser(null));
    }

    @Test
    void testToUser3() {
        UserDto userDto = mock(UserDto.class);
        when(userDto.getEmail()).thenReturn("jane.doe@example.org");
        when(userDto.getName()).thenReturn("Name");
        when(userDto.getId()).thenReturn(1L);
        User actualToUserResult = userMapperImpl.toUser(userDto);
        assertEquals("jane.doe@example.org", actualToUserResult.getEmail());
        assertEquals("Name", actualToUserResult.getName());
        assertEquals(1L, actualToUserResult.getId());
        verify(userDto).getEmail();
        verify(userDto).getName();
        verify(userDto).getId();
    }
}

