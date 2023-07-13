package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.dto.BookingInDto;
import ru.practicum.shareit.booking.model.dto.BookingOutDto;

import java.util.List;

public interface BookingService {

    BookingOutDto getById(long userId, long id);

    List<BookingOutDto> getByUser(long userId, String state);

    List<BookingOutDto> getByOwner(long userId, String state);

    BookingOutDto create(BookingInDto bookingInDto, long id);

    BookingOutDto confirm(long userId, long id, boolean approved);

}