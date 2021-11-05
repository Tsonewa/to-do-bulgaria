package com.example.todobulgaria.models.dto;

import com.example.todobulgaria.annotations.PasswordMatches;
import com.example.todobulgaria.annotations.ValidEmail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@PasswordMatches
public class UserRegistrationDto {

    @NotBlank
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 symbols")
    private String username;
    @NotBlank
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 symbols")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 symbols")
    private String lastName;
    @ValidEmail
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;

    public UserRegistrationDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
