package com.walletapp.ewallet.payload;


//import lombok.Getter;
//import lombok.Setter;


public class UserDto {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    private String password;
    private String email;



}
