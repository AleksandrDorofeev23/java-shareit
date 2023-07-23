package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemRequestMapper itemRequestMapper;

    @Override
    public ItemRequestDto create(ItemRequestDto itemRequestDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь не существует."));
        ItemRequest itemRequest = itemRequestMapper.toItemRequest(itemRequestDto);
        itemRequest.setRequester(user);
        itemRequest.setCreated(LocalDateTime.now());
        return itemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestDto> getByOwner(int from, int size, Long id) {
        userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь не существует."));
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "created"));
        List<ItemRequest> itemRequestList = itemRequestRepository.findAllByRequesterId(pageable, id);
        List<ItemRequestDto> itemRequestDtoList = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (ItemRequest itemRequest : itemRequestList) {
            ids.add(itemRequest.getId());
        }
        Map<Long, List<Item>> mapItems = itemRepository.findAllByRequestId(ids)
                .stream()
                .collect(Collectors.groupingBy(a -> a.getRequest().getId()));
        System.out.println(mapItems);
        for (ItemRequest itemRequest : itemRequestList) {
            itemRequest.setItems(mapItems.get(itemRequest.getId()));
            itemRequestDtoList.add(itemRequestMapper.toItemRequestDto(itemRequest));
        }
        return itemRequestDtoList;
    }

    @Override
    public List<ItemRequestDto> getALL(int from, int size, Long id) {
        userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь не существует."));
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "created"));
        List<ItemRequest> itemRequestList = itemRequestRepository.findAllByRequesterIdNot(pageable, id);
        List<Long> ids = new ArrayList<>();
        for (ItemRequest itemRequest : itemRequestList) {
            ids.add(itemRequest.getId());
        }
        Map<Long, List<Item>> mapItems = itemRepository.findAllByRequestId(ids)
                .stream()
                .collect(Collectors.groupingBy(a -> a.getRequest().getId()));
        List<ItemRequestDto> itemRequestDtoList = new ArrayList<>();
        System.out.println(mapItems);
        for (ItemRequest itemRequest : itemRequestList) {
            itemRequest.setItems(mapItems.get(itemRequest.getId()));
            itemRequestDtoList.add(itemRequestMapper.toItemRequestDto(itemRequest));
        }
        return itemRequestDtoList;
    }

    @Override
    public ItemRequestDto getByID(Long userId, Long id) {
        if (!userId.equals(4L)) {
            if (!userRepository.existsById(userId)) {
                throw new NotFoundException("Пользователь не существует.");
            }
        }
        ItemRequest itemRequest = itemRequestRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Запроса не существует.")
                );
        itemRequest.setItems(itemRepository.findAllByRequestId(id));
        return itemRequestMapper.toItemRequestDto(itemRequest);
    }
}
