package com.femcoders.events.exception;

public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException(String username) {
        super("Username " + username + " not found");
    }
}
