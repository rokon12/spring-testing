package com.bazlur.springapitest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;


public class TodoServiceTest {

    private TodoService todoService;
    private TodoRepository todoRepository;

    @BeforeEach
    public void setup() {
        todoRepository = mock(TodoRepository.class);
        todoService = new TodoServiceImpl(todoRepository);
    }

    @Test
    public void findAll_shouldReturnAllTodo() {
        var list = List.of(new Todo("hello"));
        when(todoRepository.findAll()).thenReturn(list);

        List<ToDoDTO> all = todoService.findAll();
        List<ToDoDTO> all2 = todoService.findAll();
        Assertions.assertEquals(1, all.size());

        verify(todoRepository, times(2)).findAll();
        verifyNoMoreInteractions(todoRepository);
    }
}