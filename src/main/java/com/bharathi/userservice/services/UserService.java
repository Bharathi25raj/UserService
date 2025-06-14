package com.bharathi.userservice.services;


import com.bharathi.userservice.models.User;

public interface UserService {

    User signup(String name, String email, String password);

}
