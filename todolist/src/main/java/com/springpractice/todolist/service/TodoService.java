package com.springpractice.todolist.service;

import com.springpractice.todolist.entity.ToDoItem;
import com.springpractice.todolist.model.ApiResponse;
import com.springpractice.todolist.payload.ToDoItemDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    ApiResponse saveTodoItemDTO(ToDoItemDTO todoItemDto);

    ApiResponse getAllToDoItemDTO();

    ToDoItemDTO getToDoItemById(Long id) throws Exception;

    ApiResponse deleteToDoItemById(Long id);

    ApiResponse updateToDoItem(Long id, ToDoItem todoItem);

    ApiResponse markToDoItem(Long id,ToDoItem todoItem);

}
