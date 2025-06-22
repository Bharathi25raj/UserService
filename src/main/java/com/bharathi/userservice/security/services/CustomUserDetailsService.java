package com.bharathi.userservice.security.services;

import com.bharathi.userservice.models.User;
import com.bharathi.userservice.repositories.UserRepository;
import com.bharathi.userservice.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //get by username
        Optional<User> optionalUser = userRepository.findByEmail(username);

        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User with email: " + username + " doesn't exist");
        }

        User user = optionalUser.get();

        CustomUserDetails userDetails = new CustomUserDetails(user);

        return userDetails;
    }
}
