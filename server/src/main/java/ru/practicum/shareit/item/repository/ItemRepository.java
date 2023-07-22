package ru.practicum.shareit.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAllByOwnerId(Pageable pageable, long id);

    List<Item> findAllByRequestId(long id);

    @Query("select i " +
            "from Item as i " +
            "join i.request as r " +
            "where r.id in ?1")
    List<Item> findAllByRequestId(List<Long> ids);
}
