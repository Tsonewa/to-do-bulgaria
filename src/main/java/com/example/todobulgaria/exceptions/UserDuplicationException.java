package com.example.todobulgaria.exceptions;

public class UserDuplicationException extends RuntimeException{

    private final String  username;

    public UserDuplicationException(String username) {
        super("User with id " + username + " already exist");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
