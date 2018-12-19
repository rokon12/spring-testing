package com.bazlur.springapitest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TodoServiceImpl implements TodoService {
    private static final Logger log = LoggerFactory.getLogger(TodoService.class);

    private final TodoRepository todoRepository;

    TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<ToDoDTO> findAll() {

        return todoRepository.findAll()
                .stream()
                .map(ToDoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ToDoDTO save(ToDoDTO toDoDTO) {
        Todo todo = new Todo(toDoDTO.getContent());

        return new ToDoDTO(todoRepository.save(todo));
    }

    @Override
    public ToDoDTO findById(long id) {

        return new ToDoDTO(findToDoById(id));
    }

    private Todo findToDoById(long id) {

        return todoRepository.findOneById(id)
                .orElseThrow(TodoNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        todoRepository.delete(findToDoById(id));
    }

}
