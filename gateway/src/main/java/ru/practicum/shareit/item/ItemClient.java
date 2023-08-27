package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.*;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";
    private static final String CACHE = "items";

    @Autowired
    public ItemClient(@Value("${shareit-server-container.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> create(ItemDto itemDto, long id) {
        return post("", id, itemDto);
    }

    @CachePut(CACHE)
    public ResponseEntity<Object> update(ItemDto itemDto, long id, long userId) {
        return patch("/" + id, userId, itemDto);
    }

    @CachePut(CACHE)
    public ResponseEntity<Object> getById(long id, long userId) {
        return get("/" + id, userId);
    }

    @CachePut(CACHE)
    public ResponseEntity<Object> getAllByUser(int from, int size, long id) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("?from={from}&size={size}", id, parameters);
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> search(int from, int size, String text, long userId) {
        Map<String, Object> parameters = Map.of(
                "text", text,
                "from", from,
                "size", size
        );
        return get("/search?text={text}&from={from}&size={size}", userId, parameters);
    }

    @CachePut(CACHE)
    public ResponseEntity<Object> createComment(long id, long userId, CommentDto commentDto) {
        return post("/" + id + "/comment", userId, commentDto);
    }

    @CacheEvict(CACHE)
    public ResponseEntity<Object> deleteById(long id) {
        return delete("/" + id);
    }
}
