package com.example.Spring_Security.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty(message = "Name should not be empty!")
    private String name;

    @NotEmpty(message = "Surname should not be empty!")
    private String surname;

    @NotEmpty(message = "Email should not be empty!")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty!")
    private String password;

    @NotEmpty(message = "Phone should not be empty!")
    private String phone;

}
