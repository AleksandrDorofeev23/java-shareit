package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.dto.BookingInDto;
import ru.practicum.shareit.booking.model.dto.BookingOutDto;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Slf4j
public class BookingController {
    private final BookingService bookingService;
    private static final String userIdHeader = "X-Sharer-User-Id";

    @GetMapping("/{id}")
    public BookingOutDto getByID(@RequestHeader(userIdHeader) long userId,
                                 @PathVariable long id) {
        log.info(String.format("Получен запрос @GetMapping(/bookings/%d)", id));
        return bookingService.getById(userId, id);
    }

    @GetMapping
    public List<BookingOutDto> getByUser(@RequestHeader(userIdHeader) long id,
                                         @RequestParam(defaultValue = "ALL") String state) {
        log.info("Получен запрос @GetMapping(/bookings)");
        return bookingService.getByUser(id, state);
    }

    @GetMapping("/owner")
    public List<BookingOutDto> getByOwner(@RequestHeader(userIdHeader) long id,
                                          @RequestParam(defaultValue = "ALL") String state) {
        log.info("Получен запрос @GetMapping(/bookings/owner)");
        return bookingService.getByOwner(id, state);
    }

    @PostMapping
    public BookingOutDto create(@RequestHeader(userIdHeader) long id,
                                @RequestBody @Valid BookingInDto bookingInDto) {
        log.info("Получен запрос @PostMapping(/bookings)");
        return bookingService.create(bookingInDto, id);
    }

    @PatchMapping("/{id}")
    public BookingOutDto confirm(@RequestHeader(userIdHeader) long userId,
                                 @PathVariable long id,
                                 @RequestParam boolean approved) {
        log.info(String.format("Получен запрос @PatchMapping(/bookings/%d)", id));
        return bookingService.confirm(userId, id, approved);
    }


}
