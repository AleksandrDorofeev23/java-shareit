package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.exceptions.EmailException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testGetById() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        UserDto userDto = new UserDto(1L, "Name", "jane.doe@example.org");

        when(userMapper.toUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userServiceImpl.getById(1L));
        verify(userRepository).findById((Long) any());
        verify(userMapper).toUserDto((User) any());
    }

    @Test
    void testGetById2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(userMapper.toUserDto((User) any())).thenThrow(new EmailException("foo"));
        assertThrows(EmailException.class, () -> userServiceImpl.getById(1L));
        verify(userRepository).findById((Long) any());
        verify(userMapper).toUserDto((User) any());
    }

    @Test
    void testGetById3() {
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(userMapper.toUserDto((User) any())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertThrows(NotFoundException.class, () -> userServiceImpl.getById(1L));
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testGetAll() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(userServiceImpl.getAll().isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void testCreate() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findByEmailContainingIgnoreCase((String) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        when(userMapper.toUser((UserDto) any())).thenReturn(user2);
        assertThrows(EmailException.class, () -> userServiceImpl.create(new UserDto(1L, "Name", "jane.doe@example.org")));
        verify(userRepository).save((User) any());
        verify(userRepository).findByEmailContainingIgnoreCase((String) any());
        verify(userMapper).toUser((UserDto) any());
    }

    @Test
    void testCreate2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findByEmailContainingIgnoreCase((String) any())).thenReturn(ofResult);
        when(userMapper.toUser((UserDto) any()))
                .thenThrow(new EmailException("Пользователь с такой почтой уже существует."));
        assertThrows(EmailException.class, () -> userServiceImpl.create(new UserDto(1L, "Name", "jane.doe@example.org")));
        verify(userRepository).findByEmailContainingIgnoreCase((String) any());
        verify(userMapper).toUser((UserDto) any());
    }

    @Test
    void testCreate3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findByEmailContainingIgnoreCase((String) any())).thenReturn(Optional.empty());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        UserDto userDto = new UserDto(1L, "Name", "jane.doe@example.org");

        when(userMapper.toUserDto((User) any())).thenReturn(userDto);
        when(userMapper.toUser((UserDto) any())).thenReturn(user1);
        assertSame(userDto, userServiceImpl.create(new UserDto(1L, "Name", "jane.doe@example.org")));
        verify(userRepository).save((User) any());
        verify(userRepository).findByEmailContainingIgnoreCase((String) any());
        verify(userMapper).toUser((UserDto) any());
        verify(userMapper).toUserDto((User) any());
    }

    @Test
    void testCreate4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findByEmailContainingIgnoreCase((String) any())).thenReturn(Optional.empty());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        when(userMapper.toUserDto((User) any())).thenThrow(new EmailException("foo"));
        when(userMapper.toUser((UserDto) any())).thenReturn(user1);
        assertThrows(EmailException.class, () -> userServiceImpl.create(new UserDto(1L, "Name", "jane.doe@example.org")));
        verify(userRepository).save((User) any());
        verify(userRepository).findByEmailContainingIgnoreCase((String) any());
        verify(userMapper).toUser((UserDto) any());
        verify(userMapper).toUserDto((User) any());
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Optional<User> ofResult1 = Optional.of(user2);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findByEmailContainingIgnoreCase((String) any())).thenReturn(ofResult1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        UserDto userDto = new UserDto(1L, "Name", "jane.doe@example.org");

        when(userMapper.toUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userServiceImpl.update(new UserDto(1L, "Name", "jane.doe@example.org"), 1L));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(userRepository).findByEmailContainingIgnoreCase((String) any());
        verify(userMapper).toUserDto((User) any());
    }

    @Test
    void testUpdate2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Optional<User> ofResult1 = Optional.of(user2);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findByEmailContainingIgnoreCase((String) any())).thenReturn(ofResult1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(userMapper.toUserDto((User) any())).thenThrow(new EmailException("foo"));
        assertThrows(EmailException.class,
                () -> userServiceImpl.update(new UserDto(1L, "Name", "jane.doe@example.org"), 1L));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(userRepository).findByEmailContainingIgnoreCase((String) any());
        verify(userMapper).toUserDto((User) any());
    }

    @Test
    void testUpdate3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(-1L);
        doNothing().when(user2).setEmail((String) any());
        doNothing().when(user2).setId(anyLong());
        doNothing().when(user2).setName((String) any());
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Optional<User> ofResult1 = Optional.of(user2);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findByEmailContainingIgnoreCase((String) any())).thenReturn(ofResult1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(userMapper.toUserDto((User) any())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertThrows(EmailException.class,
                () -> userServiceImpl.update(new UserDto(1L, "Name", "jane.doe@example.org"), 1L));
        verify(userRepository).findById((Long) any());
        verify(userRepository).findByEmailContainingIgnoreCase((String) any());
        verify(user2).getId();
        verify(user2).setEmail((String) any());
        verify(user2).setId(anyLong());
        verify(user2).setName((String) any());
    }

    @Test
    void testUpdate4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findByEmailContainingIgnoreCase((String) any())).thenReturn(Optional.empty());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(1L);
        doNothing().when(user2).setEmail((String) any());
        doNothing().when(user2).setId(anyLong());
        doNothing().when(user2).setName((String) any());
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        UserDto userDto = new UserDto(1L, "Name", "jane.doe@example.org");

        when(userMapper.toUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userServiceImpl.update(new UserDto(1L, "Name", "jane.doe@example.org"), 1L));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(userRepository).findByEmailContainingIgnoreCase((String) any());
        verify(user2).setEmail((String) any());
        verify(user2).setId(anyLong());
        verify(user2).setName((String) any());
        verify(userMapper).toUserDto((User) any());
    }

    @Test
    void testUpdate5() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn(1L);
        doNothing().when(user1).setEmail((String) any());
        doNothing().when(user1).setId(anyLong());
        doNothing().when(user1).setName((String) any());
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findByEmailContainingIgnoreCase((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(userMapper.toUserDto((User) any())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertThrows(NotFoundException.class,
                () -> userServiceImpl.update(new UserDto(1L, "Name", "jane.doe@example.org"), 1L));
        verify(userRepository).findById((Long) any());
        verify(user1).setEmail((String) any());
        verify(user1).setId(anyLong());
        verify(user1).setName((String) any());
    }

    @Test
    void testUpdate6() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(1L);
        doNothing().when(user2).setEmail((String) any());
        doNothing().when(user2).setId(anyLong());
        doNothing().when(user2).setName((String) any());
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Optional<User> ofResult1 = Optional.of(user2);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findByEmailContainingIgnoreCase((String) any())).thenReturn(ofResult1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        UserDto userDto = new UserDto(1L, "Name", "jane.doe@example.org");

        when(userMapper.toUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userServiceImpl.update(new UserDto(), 1L));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(user2).setEmail((String) any());
        verify(user2).setId(anyLong());
        verify(user2).setName((String) any());
        verify(userMapper).toUserDto((User) any());
    }

    @Test
    void testDeleteById() {
        doNothing().when(userRepository).deleteById((Long) any());
        userServiceImpl.deleteById(1L);
        verify(userRepository).deleteById((Long) any());
    }

    @Test
    void testDeleteById2() {
        doThrow(new EmailException("foo")).when(userRepository).deleteById((Long) any());
        assertThrows(EmailException.class, () -> userServiceImpl.deleteById(1L));
        verify(userRepository).deleteById((Long) any());
    }
}

