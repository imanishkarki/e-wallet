package com.springpractice.todolist.repository;

import com.springpractice.todolist.entity.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository <ToDoItem, Long>{
    Optional<ToDoItem> findByTitle(String title);



}
