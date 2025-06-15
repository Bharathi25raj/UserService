package com.bharathi.userservice.controllers;

import com.bharathi.userservice.dtos.*;
import com.bharathi.userservice.exceptions.InvalidPasswordException;
import com.bharathi.userservice.exceptions.UserNotFoundException;
import com.bharathi.userservice.models.Token;
import com.bharathi.userservice.models.User;
import com.bharathi.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogOutRequestDto requestDto){
        ResponseEntity<Void> responseEntity;

        try{
            userService.logout(requestDto.getToken());
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            System.out.println("Something went wrong");
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}
