package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class ItemStorageImpl implements ItemStorage {
    private final Map<Long, Item> items = new HashMap<>();
    private long id = 0;

    @Override
    public Item getById(long id) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("Такой вещи нет");
        }
        return items.get(id);
    }

    @Override
    public Collection<Item> getAllByUser() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Item create(Item item) {
        item.setId(++id);
        items.put(item.getId(), item);
        return items.get(item.getId());
    }

    @Override
    public Item update(Item item) {
        if (!items.containsKey(item.getId())) {
            throw new NotFoundException("Такой вещи нет");
        }
        items.put(item.getId(), item);
        return items.get(item.getId());
    }

    @Override
    public void deleteById(long id) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("Такой вещи нет");
        }
        items.remove(id);
    }
}
