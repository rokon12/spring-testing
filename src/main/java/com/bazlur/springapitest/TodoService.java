package com.bazlur.springapitest;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface TodoService {
    List<ToDoDTO> findAll();

    ToDoDTO save(ToDoDTO todo);

    ToDoDTO findById(long id);

    void delete(Long id);
}
