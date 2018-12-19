package com.bazlur.springapitest;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@Entity
public class Todo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;
    private String content;
    private LocalDateTime createdDate;
    private Boolean completed;
    private LocalDateTime lastUpdatedTime;

    public Todo(String content) {
        this.content = content;
        this.completed = false;
        this.createdDate = LocalDateTime.now();
        this.lastUpdatedTime = LocalDateTime.now();
    }
}
