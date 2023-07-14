package ru.practicum.shareit.item.model.dto;

import lombok.Data;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.user.model.dto.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class ItemPlusDto {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private UserDto owner;
    private BookingDto lastBooking;
    private BookingDto nextBooking;
    private Set<CommentDto> comments = new HashSet<>();
}
