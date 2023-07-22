package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
@Validated
public class ItemController {

    private final ItemClient itemClient;

    private static final String userIdHeader = "X-Sharer-User-Id";

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@RequestHeader(userIdHeader) long userId, @PathVariable long id) {
        log.info(String.format("Получен запрос @GetMapping(/items/%d)", id));
        return itemClient.getById(id, userId);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllByUser(@RequestHeader(userIdHeader) long id,
                                                @RequestParam(defaultValue = "0") @Min(0) int from,
                                                @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        log.info("Получен запрос @GetMapping(/items)");
        return itemClient.getAllByUser(from, size, id);
    }

    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody ItemDto itemDto, @RequestHeader(userIdHeader) long id) {
        log.info("Получен запрос @PostMapping(/items)");
        return itemClient.create(itemDto, id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody ItemDto itemDto, @PathVariable long id,
                                         @RequestHeader(userIdHeader) long userId) {
        log.info(String.format("Получен запрос @PatchMapping(/items/%d)", id));
        return itemClient.update(itemDto, id, userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam(name = "text") String text,
                                         @RequestParam(defaultValue = "0") @Min(0) int from,
                                         @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
                                         @RequestHeader(userIdHeader) long userId) {
        log.info(String.format("Получен запрос @GetMapping(/items/%s)", text));
        return itemClient.search(from, size, text, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        log.info(String.format("Получен запрос @DeleteMapping(/items/%d)", id));
        itemClient.deleteById(id);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader(userIdHeader) long userId, @PathVariable long id,
                                    @RequestBody @Valid CommentDto commentDto) {
        log.info(String.format("Получен запрос @PostMapping(/items/%d/comment)", id));
        return itemClient.createComment(id, userId, commentDto);
    }

}
