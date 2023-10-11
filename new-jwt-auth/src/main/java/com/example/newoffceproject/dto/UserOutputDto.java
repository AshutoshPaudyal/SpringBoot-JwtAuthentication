package com.example.newoffceproject.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserOutputDto {

    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
}
