package com.makeen.assignment.api;

import com.makeen.assignment.model.ChangePasswordRequest;
import com.makeen.assignment.util.JwtTokenUtil;
import com.makeen.assignment.model.AuthRequest;
import com.makeen.assignment.model.CreateUserRequest;
import com.makeen.assignment.model.User;
import com.makeen.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "auth")
public class AuthenticationAPI {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate( new UsernamePasswordAuthenticationToken( request.getUsername(), request.getPassword() ) );

            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header( HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken( user.getUsername() ) )
                    .body( "Hello " + user.getUsername() );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).build();
        }
    }

    @PostMapping("register")
    public User register(@RequestBody @Valid CreateUserRequest request) {
        return userService.create( request );
    }
}
