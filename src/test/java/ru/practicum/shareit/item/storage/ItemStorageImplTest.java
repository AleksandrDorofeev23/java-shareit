package ru.practicum.shareit.item.storage;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.item.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

@ContextConfiguration(classes = {ItemStorageImpl.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemStorageImplTest {
    @Autowired
    private ItemStorageImpl itemStorageImpl;

    @Test
    void testCreate() {
        User owner = new User(1L, "Name", "jane.doe@example.org");

        Item item = new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest());

        assertSame(item, itemStorageImpl.create(item));
    }

    @Test
    void testCreate2() {
        User owner = mock(User.class);
        Item item = new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest());

        assertSame(item, itemStorageImpl.create(item));
    }

    @Test
    void testDeleteById() {
        assertThrows(NotFoundException.class, () -> itemStorageImpl.deleteById(1L));
        assertThrows(NotFoundException.class, () -> itemStorageImpl.deleteById(2L));
    }
}

