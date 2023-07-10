package ru.practicum.shareit.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
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
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.storage.UserStorage;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @MockBean
    private UserStorage userStorage;

    @Test
    void testGetById() {
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        UserDto actualById = userServiceImpl.getById(1L);
        assertEquals("jane.doe@example.org", actualById.getEmail());
        assertEquals("Name", actualById.getName());
        assertEquals(1L, actualById.getId());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testGetAll() {
        when(userStorage.getAll()).thenReturn(new ArrayList<>());
        assertTrue(userServiceImpl.getAll().isEmpty());
        verify(userStorage).getAll();
    }

    @Test
    void testGetAll2() {
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User(1L, "Name", "jane.doe@example.org"));
        when(userStorage.getAll()).thenReturn(userList);
        Collection<UserDto> actualAll = userServiceImpl.getAll();
        assertEquals(1, actualAll.size());
        UserDto getResult = ((List<UserDto>) actualAll).get(0);
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertEquals("Name", getResult.getName());
        assertEquals(1L, getResult.getId());
        verify(userStorage).getAll();
    }

    @Test
    void testGetAll3() {
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User(1L, "Name", "jane.doe@example.org"));
        userList.add(new User(1L, "Name", "jane.doe@example.org"));
        when(userStorage.getAll()).thenReturn(userList);
        Collection<UserDto> actualAll = userServiceImpl.getAll();
        assertEquals(2, actualAll.size());
        UserDto getResult = ((List<UserDto>) actualAll).get(0);
        assertEquals("Name", getResult.getName());
        UserDto getResult1 = ((List<UserDto>) actualAll).get(1);
        assertEquals("Name", getResult1.getName());
        assertEquals(1L, getResult1.getId());
        assertEquals("jane.doe@example.org", getResult1.getEmail());
        assertEquals(1L, getResult.getId());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        verify(userStorage).getAll();
    }

    @Test
    void testGetAll4() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getName()).thenReturn("Name");
        when(user.getId()).thenReturn(1L);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userStorage.getAll()).thenReturn(userList);
        Collection<UserDto> actualAll = userServiceImpl.getAll();
        assertEquals(1, actualAll.size());
        UserDto getResult = ((List<UserDto>) actualAll).get(0);
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertEquals("Name", getResult.getName());
        assertEquals(1L, getResult.getId());
        verify(userStorage).getAll();
        verify(user).getEmail();
        verify(user).getName();
        verify(user).getId();
    }

    @Test
    void testCreate() {
        when(userStorage.create((User) any())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        UserDto actualCreateResult = userServiceImpl.create(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertEquals("jane.doe@example.org", actualCreateResult.getEmail());
        assertEquals("Name", actualCreateResult.getName());
        assertEquals(1L, actualCreateResult.getId());
        verify(userStorage).create((User) any());
    }

    @Test
    void testCreate2() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getName()).thenReturn("Name");
        when(user.getId()).thenReturn(1L);
        when(userStorage.create((User) any())).thenReturn(user);
        UserDto actualCreateResult = userServiceImpl.create(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertEquals("jane.doe@example.org", actualCreateResult.getEmail());
        assertEquals("Name", actualCreateResult.getName());
        assertEquals(1L, actualCreateResult.getId());
        verify(userStorage).create((User) any());
        verify(user).getEmail();
        verify(user).getName();
        verify(user).getId();
    }

    @Test
    void testUpdate() {
        when(userStorage.update((UserDto) any(), anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        UserDto actualUpdateResult = userServiceImpl.update(new UserDto(1L, "Name", "jane.doe@example.org"), 1L);
        assertEquals("jane.doe@example.org", actualUpdateResult.getEmail());
        assertEquals("Name", actualUpdateResult.getName());
        assertEquals(1L, actualUpdateResult.getId());
        verify(userStorage).update((UserDto) any(), anyLong());
    }

    @Test
    void testUpdate2() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getName()).thenReturn("Name");
        when(user.getId()).thenReturn(1L);
        when(userStorage.update((UserDto) any(), anyLong())).thenReturn(user);
        UserDto actualUpdateResult = userServiceImpl.update(new UserDto(1L, "Name", "jane.doe@example.org"), 1L);
        assertEquals("jane.doe@example.org", actualUpdateResult.getEmail());
        assertEquals("Name", actualUpdateResult.getName());
        assertEquals(1L, actualUpdateResult.getId());
        verify(userStorage).update((UserDto) any(), anyLong());
        verify(user).getEmail();
        verify(user).getName();
        verify(user).getId();
    }

    @Test
    void testDeleteById() {
        doNothing().when(userStorage).deleteById(anyLong());
        userServiceImpl.deleteById(1L);
        verify(userStorage).deleteById(anyLong());
    }
}

