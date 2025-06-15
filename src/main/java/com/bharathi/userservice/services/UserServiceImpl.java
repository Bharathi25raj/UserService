package com.bharathi.userservice.services;

import com.bharathi.userservice.dtos.LogInResponseDto;
import com.bharathi.userservice.dtos.UserDto;
import com.bharathi.userservice.exceptions.InvalidPasswordException;
import com.bharathi.userservice.exceptions.InvalidTokenException;
import com.bharathi.userservice.exceptions.UserNotFoundException;
import com.bharathi.userservice.models.Token;
import com.bharathi.userservice.models.User;
import com.bharathi.userservice.repositories.TokenRepository;
import com.bharathi.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final TokenRepository tokenRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signup(String name, String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()){
            //User is already present in the DB and no need to sign up again
            return optionalUser.get();
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) throws UserNotFoundException, InvalidPasswordException {

        /*
        *  1. Check if user exists with the given email
        *  2. If not, throw an exception or redirect to signup page
        *  3. If user exists, check if the password matches with the password in DB
        *  4. If yes, then login successful and return new token
        *  5. Else throw exception
        */

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User doesn't exist, please sign up");
        }

        if(!bCryptPasswordEncoder.matches(password, optionalUser.get().getHashedPassword())){
            throw new InvalidPasswordException("Please enter correct password");
        }

        User user = optionalUser.get();

        Token token = generateToken(user);

        return tokenRepository.save(token);

    }

    @Override
    public void logout(String tokenValue) throws InvalidTokenException {

        //validate if te given token is present in the DB and deleted = false
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(tokenValue, false);

        if(optionalToken.isEmpty()){
            throw new InvalidTokenException("Invalid Token Passed");
        }

        Token token = optionalToken.get();
        token.setDeleted(true);
        tokenRepository.save(token);
        return;
    }

    private Token generateToken(User user){

        LocalDate currentTime = LocalDate.now(); //Current Time
        LocalDate thirtyDaysFromCurrentTime = currentTime.plusDays(30);
        Date expiryDate = Date.from(thirtyDaysFromCurrentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setExpiryAt(expiryDate);

        //Token value - randomly generated String of 128 characters
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLetterOrDigit)
                .build();

        token.setValue(generator.generate(128));
        token.setUser(user);

        return token;
    }
}
