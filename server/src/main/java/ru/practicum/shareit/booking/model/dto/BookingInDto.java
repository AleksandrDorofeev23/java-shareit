package ru.practicum.shareit.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingInDto {
    private long itemId;
    private LocalDateTime start;
    private LocalDateTime end;
}
