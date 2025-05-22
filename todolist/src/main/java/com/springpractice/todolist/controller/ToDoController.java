package com.springpractice.todolist.controller;

import com.springpractice.todolist.entity.ToDoItem;
import com.springpractice.todolist.model.ApiResponse;
import com.springpractice.todolist.payload.ToDoItemDTO;
import com.springpractice.todolist.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    @Autowired
    private TodoService todoService;


    // Create a new ToDo item
    @PostMapping
    public ResponseEntity<ApiResponse> saveToDoItemDTO(@RequestBody @Valid  ToDoItemDTO todoItemDto) {
        return ResponseEntity.ok(todoService.saveTodoItemDTO(todoItemDto));

    }

    // Get all ToDo items
    @GetMapping
    public ResponseEntity<ApiResponse> getAllToDoItems() {
        return ResponseEntity.ok(todoService.getAllToDoItemDTO());
    }


    // Get a ToDo item by ID
    @GetMapping("/{id}")
    public ResponseEntity<ToDoItemDTO> getToDoItemById(@PathVariable Long id) throws Exception {
        ToDoItemDTO response = todoService.getToDoItemById(id);
        return ResponseEntity.ok(response);
    }

    // Delete a ToDo item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteToDoItemById(@PathVariable Long id) {
        ApiResponse dlt = todoService.deleteToDoItemById(id);
        return ResponseEntity.ok(dlt);
    }

    // Mark a ToDo item as completed
    @PutMapping("/mark/{id}")
    public ResponseEntity<ApiResponse> markToDoItem(@PathVariable Long id, @RequestBody ToDoItem todoItem) {
        ApiResponse mrk = todoService.markToDoItem(id, todoItem);
        return ResponseEntity.ok(mrk);
    }

    // Update a ToDo item by ID
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateToDoItem(@PathVariable Long id, @RequestBody ToDoItem todoItem) {
        ApiResponse updt = todoService.updateToDoItem(id, todoItem);
        return ResponseEntity.ok(updt);
    }
}



