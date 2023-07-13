package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.model.dto.BookingInDto;
import ru.practicum.shareit.booking.model.dto.BookingOutDto;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.utils.BookingMapper;
import ru.practicum.shareit.exceptions.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.utils.ItemMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.utils.UserMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final BookingMapper bookingMapper;
    private final UserMapper userMapper;
    private final ItemMapper itemMapper;

    @Transactional(readOnly = true)
    @Override
    public BookingOutDto getById(long userId, long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Аренда не найдена"));
        if (!(booking.getItem().getOwner().getId() == userId || booking.getBooker().getId() == userId)) {
            throw new AccessException("У пользователя нет прав на просмотр.");
        }
        return bookingMapper.toBookingOutDto(booking);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingOutDto> getByUser(long id, String stateStr) {
        userMapper.toUser(userService.getById(id));
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Booking> bookingList = new ArrayList<>();
        List<BookingOutDto> bookingOutDtoList = new ArrayList<>();
        State state;
        try {
            state = State.valueOf(stateStr.toUpperCase());
        } catch (Exception e) {
            throw new StateException("Такого типа нет.");
        }
        switch (state) {
            case ALL:
                bookingList = bookingRepository.findAllByBookerIdOrderByStartDesc(id);
                break;
            case CURRENT:
                bookingList = bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(id, localDateTime, localDateTime);
                break;
            case FUTURE:
                bookingList = bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(id, localDateTime);
                break;
            case PAST:
                bookingList = bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(id, localDateTime);
                break;
            case WAITING:
                bookingList = bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(id, Status.WAITING);
                break;
            case REJECTED:
                bookingList = bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(id, Status.REJECTED);
                break;
        }
        for (Booking booking : bookingList) {
            bookingOutDtoList.add(bookingMapper.toBookingOutDto(booking));
        }
        return bookingOutDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingOutDto> getByOwner(long id, String stateStr) {
        userMapper.toUser(userService.getById(id));
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Booking> bookingList = new ArrayList<>();
        List<BookingOutDto> bookingOutDtoList = new ArrayList<>();
        State state;
        try {
            state = State.valueOf(stateStr.toUpperCase());
        } catch (Exception e) {
            throw new StateException("Такого типа нет.");
        }
        switch (state) {
            case ALL:
                bookingList = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(id);
                break;
            case CURRENT:
                bookingList = bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(id, localDateTime, localDateTime);
                break;
            case FUTURE:
                bookingList = bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(id, localDateTime);
                break;
            case PAST:
                bookingList = bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(id, localDateTime);
                break;
            case WAITING:
                bookingList = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(id, Status.WAITING);
                break;
            case REJECTED:
                bookingList = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(id, Status.REJECTED);
                break;
        }
        for (Booking booking : bookingList) {
            bookingOutDtoList.add(bookingMapper.toBookingOutDto(booking));
        }
        return bookingOutDtoList;
    }

    @Transactional
    @Override
    public BookingOutDto create(BookingInDto bookingInDto, long id) {
        if (bookingInDto.getStart().equals(bookingInDto.getEnd()) || bookingInDto.getStart().isBefore(LocalDateTime.now())
                || bookingInDto.getStart().isAfter(bookingInDto.getEnd())) {
            throw new DateTimeException("Даты бронирования отсутствуют или некорректны.");
        }
        Booking booking = bookingMapper.toBooking(bookingInDto);
        User user = userMapper.toUser(userService.getById(id));
        Item item = itemMapper.toItem(itemService.getById(bookingInDto.getItemId(), id));
        booking.setStatus(Status.WAITING);
        booking.setBooker(user);
        if (!item.getAvailable()) {
            throw new NotAvailableException("Вещь уже занята.");
        }
        if (user.getId() == item.getOwner().getId()) {
            throw new AccessException("Владелец не может бронировать свои вещи");
        }
        booking.setItem(item);
        return bookingMapper.toBookingOutDto(bookingRepository.save(booking));
    }

    @Transactional
    @Override
    public BookingOutDto confirm(long userId, long id, boolean approved) {
        User owner = userMapper.toUser(userService.getById(userId));
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Аренда не найдена."));
        if (booking.getItem().getOwner().getId() != owner.getId()) {
            throw new NotFoundException("У пользователя нет такой вещи.");
        }
        if (!booking.getStatus().equals(Status.WAITING)) {
            throw new IllegalArgumentException("Статус уже подтвержден.");
        }
        try {
            if (approved) {
                booking.setStatus(Status.APPROVED);
            } else {
                booking.setStatus(Status.REJECTED);
            }
            booking = bookingRepository.save(booking);
        } catch (Exception e) {
            throw new IllegalArgumentException("Неверный статус.");
        }
        return bookingMapper.toBookingOutDto(booking);
    }

}
