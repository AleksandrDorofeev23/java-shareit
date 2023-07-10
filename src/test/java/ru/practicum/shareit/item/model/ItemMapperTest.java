package ru.practicum.shareit.item.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.dto.ItemDto;

class ItemMapperTest {

    @Test
    void testToItemDto() {
        ItemDto itemDto = mock(ItemDto.class);
        when(itemDto.getAvailable()).thenReturn(true);
        when(itemDto.getDescription()).thenReturn("The characteristics of someone or something");
        when(itemDto.getName()).thenReturn("Name");
        when(itemDto.getId()).thenReturn(1L);
        ItemDto actualToItemDtoResult = ItemMapper.toItemDto(
                ItemMapper.toItem(ItemMapper.toItemDto(ItemMapper.toItem(ItemMapper.toItemDto(ItemMapper.toItem(itemDto))))));
        assertTrue(actualToItemDtoResult.getAvailable());
        assertEquals("Name", actualToItemDtoResult.getName());
        assertEquals(1L, actualToItemDtoResult.getId());
        assertEquals("The characteristics of someone or something", actualToItemDtoResult.getDescription());
        verify(itemDto).getAvailable();
        verify(itemDto).getDescription();
        verify(itemDto).getName();
        verify(itemDto).getId();
    }

    @Test
    void testToItem() {
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        when(item.getDescription()).thenReturn("The characteristics of someone or something");
        when(item.getName()).thenReturn("Name");
        when(item.getId()).thenReturn(1L);
        Item actualToItemResult = ItemMapper.toItem(
                ItemMapper.toItemDto(ItemMapper.toItem(ItemMapper.toItemDto(ItemMapper.toItem(ItemMapper.toItemDto(item))))));
        assertTrue(actualToItemResult.getAvailable());
        assertNull(actualToItemResult.getRequest());
        assertNull(actualToItemResult.getOwner());
        assertEquals("Name", actualToItemResult.getName());
        assertEquals(1L, actualToItemResult.getId());
        assertEquals("The characteristics of someone or something", actualToItemResult.getDescription());
        verify(item).getAvailable();
        verify(item).getDescription();
        verify(item).getName();
        verify(item).getId();
    }

}

