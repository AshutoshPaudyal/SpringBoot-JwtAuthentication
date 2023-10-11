package com.example.newoffceproject.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInputDto {

    private Integer id;
    private String name;
    @Email(message = "This is invalid email format")
    private String email;
    private String phoneNumber;
    private String enterPassword;
    private String confirmPassword;
}
