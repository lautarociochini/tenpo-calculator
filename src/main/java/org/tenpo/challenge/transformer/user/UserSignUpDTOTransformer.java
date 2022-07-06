package org.tenpo.challenge.transformer.user;

import org.springframework.stereotype.Component;
import org.tenpo.challenge.model.dto.user.UserSignupDTO;
import org.tenpo.challenge.model.internal.User;
import org.tenpo.challenge.transformer.Transformer;

@Component
public class UserSignUpDTOTransformer implements Transformer<User, UserSignupDTO> {

    @Override
    public UserSignupDTO transform(User user) {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        userSignupDTO.setId(user.getId());
        userSignupDTO.setUsername(user.getUsername());

        return userSignupDTO;
    }
}
