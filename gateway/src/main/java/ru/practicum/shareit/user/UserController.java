package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    private final UserClient userClient;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        log.info(String.format("Получен запрос @GetMapping(/users/%d)", id));
        return userClient.getById(id);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        log.info("Получен запрос @GetMapping(/users)");
        return userClient.getAll();
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody UserDto userDto) {
        log.info("Получен запрос @PostMapping(/users)");
        return userClient.create(userDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody UserDto userDto, @PathVariable long id) {
        log.info(String.format("Получен запрос @PatchMapping(/users/%d)", id));
        return userClient.update(userDto, id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable long id) {
        log.info(String.format("Получен запрос @DeleteMapping(/users/%d)", id));
        userClient.deleteById(id);
    }
}
