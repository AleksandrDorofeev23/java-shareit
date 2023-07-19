package ru.practicum.shareit.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.item.model.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemPlusDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ItemController.class})
@ExtendWith(SpringExtension.class)
class ItemControllerTest {
    @Autowired
    private ItemController itemController;

    @MockBean
    private ItemService itemService;

    @Test
    void testGetById() throws Exception {
        ItemPlusDto itemPlusDto = new ItemPlusDto();
        itemPlusDto.setAvailable(true);
        itemPlusDto.setComments(new HashSet<>());
        itemPlusDto.setDescription("The characteristics of someone or something");
        itemPlusDto.setId(1L);
        itemPlusDto.setLastBooking(new BookingDto());
        itemPlusDto.setName("Name");
        itemPlusDto.setNextBooking(new BookingDto());
        itemPlusDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemService.getById(anyLong(), anyLong())).thenReturn(itemPlusDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/items/{id}", 1L)
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"available\":true,"
                                        + "\"owner\":{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\"},\"lastBooking\":{\"id\":0,\"start\":null,\"end"
                                        + "\":null,\"itemId\":0,\"bookerId\":0,\"status\":null},\"nextBooking\":{\"id\":0,\"start\":null,\"end\":null,\"itemId\""
                                        + ":0,\"bookerId\":0,\"status\":null},\"comments\":[]}"));
    }

    @Test
    void testGetAllByUser() throws Exception {
        when(itemService.getAllByUser(anyInt(), anyInt(), anyLong())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/items");
        MockHttpServletRequestBuilder paramResult = getResult.param("from", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1))
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdate() throws Exception {
        when(itemService.update((ItemDto) any(), anyLong(), anyLong())).thenReturn(new ItemDto());

        ItemDto itemDto = new ItemDto();
        itemDto.setAvailable(true);
        itemDto.setDescription("The characteristics of someone or something");
        itemDto.setId(1L);
        itemDto.setName("Name");
        itemDto.setRequestId(1L);
        String content = (new ObjectMapper()).writeValueAsString(itemDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/items/{id}", 1L)
                .header("X-Sharer-User-Id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":0,\"name\":null,\"description\":null,\"available\":null,\"requestId\":null}"));
    }

    @Test
    void testSearch() throws Exception {
        when(itemService.search(anyInt(), anyInt(), (String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/items/search");
        MockHttpServletRequestBuilder paramResult = getResult.param("from", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1)).param("text", "foo");
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testDeleteById() throws Exception {
        doNothing().when(itemService).deleteById(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/items/{id}", 1L);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteById2() throws Exception {
        doNothing().when(itemService).deleteById(anyLong());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/items/{id}", 1L);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCreate() throws Exception {
        when(itemService.getAllByUser(anyInt(), anyInt(), anyLong())).thenReturn(new ArrayList<>());

        ItemDto itemDto = new ItemDto();
        itemDto.setAvailable(true);
        itemDto.setDescription("The characteristics of someone or something");
        itemDto.setId(1L);
        itemDto.setName("Name");
        itemDto.setRequestId(1L);
        String content = (new ObjectMapper()).writeValueAsString(itemDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/items")
                .header("X-Sharer-User-Id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testCreateComment2() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setAuthorName("JaneDoe");
        commentDto.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        commentDto.setId(1L);
        commentDto.setText("Text");
        when(itemService.createComment(anyLong(), anyLong(), (CommentDto) any())).thenReturn(commentDto);

        CommentDto commentDto1 = new CommentDto();
        commentDto1.setAuthorName("JaneDoe");
        commentDto1.setCreated(null);
        commentDto1.setId(1L);
        commentDto1.setText("Text");
        String content = (new ObjectMapper()).writeValueAsString(commentDto1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/items/{id}/comment", 1L)
                .header("X-Sharer-User-Id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"text\":\"Text\",\"authorName\":\"JaneDoe\",\"created\":[1,1,1,1,1]}"));
    }

    @Test
    void testCreateComment3() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setAuthorName("JaneDoe");
        commentDto.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        commentDto.setId(1L);
        commentDto.setText("Text");
        when(itemService.createComment(anyLong(), anyLong(), (CommentDto) any())).thenReturn(commentDto);

        CommentDto commentDto1 = new CommentDto();
        commentDto1.setAuthorName("JaneDoe");
        commentDto1.setCreated(null);
        commentDto1.setId(1L);
        commentDto1.setText("");
        String content = (new ObjectMapper()).writeValueAsString(commentDto1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/items/{id}/comment", 1L)
                .header("X-Sharer-User-Id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

}

