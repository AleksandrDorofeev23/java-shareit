package ru.practicum.shareit.item.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.item.mapper.CommentMapperImpl;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.CommentDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CommentMapperImpl.class})
@ExtendWith(SpringExtension.class)
class CommentMapperImplTest {
    @Autowired
    private CommentMapperImpl commentMapperImpl;

    @Test
    void testToComment() {
        CommentDto commentDto = new CommentDto();
        commentDto.setAuthorName("JaneDoe");
        commentDto.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        commentDto.setId(1L);
        commentDto.setText("Text");
        Comment actualToCommentResult = commentMapperImpl.toComment(commentDto);
        assertEquals("Text", actualToCommentResult.getText());
        assertEquals(1L, actualToCommentResult.getId());
        assertEquals("0001-01-01", actualToCommentResult.getCreated().toLocalDate().toString());
    }

    @Test
    void testToComment2() {
        CommentDto commentDto = mock(CommentDto.class);
        when(commentDto.getText()).thenReturn("Text");
        when(commentDto.getCreated()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(commentDto.getId()).thenReturn(1L);
        doNothing().when(commentDto).setAuthorName((String) any());
        doNothing().when(commentDto).setCreated((LocalDateTime) any());
        doNothing().when(commentDto).setId(anyLong());
        doNothing().when(commentDto).setText((String) any());
        commentDto.setAuthorName("JaneDoe");
        commentDto.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        commentDto.setId(1L);
        commentDto.setText("Text");
        Comment actualToCommentResult = commentMapperImpl.toComment(commentDto);
        assertEquals("Text", actualToCommentResult.getText());
        assertEquals(1L, actualToCommentResult.getId());
        assertEquals("0001-01-01", actualToCommentResult.getCreated().toLocalDate().toString());
        verify(commentDto).getText();
        verify(commentDto).getCreated();
        verify(commentDto).getId();
        verify(commentDto).setAuthorName((String) any());
        verify(commentDto).setCreated((LocalDateTime) any());
        verify(commentDto).setId(anyLong());
        verify(commentDto).setText((String) any());
    }

    @Test
    void testToCommentDto() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user2);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user1);
        item.setRequest(itemRequest);

        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setId(1L);
        comment.setItem(item);
        comment.setText("Text");
        CommentDto actualToCommentDtoResult = commentMapperImpl.toCommentDto(comment);
        assertEquals("Name", actualToCommentDtoResult.getAuthorName());
        assertEquals("Text", actualToCommentDtoResult.getText());
        assertEquals(1L, actualToCommentDtoResult.getId());
        assertEquals("0001-01-01", actualToCommentDtoResult.getCreated().toLocalDate().toString());
    }

    @Test
    void testToCommentDto2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setId(1L);
        user1.setName("Name");

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new ArrayList<>());
        itemRequest.setRequester(user2);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(user1);
        item.setRequest(itemRequest);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");
        Comment comment = mock(Comment.class);
        when(comment.getText()).thenReturn("Text");
        when(comment.getCreated()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(comment.getId()).thenReturn(1L);
        when(comment.getAuthor()).thenReturn(user3);
        doNothing().when(comment).setAuthor((User) any());
        doNothing().when(comment).setCreated((LocalDateTime) any());
        doNothing().when(comment).setId(anyLong());
        doNothing().when(comment).setItem((Item) any());
        doNothing().when(comment).setText((String) any());
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setId(1L);
        comment.setItem(item);
        comment.setText("Text");
        CommentDto actualToCommentDtoResult = commentMapperImpl.toCommentDto(comment);
        assertEquals("Name", actualToCommentDtoResult.getAuthorName());
        assertEquals("Text", actualToCommentDtoResult.getText());
        assertEquals(1L, actualToCommentDtoResult.getId());
        assertEquals("0001-01-01", actualToCommentDtoResult.getCreated().toLocalDate().toString());
        verify(comment).getText();
        verify(comment).getCreated();
        verify(comment).getId();
        verify(comment).getAuthor();
        verify(comment).setAuthor((User) any());
        verify(comment).setCreated((LocalDateTime) any());
        verify(comment).setId(anyLong());
        verify(comment).setItem((Item) any());
        verify(comment).setText((String) any());
    }
}

