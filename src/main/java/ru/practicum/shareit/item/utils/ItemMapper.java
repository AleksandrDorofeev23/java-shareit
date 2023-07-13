package ru.practicum.shareit.item.utils;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemPlusDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {
    ItemDto toItemDto(Item item);

    ItemPlusDto toItemPlusDto(Item item);

    Item toItem(ItemDto itemDto);

    Item toItem(ItemPlusDto itemPlusDtoDto);

}
