package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.EmailException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.utils.UserMapper;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public UserDto getById(long id) {
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Пользователь не найден")));
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        User user;
        Optional<User> oldUser = userRepository.findByEmailContainingIgnoreCase(userDto.getEmail());
        user = userRepository.save(userMapper.toUser(userDto));
        if (oldUser.isPresent()) {
            throw new EmailException("Пользователь с такой почтой уже существует.");
        }
        return userMapper.toUserDto(user);

    }

    @Transactional
    public UserDto update(UserDto userDto, long id) {
        User oldUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (userDto.getName() != null) {
            oldUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            Optional<User> oldUserEmail = userRepository.findByEmailContainingIgnoreCase(userDto.getEmail());
            if (oldUserEmail.isEmpty() || oldUser.getId() == oldUserEmail.get().getId()) {
                oldUser.setEmail(userDto.getEmail());
            } else {
                throw new EmailException("Пользователь с такой почтой уже существует");
            }
        }
        userRepository.save(oldUser);
        return userMapper.toUserDto(oldUser);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
