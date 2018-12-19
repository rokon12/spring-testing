package com.bazlur.springapitest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.bazlur.springapitest.TestUtil.APPLICATION_JSON_UTF8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ToDoController.class)
public class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService mockTodoService;

    @Test
    public void sayHello_shouldReturnOK() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ok"));
    }


    @Test
    public void findAll_ShouldReturnAllFoundTodosEntries() throws Exception {
        var first = new ToDoDTO(1, "Lorem Ipsum");
        var second = new ToDoDTO(2, "Lorem Ipsum2");

        when(mockTodoService.findAll()).thenReturn(List.of(first, second));

        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].content", notNullValue()))
                .andExpect(jsonPath("$[0].content", is("Lorem Ipsum")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].content", notNullValue()))
                .andExpect(jsonPath("$[1].content", is("Lorem Ipsum2")));

        verify(mockTodoService, times(1)).findAll();
        verifyNoMoreInteractions(mockTodoService);
    }

    @Test
    public void createNewTODO_shouldCreateNewTodo_AndReturnCreatedTODO() throws Exception {
        var todo = new ToDoDTO(1, "Lorem Ipsum");
        String content = TestUtil.convertObjectToJsonBytes(todo);
        when(mockTodoService.save(todo)).thenReturn(new ToDoDTO(1, "Lorem Ipsum"));

        mockMvc.perform(
                post("/api/todo")
                        .contentType(getContentType())
                        .content(content)
        ).andExpect(status()
                .isAccepted());

        ArgumentCaptor<ToDoDTO> dtoCaptor = ArgumentCaptor.forClass(ToDoDTO.class);
        verify(mockTodoService, times(1)).save(dtoCaptor.capture());
        verifyNoMoreInteractions(mockTodoService);

        ToDoDTO dtoArgument = dtoCaptor.getValue();
        assertThat(dtoArgument.getId(), is(1L));
        assertThat(dtoArgument.getContent(), is("Lorem Ipsum"));
    }

    private MediaType getContentType() {

        return MediaType.APPLICATION_JSON_UTF8;
    }

    @Test
    public void createTODOWithReallyLongContent_shouldReturnValidationError() throws Exception {
        var todo = new ToDoDTO(1, TestUtil.createStringWithLength(501));
        String content = TestUtil.convertObjectToJsonBytes(todo);

        mockMvc.perform(post("/api/todo")
                .contentType(getContentType())
                .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(getContentType()))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[*].path", containsInAnyOrder("content")))
                .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
                        "The maximum length of the description is 500 characters."
                )));
        verifyZeroInteractions(mockTodoService);
    }

    @Test
    public void findById_TodoEntryNotFound_ShouldReturnHttpStatusCode404() throws Exception {
        when(mockTodoService.findById(1L)).thenThrow(new TodoNotFoundException());

        mockMvc.perform(get("/api/todo/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(mockTodoService, times(1)).findById(1L);
        verifyNoMoreInteractions(mockTodoService);
    }

    @Test
    public void deleteTODOByID_shouldReturnHttpOK() throws Exception {
        doNothing().when(mockTodoService).delete(1L);

        mockMvc.perform(delete("/api/todo/{id}", 1L))
                .andExpect(status().isOk());
    }
}