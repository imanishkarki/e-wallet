package com.springpractice.todolist.GlobalExceptionHandler;

import com.springpractice.todolist.entity.ToDoItem;
import com.springpractice.todolist.model.ApiResponse;
import com.springpractice.todolist.payload.ToDoItemDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExpHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex){
      //  return  "Data already exists or violates constraints"+ ex.getRootCause();
        Map<String,Object> errorData = new HashMap<>();
        errorData.put("details",ex.getMessage());

        return new ResponseEntity(new ApiResponse(errorData,false,"Data already exists" ), HttpStatus.BAD_REQUEST);

    }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleException(Exception exp){

        return new ResponseEntity(new ApiResponse(false, "No Such ID"), HttpStatus.NOT_FOUND);
  }
}


