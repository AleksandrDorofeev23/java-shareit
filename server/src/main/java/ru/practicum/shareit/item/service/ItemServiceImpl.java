package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidException;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemPlusDto;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final ItemMapper itemMapper;
    private final CommentMapper commentMapper;
    private final BookingMapper bookingMapper;
    private final UserMapper userMapper;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemRequestRepository itemRequestRepository;

    @Transactional(readOnly = true)
    @Override
    public ItemPlusDto getById(long id, long userId) {
        Item item;
        try {
            item = itemRepository.findById(id).get();
        } catch (RuntimeException e) {
            throw new NotFoundException("Такой вещи нет");
        }
        ItemPlusDto itemPlusDto = itemMapper.toItemPlusDto(item);
        List<BookingDto> bookingDtoList = bookingRepository.findByItem_IdAndStatusOrderByStartDesc(id, Status.APPROVED)
                .stream()
                .map(bookingMapper::toBookingDto)
                .collect(Collectors.toList());
        Set<CommentDto> commentDtoList = commentRepository.findByItem_IdOrderByCreatedDesc(id)
                .stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toSet());
        if (itemPlusDto.getOwner().getId() == userId) {
            LocalDateTime localDateTime = LocalDateTime.now();
            itemPlusDto.setNextBooking(bookingDtoList.stream()
                    .filter(a -> a.getItemId() == itemPlusDto.getId() &&
                            a.getStart().isAfter(localDateTime))
                    .min(Comparator.comparing(BookingDto::getStart)).orElse(null));
            itemPlusDto.setLastBooking(bookingDtoList.stream()
                    .filter(a -> a.getItemId() == itemPlusDto.getId() &&
                            a.getStart().isBefore(localDateTime))
                    .max(Comparator.comparing(BookingDto::getStart)).orElse(null));
        }
        itemPlusDto.setComments(commentDtoList);
        return itemPlusDto;
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<ItemPlusDto> getAllByUser(int from, int size, long id) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<ItemPlusDto> itemPlusDtoList = itemRepository.findAllByOwnerId(pageable, id)
                .stream()
                .map(itemMapper::toItemPlusDto)
                .collect(Collectors.toList());
        List<BookingDto> bookingDtoList = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(id)
                .stream()
                .map(bookingMapper::toBookingDto)
                .collect(Collectors.toList());
        Set<Comment> commentList = commentRepository.findAllByItemIdInOrderByCreatedDesc(
                itemPlusDtoList.stream()
                        .map(ItemPlusDto::getId)
                        .collect(Collectors.toList()));
        LocalDateTime localDateTime = LocalDateTime.now();
        itemPlusDtoList.forEach(a -> {
            a.setNextBooking(bookingDtoList.stream()
                    .filter(b -> b.getItemId() == a.getId() &&
                            b.getStart().isAfter(localDateTime))
                    .min(Comparator.comparing(BookingDto::getStart)).orElse(null));
            a.setLastBooking(bookingDtoList.stream()
                    .filter(b -> b.getItemId() == a.getId() &&
                            b.getStart().isBefore(localDateTime))
                    .max(Comparator.comparing(BookingDto::getStart)).orElse(null));
        });
        for (ItemPlusDto itemPlusDto : itemPlusDtoList) {
            itemPlusDto.setComments(commentList.stream()
                    .filter(a -> a.getItem().getId() == itemPlusDto.getId())
                    .map(commentMapper::toCommentDto)
                    .collect(Collectors.toSet()));
        }
        return itemPlusDtoList.stream()
                .sorted(Comparator.comparingLong(ItemPlusDto::getId))
                .collect(Collectors.toList());

    }

    @Transactional
    @Override
    public ItemDto create(ItemDto itemDto, long id) {
        Item newItem = itemMapper.toItem(itemDto);
        User owner = userMapper.toUser(userService.getById(id));
        newItem.setOwner(owner);
        if (itemDto.getRequestId() != null) {
            ItemRequest itemRequest = itemRequestRepository.findById(itemDto.getRequestId())
                    .orElseThrow(() -> new NotFoundException("Такого запроса нет."));
            newItem.setRequest(itemRequest);
        }
        return itemMapper.toItemDto(itemRepository.save(newItem));

    }

    @Transactional
    @Override
    public ItemDto update(ItemDto itemDto, long itemId, long id) {
        userService.getById(id);
        Item oldItem = itemRepository.findById(itemId).get();
        if (oldItem.getOwner().getId() != id) {
            throw new NotFoundException("Пользователю не принадлежит этот предмет.");
        }
        if (itemDto.getName() != null) {
            oldItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            oldItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            oldItem.setAvailable(itemDto.getAvailable());
        }
        return itemMapper.toItemDto(itemRepository.save(oldItem));

    }

    @Transactional
    @Override
    public void deleteById(long id) {
        itemRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<ItemDto> search(int from, int size, String text) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.findAll(pageable)
                .stream()
                .filter(a -> a.getAvailable() && (a.getDescription().toLowerCase().contains(text.toLowerCase()) || a.getName().toLowerCase().contains(text.toLowerCase())))
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CommentDto createComment(long id, long userId, CommentDto commentDto) {
        Comment comment = commentMapper.toComment(commentDto);
        User user = userMapper.toUser(userService.getById(userId));
        Item item = itemRepository.findById(id).get();
        List<Booking> bookingList = bookingRepository.findAllByItemIdAndBookerIdAndStatusOrderByStartDesc(id, userId, Status.APPROVED).orElseThrow(() -> new NotFoundException(
                "Эта вещь не была арендована пользователем"));
        bookingList.stream().filter(booking -> booking.getEnd().isBefore(LocalDateTime.now())).findAny().orElseThrow(() ->
                new ValidException("Нельзя оставлять комментарии, если аренда еще не истекла."));
        comment.setAuthor(user);
        comment.setItem(item);
        comment.setCreated(LocalDateTime.now());
        comment = commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);

    }

}
