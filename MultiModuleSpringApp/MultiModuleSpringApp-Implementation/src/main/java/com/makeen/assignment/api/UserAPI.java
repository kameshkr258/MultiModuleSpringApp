package com.makeen.assignment.api;

import com.makeen.assignment.model.ChangePasswordRequest;
import com.makeen.assignment.model.CreateUserRequest;
import com.makeen.assignment.model.User;
import com.makeen.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "user")
public class UserAPI {

    @Autowired
    private UserService userService;

    @PostMapping
    public User create(@RequestBody @Valid CreateUserRequest request) {
        return userService.create( request );
    }

    @GetMapping("{id}")
    public User get(@PathVariable String id) {
        return userService.getUser( Long.parseLong( id ) );
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "Successfully logged out";
    }

    @PostMapping("updatePassword")
    public User updatePassword(@RequestBody ChangePasswordRequest request) {
        return userService.updatePassword( request.getId(),request.getPassword() );
    }

}
