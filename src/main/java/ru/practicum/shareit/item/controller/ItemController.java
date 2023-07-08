package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    private final String userIdHeader = "X-Sharer-User-Id";

    @GetMapping("/{id}")
    public ItemDto getById(@PathVariable long id) {
        return itemService.getById(id);
    }

    @GetMapping()
    public Collection<ItemDto> getAllByUser(@RequestHeader(userIdHeader) long id) {
        return itemService.getAllByUser(id);
    }

    @PostMapping()
    public ItemDto create(@Valid @RequestBody ItemDto itemDto, @RequestHeader(userIdHeader) long id) {
        return itemService.create(itemDto, id);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestBody Item item, @PathVariable long id,
                          @RequestHeader(userIdHeader) long userId) {
        return itemService.update(item, id, userId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestParam(name = "text") String text) {
        return itemService.search(text);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        itemService.deleteById(id);
    }

}
