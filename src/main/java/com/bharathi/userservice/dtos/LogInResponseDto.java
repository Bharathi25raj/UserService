package com.bharathi.userservice.dtos;

import com.bharathi.userservice.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInResponseDto {
    private Token token;
}
