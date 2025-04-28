package it.micael.vanini.cinemille.controller.impl;

import it.micael.vanini.cinemille.configuration.jwt.Jwtutils;
import it.micael.vanini.cinemille.controller.AuthenticationController;
import it.micael.vanini.cinemille.dto.LoginRequest;
import it.micael.vanini.cinemille.dto.LoginResponse;
import it.micael.vanini.cinemille.dto.SignUpRequest;
import it.micael.vanini.cinemille.exception.AuthenticationException;
import it.micael.vanini.cinemille.exception.CineMilleException;
import it.micael.vanini.cinemille.model.User;
import it.micael.vanini.cinemille.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.micael.vanini.cinemille.dto.Error.USERNAME_ALREADY_EXISTS;
import static it.micael.vanini.cinemille.dto.Error.USERNAME_NOT_FOUND;

@RestController
@RequestMapping("api/auth")
@CrossOrigin("http://localhost:4200")
public class AuthenticationControllerImpl implements AuthenticationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationControllerImpl.class);

    private final UserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final Jwtutils jwtUtils;

    private final AuthenticationManager authenticationManager;

    public AuthenticationControllerImpl(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder, Jwtutils jwtUtils, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        LOGGER.info("REST API: login");
        String username = loginRequest.getUsername().trim();
        if (!this.userDetailsService.existsByUsername(loginRequest.getUsername())) {
            throw new UsernameNotFoundException(USERNAME_NOT_FOUND);
        }
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        loginRequest.getPassword()
                )
        );
        loginRequest.setPassword(null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtUtils.generateJwtToken(authentication);
        return new LoginResponse(jwt);
    }

    @Override
    public void signup(SignUpRequest signUpRequest) throws CineMilleException {
        if (this.userDetailsService.existsByUsername(signUpRequest.getUsername())) {
            throw new AuthenticationException(USERNAME_ALREADY_EXISTS);
        }
        User user = new User(
                null,
                signUpRequest.getUsername().trim(),
                this.passwordEncoder.encode(signUpRequest.getPassword())
        );
        signUpRequest.setPassword(null);
        this.userDetailsService.signUp(user);
    }

    @Override
    public void validateToken(String token) throws CineMilleException {
        LOGGER.info("REST API: validateToken");
        this.jwtUtils.validateJwtToken(token);
    }
}
