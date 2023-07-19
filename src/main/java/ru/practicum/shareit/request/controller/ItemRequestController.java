package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
@Slf4j
@Validated
public class ItemRequestController {
    private final ItemRequestService itemRequestService;
    private static final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping
    public ItemRequestDto create(@RequestHeader(userIdHeader) Long id,
                                 @RequestBody @Valid ItemRequestDto itemRequestDto) {
        log.info("Получен запрос @PostMapping(/requests/)");
        return itemRequestService.create(itemRequestDto, id);
    }

    @GetMapping
    public List<ItemRequestDto> getByOwner(
            @RequestHeader(userIdHeader) Long id,
            @RequestParam(defaultValue = "0") @Min(0) int from,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        log.info("Получен запрос @GetMapping(/requests/)");
        return itemRequestService.getByOwner(from, size, id);
    }

    @GetMapping("all")
    public List<ItemRequestDto> getALL(
            @RequestHeader(userIdHeader) Long id,
            @RequestParam(defaultValue = "0") @Min(0) int from,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        log.info("Получен запрос @GetMapping(/requests/all)");
        return itemRequestService.getALL(from, size, id);
    }

    @GetMapping("{id}")
    public ItemRequestDto getByID(
            @RequestHeader(userIdHeader) Long userId,
            @PathVariable Long id) {
        log.info(String.format("Получен запрос @GetMapping(/requests/%d)", id));
        return itemRequestService.getByID(userId, id);
    }
}
