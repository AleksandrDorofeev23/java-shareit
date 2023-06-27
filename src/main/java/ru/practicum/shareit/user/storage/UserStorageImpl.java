package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.exceptions.NotFoundException;
import ru.practicum.shareit.user.exceptions.ValidException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Component
public class UserStorageImpl implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long id = 0;

    @Override
    public User getById(long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Такого пользователя нет");
        }
        return users.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User create(User user) {
        if (users.values()
                .stream()
                .anyMatch(a -> a.getEmail().equalsIgnoreCase(user.getEmail()))) {
            throw new ValidException("Пользователь с такой почтой уже существует");
        }
        user.setId(++id);
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public User update(User user, long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Такого пользователя нет");
        }
        User oldUser = users.get(id);
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            if (!user.getEmail().equals(oldUser.getEmail())) {
                if (users.values()
                        .stream()
                        .noneMatch(a -> a.getEmail().equalsIgnoreCase(user.getEmail()))) {
                    oldUser.setEmail(user.getEmail());
                } else {
                    throw new ValidException("Пользователь с такой почтой уже существует");
                }
            }
        }
        users.put(oldUser.getId(), oldUser);
        return users.get(oldUser.getId());
    }

    public void deleteById(long id) {
        users.remove(id);
    }

}
