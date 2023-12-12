package com.springbootapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    @NotEmpty(message = "Should Not be empty")
    private String name;
    @NotEmpty(message = "Should not be Empty")
    @Email
    private String email;
    @NotEmpty(message = "Should Not be Empty")
    private String body;
}
