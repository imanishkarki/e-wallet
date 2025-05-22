package com.springpractice.todolist.model;


import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)

@Builder

public class ApiResponse {

    public ApiResponse(boolean success, String message){
        this.success = success;
        this.message= message;
    }


    public ApiResponse(Object data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Object data;
    private boolean success;


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    private String message;



}

