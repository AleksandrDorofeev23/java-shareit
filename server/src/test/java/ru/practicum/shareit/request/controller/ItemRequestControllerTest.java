package ru.practicum.shareit.request.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.item.mapper.ItemMapperImpl;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.controller.ItemRequestController;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.request.service.ItemRequestServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ItemRequestController.class})
@ExtendWith(SpringExtension.class)
class ItemRequestControllerTest {
    @Autowired
    private ItemRequestController itemRequestController;

    @MockBean
    private ItemRequestService itemRequestService;

    @Test
    void testCreate() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        ArrayList<Item> itemList = new ArrayList<>();
        itemRequest.setItems(itemList);
        itemRequest.setRequester(user);
        ItemRequestRepository itemRequestRepository = mock(ItemRequestRepository.class);
        when(itemRequestRepository.save((ItemRequest) any())).thenReturn(itemRequest);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(user1));
        ItemRepository itemRepository = mock(ItemRepository.class);
        ItemRequestController itemRequestController = new ItemRequestController(new ItemRequestServiceImpl(
                itemRequestRepository, userRepository, itemRepository, new ItemRequestMapper(new ItemMapperImpl())));
        ItemRequestDto actualCreateResult = itemRequestController.create(1L, new ItemRequestDto());
        assertEquals(itemList, actualCreateResult.getItems());
        assertEquals("01:01", actualCreateResult.getCreated().toLocalTime().toString());
        assertEquals(1L, actualCreateResult.getId());
        assertEquals("The characteristics of someone or something", actualCreateResult.getDescription());
        verify(itemRequestRepository).save((ItemRequest) any());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testGetByOwner() throws Exception {
        when(itemRequestService.getByOwner(anyInt(), anyInt(), (Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/requests");
        MockHttpServletRequestBuilder paramResult = getResult.param("from", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1))
                .header("X-Sharer-User-Id", "42");
        MockMvcBuilders.standaloneSetup(itemRequestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetALL() throws Exception {
        when(itemRequestService.getALL(anyInt(), anyInt(), (Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/requests/all");
        MockHttpServletRequestBuilder paramResult = getResult.param("from", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1))
                .header("X-Sharer-User-Id", "42");
        MockMvcBuilders.standaloneSetup(itemRequestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetByID() throws Exception {
        when(itemRequestService.getByID((Long) any(), (Long) any())).thenReturn(new ItemRequestDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/requests/{id}", 1L)
                .header("X-Sharer-User-Id", "42");
        MockMvcBuilders.standaloneSetup(itemRequestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":0,\"description\":null,\"created\":null,\"items\":null}"));
    }

    @Test
    void testGetByID2() throws Exception {
        when(itemRequestService.getByOwner(anyInt(), anyInt(), (Long) any())).thenReturn(new ArrayList<>());
        when(itemRequestService.getByID((Long) any(), (Long) any())).thenReturn(new ItemRequestDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/requests/{id}", "", "Uri Variables")
                .header("X-Sharer-User-Id", "42");
        MockMvcBuilders.standaloneSetup(itemRequestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

