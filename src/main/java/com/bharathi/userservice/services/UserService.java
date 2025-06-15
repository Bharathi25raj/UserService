package com.bharathi.userservice.services;


import com.bharathi.userservice.exceptions.InvalidPasswordException;
import com.bharathi.userservice.exceptions.InvalidTokenException;
import com.bharathi.userservice.exceptions.UserNotFoundException;
import com.bharathi.userservice.models.Token;
import com.bharathi.userservice.models.User;

public interface UserService {

    User signup(String name, String email, String password);

    Token login(String email, String password) throws UserNotFoundException, InvalidPasswordException;

    void logout(String token) throws InvalidTokenException;

    User validateToken(String tokenValue) throws InvalidTokenException;

}
