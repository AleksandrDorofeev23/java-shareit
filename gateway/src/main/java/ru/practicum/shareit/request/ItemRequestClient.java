package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Map;

@Service
public class ItemRequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";
    private static final String CACHE = "requests";

    @Autowired
    public ItemRequestClient(@Value("${shareit-server-container.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> create(ItemRequestDto itemRequestDto, long id) {
        return post("", id, itemRequestDto);
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> getByOwner(int from, int size, long id) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("?from={from}&&size={size}", id, parameters);
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> getALL(int from, int size, long id) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("/all?from={from}&&size={size}", id, parameters);
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> getByID(long userId, long id) {
        return get("/" + userId, id);
    }
}
