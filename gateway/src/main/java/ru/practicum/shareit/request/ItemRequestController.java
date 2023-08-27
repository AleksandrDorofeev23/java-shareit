package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
@Slf4j
@Validated
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;
    private static final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(userIdHeader) Long id,
                                         @RequestBody @Valid ItemRequestDto itemRequestDto) {
        log.info("Получен запрос @PostMapping(/requests/)");
        return itemRequestClient.create(itemRequestDto, id);
    }

    @GetMapping
    public ResponseEntity<Object> getByOwner(
            @RequestHeader(userIdHeader) Long id,
            @RequestParam(defaultValue = "0") @Min(0) int from,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        log.info("Получен запрос @GetMapping(/requests/)");
        return itemRequestClient.getByOwner(from, size, id);
    }

    @GetMapping("all")
    public ResponseEntity<Object> getALL(
            @RequestHeader(userIdHeader) Long id,
            @RequestParam(defaultValue = "0") @Min(0) int from,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        log.info("Получен запрос @GetMapping(/requests/all)");
        return itemRequestClient.getALL(from, size, id);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getByID(
            @RequestHeader(userIdHeader) Long userId,
            @PathVariable Long id) {
        log.info(String.format("Получен запрос @GetMapping(/requests/%d)", id));
        return itemRequestClient.getByID(userId, id);
    }
}
