package com.maojie.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: User entity representing user information in the application.
 */
@Data
public class User {

    @NotNull
    private Integer id; // Primary key ID

    private String username; // Username

    @JsonIgnore
    private String password; // Password, ignored in JSON serialization

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname; // Nickname, with a length constraint of 1-10 characters

    @NotEmpty
    @Email
    private String email; // Email address

    private String userPic; // URL of the user's avatar image

    private LocalDateTime createTime; // Timestamp for when the user was created

    private LocalDateTime updateTime; // Timestamp for when the user was last updated
}
