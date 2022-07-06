package org.tenpo.challenge.model.dto.user;

import javax.validation.constraints.NotEmpty;

public class AuthenticationRequest {

    @NotEmpty(message = "Username can not be empty")
    private String username;

    @NotEmpty(message = "Password can not be empty")
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
