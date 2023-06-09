package com.bezkoder.springjwt.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

//    @NotBlank
//    @Size(max = 50)
//    @Email
//    private String email;

    private Set<String> roll;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

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

    public Set<String> getRoll() {
        return roll;
    }

    public void setRoll(Set<String> roll) {
        this.roll = roll;
    }
}
