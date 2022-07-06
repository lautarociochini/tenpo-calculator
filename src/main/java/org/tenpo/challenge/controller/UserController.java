package org.tenpo.challenge.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tenpo.challenge.aspect.transaction.SaveOperation;
import org.tenpo.challenge.model.dto.user.AuthenticationRequest;
import org.tenpo.challenge.model.dto.user.UserSignupDTO;
import org.tenpo.challenge.model.dto.user.SignupRequest;
import org.tenpo.challenge.model.internal.User;
import org.tenpo.challenge.service.session.SessionService;
import org.tenpo.challenge.service.user.UserService;
import org.tenpo.challenge.transformer.Transformer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.tenpo.challenge.constants.ApiConstants.X_AUTH_TOKEN;

@RestController
@RequestMapping("v1/user")
public class UserController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "sessionServiceImpl")
    private SessionService sessionService;

    @Resource(name = "userSignUpDTOTransformer")
    private Transformer<User, UserSignupDTO> transformer;

    @SaveOperation
    @PostMapping("/signup")
    @ApiOperation(value = "Sign up new user", response = UserSignupDTO.class)
    public UserSignupDTO signup(@Valid @RequestBody SignupRequest signupRequest) {
        User user = this.userService.signIn(signupRequest);
        return this.transformer.transform(user);
    }

    @SaveOperation
    @PostMapping("/login")
    @ApiOperation(value = "Log in a user", response = ResponseEntity.class)
    public ResponseEntity<Object> login(@Valid @RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        this.sessionService.authenticate(request, authenticationRequest);
        return ResponseEntity.ok().body("User authenticated successfully");
    }

    @SaveOperation
    @PostMapping("/logout")
    @ApiOperation(value = "Log out a user", response = ResponseEntity.class)
    public ResponseEntity<Object> logout(@RequestHeader(value = X_AUTH_TOKEN) String xAuthtoken) {
        this.sessionService.logout(xAuthtoken);
        return ResponseEntity.ok().body("User unauthenticated successfully");
    }
}

