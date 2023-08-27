package ru.practicum.shareit.item.model.dto;

import lombok.Data;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.HashSet;
import java.util.Set;

@Data
public class ItemPlusDto {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private UserDto owner;
    private BookingDto lastBooking;
    private BookingDto nextBooking;
    private Set<CommentDto> comments = new HashSet<>();
}
