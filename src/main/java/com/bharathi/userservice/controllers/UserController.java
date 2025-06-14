package com.bharathi.userservice.controllers;

import com.bharathi.userservice.dtos.SignUpRequestDto;
import com.bharathi.userservice.dtos.UserDto;
import com.bharathi.userservice.models.User;
import com.bharathi.userservice.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignUpRequestDto requestDto){

        User user = userService.signup(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );


        return UserDto.from(user);
    }

}
