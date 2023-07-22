package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;
    private static final String userIdHeader = "X-Sharer-User-Id";

    @GetMapping("/{id}")
    public ResponseEntity<Object> getByID(@RequestHeader(userIdHeader) long userId,
                                          @PathVariable long id) {
        log.info(String.format("Получен запрос @GetMapping(/bookings/%d)", id));
        return bookingClient.getByID(userId, id);
    }

    @GetMapping
    public ResponseEntity<Object> getByUser(@RequestHeader(userIdHeader) long id,
                                            @RequestParam(defaultValue = "ALL") String state,
                                            @RequestParam(defaultValue = "0") @Min(0) int from,
                                            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        log.info("Получен запрос @GetMapping(/bookings)");
        return bookingClient.getByUser(from, size, id, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getByOwner(@RequestHeader(userIdHeader) long id,
                                             @RequestParam(defaultValue = "ALL") String state,
                                             @RequestParam(defaultValue = "0") @Min(0) int from,
                                             @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        log.info("Получен запрос @GetMapping(/bookings/owner)");
        return bookingClient.getByOwner(from, size, id, state);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(userIdHeader) long id,
                                         @RequestBody @Valid BookItemRequestDto bookingInDto) {
        log.info("Получен запрос @PostMapping(/bookings)");
        return bookingClient.create(bookingInDto, id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> confirm(@RequestHeader(userIdHeader) long userId,
                                          @PathVariable long id,
                                          @RequestParam String approved) {
        log.info(String.format("Получен запрос @PatchMapping(/bookings/%d)", id));
        return bookingClient.confirm(userId, id, approved);
    }


}