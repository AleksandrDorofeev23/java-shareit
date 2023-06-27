package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.model.ItemMapper.toItem;
import static ru.practicum.shareit.item.model.ItemMapper.toItemDto;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public ItemDto getById(long id) {
        return toItemDto(itemStorage.getById(id));
    }

    @Override
    public Collection<ItemDto> getAllByUser(long id) {
        return itemStorage.getAllByUser()
                .stream()
                .filter(a -> Objects.equals(a.getOwner().getId(), id))
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto create(ItemDto itemDto, long id) {
        Item newItem = toItem(itemDto);
        User owner = userStorage.getById(id);
        newItem.setOwner(owner);
        return toItemDto(itemStorage.create(newItem));
    }

    @Override
    public ItemDto update(Item item, long itemId, long id) {
        userStorage.getById(id);
        Item oldItem = itemStorage.getById(itemId);
        if (oldItem.getOwner().getId() != id) {
            throw new NotFoundException("Пользователю не принадлежит этот предмет");
        }
        if (item.getName() != null) {
            oldItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            oldItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            oldItem.setAvailable(item.getAvailable());
        }
        return toItemDto(itemStorage.update(oldItem));
    }

    @Override
    public void deleteById(long id) {
        itemStorage.deleteById(id);
    }

    @Override
    public Collection<ItemDto> search(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.getAllByUser()
                .stream()
                .filter(a -> a.getDescription().toLowerCase().contains(text.toLowerCase()) && a.getAvailable())
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

}
