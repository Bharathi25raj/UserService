package com.bharathi.userservice.dtos;

import com.bharathi.userservice.models.Role;
import com.bharathi.userservice.models.User;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;

    @ManyToMany
    private List<Role> roles;
    private Boolean isEmailVerified;

    public static UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        userDto.setIsEmailVerified(user.getIsEmailVerified());
        return userDto;
    }

}
