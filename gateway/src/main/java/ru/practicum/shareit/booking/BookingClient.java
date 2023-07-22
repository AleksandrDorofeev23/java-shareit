package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.exception.StateException;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";
    private static final String CACHE = "bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server-container.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> create(BookItemRequestDto bookingInDto, long id) {
        return post("", id, bookingInDto);
    }

    @CachePut(CACHE)
    public ResponseEntity<Object> confirm(long userId, long id, String approved) {
        Map<String, Object> parameters = Map.of(
                "approved", approved
        );
        return patch("/" + id + "?approved={approved}", userId, parameters, null);
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> getByID(long userId, long id) {
        return get("/" + id, userId);
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> getByUser(int from, int size, long id, String state) {
        try {
            BookingState.valueOf(state.toUpperCase());
        } catch (Exception e) {
            throw new StateException("Unknown state: UNSUPPORTED_STATUS");
        }
        Map<String, Object> parameters = Map.of(
                "state", state,
                "from", from,
                "size", size
        );
        return get("?state={state}&&from={from}&&size={size}", id, parameters);
    }

    @Cacheable(CACHE)
    public ResponseEntity<Object> getByOwner(int from, int size, long id, String state) {
        try {
            BookingState.valueOf(state.toUpperCase());
        } catch (Exception e) {
            throw new StateException("Unknown state: UNSUPPORTED_STATUS");
        }
        Map<String, Object> parameters = Map.of(
                "state", state,
                "from", from,
                "size", size
        );
        return get("/owner?state={state}&&from={from}&&size={size}", id, parameters);
    }

}
