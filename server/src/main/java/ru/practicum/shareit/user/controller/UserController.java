package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable long id) {
        log.info(String.format("Получен запрос @GetMapping(/users/%d)", id));
        return userService.getById(id);
    }

    @GetMapping
    public Collection<UserDto> getAll() {
        log.info("Получен запрос @GetMapping(/users)");
        return userService.getAll();
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto userDto) {
        log.info("Получен запрос @PostMapping(/users)");
        return userService.create(userDto);
    }

    @PatchMapping("/{id}")
    public UserDto update(@RequestBody UserDto userDto, @PathVariable long id) {
        log.info(String.format("Получен запрос @PatchMapping(/users/%d)", id));
        return userService.update(userDto, id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable long id) {
        log.info(String.format("Получен запрос @DeleteMapping(/users/%d)", id));
        userService.deleteById(id);
    }
}
