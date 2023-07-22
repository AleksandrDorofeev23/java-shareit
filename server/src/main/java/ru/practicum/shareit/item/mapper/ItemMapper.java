package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemPlusDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {
    @Mapping(source = "request.id", target = "requestId")
    ItemDto toItemDto(Item item);

    ItemPlusDto toItemPlusDto(Item item);

    Item toItem(ItemDto itemDto);

    Item toItem(ItemPlusDto itemPlusDtoDto);

}
