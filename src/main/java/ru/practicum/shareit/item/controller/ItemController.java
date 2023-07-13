package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemPlusDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    private static final String userIdHeader = "X-Sharer-User-Id";

    @GetMapping("/{id}")
    public ItemPlusDto getById(@RequestHeader(userIdHeader) long userId, @PathVariable long id) {
        log.info(String.format("Получен запрос @GetMapping(/items/%d)", id));
        return itemService.getById(id, userId);
    }

    @GetMapping()
    public Collection<ItemPlusDto> getAllByUser(@RequestHeader(userIdHeader) long id) {
        log.info("Получен запрос @GetMapping(/items)");
        return itemService.getAllByUser(id);
    }

    @PostMapping()
    public ItemDto create(@Valid @RequestBody ItemDto itemDto, @RequestHeader(userIdHeader) long id) {
        log.info("Получен запрос @PostMapping(/items)");
        return itemService.create(itemDto, id);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestBody ItemDto itemDto, @PathVariable long id,
                          @RequestHeader(userIdHeader) long userId) {
        log.info(String.format("Получен запрос @PatchMapping(/items/%d)", id));
        return itemService.update(itemDto, id, userId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestParam(name = "text") String text) {
        log.info(String.format("Получен запрос @GetMapping(/items/%s)", text));
        return itemService.search(text);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        log.info(String.format("Получен запрос @DeleteMapping(/items/%d)", id));
        itemService.deleteById(id);
    }

    @PostMapping("/{id}/comment")
    public CommentDto createComment(@RequestHeader(userIdHeader) long userId, @PathVariable long id,
                                                    @RequestBody @Valid CommentDto commentDto) {
        log.info(String.format("Получен запрос @PostMapping(/items/%d/comment)", id));
        return itemService.createComment(id, userId, commentDto);
    }

}
