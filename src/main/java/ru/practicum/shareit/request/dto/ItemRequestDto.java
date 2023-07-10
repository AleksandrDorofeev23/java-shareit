package ru.practicum.shareit.request.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemRequestDto {
    private int id;
    private String description;
    private long requestorId;
    private LocalDateTime created;
}
