package org.tenpo.challenge.service.user;

import org.tenpo.challenge.model.dto.user.SignupRequest;
import org.tenpo.challenge.model.internal.User;

public interface UserService {

    User signIn(SignupRequest signupRequest);

    User findUserByUsername(String userName);

}
