package ru.practicum.shareit.user;

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
import ru.practicum.shareit.user.dto.UserDto;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";
    private static final String CACHE = "users";

    @Autowired
    public UserClient(@Value("${shareit-server-container.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> create(UserDto userDto) {
        return post("", userDto);
    }

    @CachePut(CACHE)
    public ResponseEntity<Object> update(UserDto userDto, long id) {
        return patch("/" + id, userDto);
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> getById(long id) {
        return get("/" + id);
    }

    @CacheEvict(CACHE)
    public ResponseEntity<Object> deleteById(long id) {
        return delete("/" + id);
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> getAll() {
        return get("");
    }
}
