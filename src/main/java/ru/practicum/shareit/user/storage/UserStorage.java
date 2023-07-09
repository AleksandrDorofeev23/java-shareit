package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.Collection;

public interface UserStorage {

    User getById(long id);

    Collection<User> getAll();

    User create(User user);

    User update(UserDto userDto, long id);

    void deleteById(long id);
}
