package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemPlusDto;

import java.util.Collection;

public interface ItemService {

    ItemPlusDto getById(long id, long userId);

    Collection<ItemPlusDto> getAllByUser(long id);

    ItemDto create(ItemDto itemDto, long id);

    ItemDto update(ItemDto itemDto, long itemId, long id);

    void deleteById(long id);

    Collection<ItemDto> search(String text);

    CommentDto createComment(long id, long userId, CommentDto commentDto);
}
