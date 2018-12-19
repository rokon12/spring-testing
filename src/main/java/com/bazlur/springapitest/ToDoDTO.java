package com.bazlur.springapitest;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ToDoDTO implements Serializable {
    private Long id;
    @NotEmpty
    @Length(max = 500, message = "The maximum length of the description is 500 characters.")
    private String content;
    private LocalDateTime createdDate;
    private Boolean completed;

    public ToDoDTO() {
    }

    public ToDoDTO(Todo todo) {
        this.id = todo.getId();
        this.content = todo.getContent();
        this.createdDate = todo.getCreatedDate();
        this.completed = todo.getCompleted();
    }

    public ToDoDTO(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
