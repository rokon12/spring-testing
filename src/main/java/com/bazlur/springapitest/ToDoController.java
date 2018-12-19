package com.bazlur.springapitest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ToDoController {
    private final TodoService todoService;

    public ToDoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public String sayHello() {
        return "Ok";
    }

    @GetMapping(value = "/api/todo")
    public List<ToDoDTO> findAll() {

        return todoService.findAll();
    }

    @PostMapping(value = "/api/todo")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public ToDoDTO save(@Valid @RequestBody ToDoDTO todo) {

        return todoService.save(todo);
    }

    @GetMapping(value = "/api/todo/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ToDoDTO findOne(@PathVariable Long id) {

        return todoService.findById(id);
    }

    @DeleteMapping(value = "/api/todo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {

        todoService.delete(id);
    }
}
