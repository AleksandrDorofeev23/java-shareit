package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemStorage {
    Item getById(long id);

    Collection<Item> getAllByUser();

    Item create(Item item);

    Item update(Item item);

    void deleteById(long id);
}
