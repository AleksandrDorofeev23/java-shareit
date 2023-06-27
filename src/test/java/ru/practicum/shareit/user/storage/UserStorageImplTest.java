package ru.practicum.shareit.user.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.user.exceptions.NotFoundException;
import ru.practicum.shareit.user.model.User;

@ContextConfiguration(classes = {UserStorageImpl.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserStorageImplTest {
    @Autowired
    private UserStorageImpl userStorageImpl;

    @Test
    void testGetById() {
        assertThrows(NotFoundException.class, () -> userStorageImpl.getById(1L));
    }

    @Test
    void testGetAll() {
        assertTrue(userStorageImpl.getAll().isEmpty());
    }

    @Test
    void testCreate() {
        User user = new User(1L, "Name", "jane.doe@example.org");

        User actualCreateResult = userStorageImpl.create(user);
        assertSame(user, actualCreateResult);
        assertEquals(1L, actualCreateResult.getId());
        assertEquals(1, userStorageImpl.getAll().size());
    }

    @Test
    void testCreate2() {
        User user = new User(2L, "Name", "jane.doe@example.org");

        User actualCreateResult = userStorageImpl.create(user);
        assertSame(user, actualCreateResult);
        assertEquals(1L, actualCreateResult.getId());
        assertEquals(1, userStorageImpl.getAll().size());
    }

    @Test
    void testCreate3() {
        User user = new User(1L, "Name", "john.smith@example.org");

        User actualCreateResult = userStorageImpl.create(user);
        assertSame(user, actualCreateResult);
        assertEquals(1L, actualCreateResult.getId());
        assertEquals(1, userStorageImpl.getAll().size());
    }

    @Test
    void testCreate4() {
        User user = new User(1L, "Name", "prof.einstein@example.org");

        User actualCreateResult = userStorageImpl.create(user);
        assertSame(user, actualCreateResult);
        assertEquals(1L, actualCreateResult.getId());
        assertEquals(1, userStorageImpl.getAll().size());
    }

    @Test
    void testUpdate() {
        assertThrows(NotFoundException.class,
                () -> userStorageImpl.update(new User(1L, "Name", "jane.doe@example.org"), 1L));
    }

    @Test
    void testDeleteById() {
        userStorageImpl.deleteById(1L);
        assertTrue(userStorageImpl.getAll().isEmpty());
    }
}

