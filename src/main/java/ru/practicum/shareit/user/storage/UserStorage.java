package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserStorage {

    User getById(long id);

    Collection<User> getAll();

    User create(User user);

    User update(User user, long id);

    void deleteById(long id);
}
