package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto create(ItemRequestDto itemRequestDto, Long id);

    List<ItemRequestDto> getByOwner(int from, int size, Long id);

    List<ItemRequestDto> getALL(int from, int size, Long id);

    ItemRequestDto getByID(Long userId, Long id);
}
