package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.Collection;

public interface ItemService {
    ItemDto getById(long id);

    Collection<ItemDto> getAllByUser(long id);

    ItemDto create(ItemDto itemDto, long id);

    ItemDto update(ItemDto itemDto, long itemId, long id);

    void deleteById(long id);

    Collection<ItemDto> search(String text);
}
