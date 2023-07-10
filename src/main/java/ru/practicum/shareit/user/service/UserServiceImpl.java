package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.UserMappper;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

import static ru.practicum.shareit.user.model.UserMappper.toUserDto;
import static ru.practicum.shareit.user.model.UserMappper.toUser;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Override
    public UserDto getById(long id) {
        return toUserDto(userStorage.getById(id));
    }

    @Override
    public Collection<UserDto> getAll() {
        return userStorage.getAll()
                .stream()
                .map(UserMappper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserDto userDto) {
        return toUserDto(userStorage.create(toUser(userDto)));
    }

    public UserDto update(UserDto userDto, long id) {
        return toUserDto(userStorage.update(userDto, id));
    }

    @Override
    public void deleteById(long id) {
        userStorage.deleteById(id);
    }
}
