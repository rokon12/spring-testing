package com.bazlur.springapitest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TodoRepository todoRepository;

    @Before
    public void setUp() {
        createDummyData();
    }

    private void createDummyData() {
        var todo1 = new Todo("Hello1");
        var todo2 = new Todo("Hello2");
        var todo3 = new Todo("Hello3");

        entityManager.persist(todo1);
        entityManager.persist(todo2);
        entityManager.persist(todo3);
        entityManager.flush();
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    @Test
    public void findAll_shouldReturnAllTodos() {
        //createDummyData();
        var todos = todoRepository.findAll();
        Assertions.assertEquals(3, todos.size());

    }
}