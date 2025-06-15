package com.bharathi.userservice.controllers;

import com.bharathi.userservice.dtos.LogInRequestDto;
import com.bharathi.userservice.dtos.LogInResponseDto;
import com.bharathi.userservice.dtos.SignUpRequestDto;
import com.bharathi.userservice.dtos.UserDto;
import com.bharathi.userservice.exceptions.InvalidPasswordException;
import com.bharathi.userservice.exceptions.UserNotFoundException;
import com.bharathi.userservice.models.Token;
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

    @PostMapping("/login")
    public LogInResponseDto login(@RequestBody LogInRequestDto requestDto) throws UserNotFoundException, InvalidPasswordException {

        Token token = userService.login(requestDto.getEmail(), requestDto.getPassword());
        LogInResponseDto responseDto = new LogInResponseDto();
        responseDto.setToken(token);
        return responseDto;
    }

}
