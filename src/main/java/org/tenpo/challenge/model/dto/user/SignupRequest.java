package org.tenpo.challenge.model.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class SignupRequest implements Serializable {

    @NotEmpty(message = "Username can not be empty")
    private String username;

    @NotEmpty(message = "Password can not be empty")
    private String password;

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Please provide a valid email")
    private String email;

    public SignupRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

}
