package com.springbootapi.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class RegisterDto {
    private String fullname;
    private String username;
    private String email;
    private String password;

}
