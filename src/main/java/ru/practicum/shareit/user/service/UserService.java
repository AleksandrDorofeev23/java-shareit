package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.Collection;

public interface UserService {

    UserDto getById(long id);

    Collection<UserDto> getAll();

    UserDto create(UserDto userDto);

    UserDto update(User user, long id);

    void deleteById(long id);
}
