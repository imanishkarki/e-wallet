package com.springpractice.todolist.service;

import com.springpractice.todolist.entity.ToDoItem;
import com.springpractice.todolist.model.ApiResponse;
import com.springpractice.todolist.payload.ToDoItemDTO;
import com.springpractice.todolist.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private ToDoRepository todoRepository;


    @Override
    public ApiResponse saveTodoItemDTO(ToDoItemDTO todoItemDto) {

        Optional<ToDoItem> existingItem = todoRepository.findByTitle(todoItemDto.getTitle());


        if (existingItem.isEmpty()) {
            ToDoItem toDoItem = new ToDoItem();
            toDoItem.setId(todoItemDto.getId());
            toDoItem.setTitle(todoItemDto.getTitle());
            toDoItem.setAuthor(todoItemDto.getAuthor());
            toDoItem.setDescription(todoItemDto.getDescription());
            String errorMessage = String.format("this cant be posted",todoItemDto.getTitle(),todoItemDto.getDescription());
             
            todoRepository.save(toDoItem);
            ToDoItemDTO tdto = new ToDoItemDTO(toDoItem.getId(), toDoItem.getTitle(), toDoItem.getAuthor(), toDoItem.getDescription());
            return new ApiResponse(tdto, true, "saved");
        } else {
String errorMessage = String.format("this cant be posted",todoItemDto.getTitle(),todoItemDto.getDescription());
        throw new DataIntegrityViolationException(errorMessage);
        }
    }



    @Override
    public ApiResponse getAllToDoItemDTO() {
        List<ToDoItem> todo1 = todoRepository.findAll();

        List<ToDoItemDTO> dtoList = todo1.stream()
                .map(item -> new ToDoItemDTO(
                        item.getId(),
                        item.getTitle(),
                        item.getAuthor(),
                        item.getDescription()
                ))

                .collect(Collectors.toList()
                );
        return new ApiResponse(dtoList, true, " get all success");
    }

    @Override
    public ToDoItemDTO getToDoItemById(Long id) throws Exception {


        Optional<ToDoItem> exiItem=todoRepository.findById(id);

        if (exiItem.isPresent()){
            ToDoItem todo= exiItem.get();
        return new ToDoItemDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getAuthor(),
                todo.getDescription());
        }
        else{
            throw new Exception("No  id in record ");
        }

    }


    @Override
    public ApiResponse deleteToDoItemById(Long id) {
        ToDoItem todo = todoRepository.findById(id).get();
        todoRepository.deleteById(id);
        return new ApiResponse(true, "deleted successfully");
    }


    @Override
    public ApiResponse updateToDoItem(Long id, ToDoItem todoItem) {
        Optional<ToDoItem> optionalToDo = todoRepository.findById(id);

        if (optionalToDo.isPresent()) {
            ToDoItem existingItem = optionalToDo.get();

            if (todoItem.getTitle() != null) {
                existingItem.setTitle(todoItem.getTitle());
            }

            if (todoItem.getDescription() != null) {
                existingItem.setDescription(todoItem.getDescription());
            }
            if (todoItem.getAuthor() != null) {
                existingItem.setAuthor(todoItem.getAuthor());

            }
            if (todoItem.getCompleted() != null) {
                existingItem.setCompleted(todoItem.getCompleted());
            }
            todoRepository.save(existingItem);
            return new ApiResponse(true, "updated successfull!!");
        }
        return null;
    }

    @Override
    public ApiResponse markToDoItem(Long id, ToDoItem todoItem) {
        Optional<ToDoItem> optionalToDo = todoRepository.findById(id); // use id directly

        if (optionalToDo.isPresent()) {
            ToDoItem existingItem = optionalToDo.get();

            // Toggle or set completed flag
            Boolean newCompleted = (todoItem.getCompleted() != null)
                    ? todoItem.getCompleted()
                    : !Boolean.TRUE.equals(existingItem.getCompleted());

            existingItem.setCompleted(newCompleted);

            ToDoItem mark = todoRepository.save(existingItem); // ✅ Save the updated entity
            return new ApiResponse(mark, true, "Marked successfully");
        }

        // Return a proper failure response
        return new ApiResponse(false, "ToDo item not found with ID: " + id);
    }
}
