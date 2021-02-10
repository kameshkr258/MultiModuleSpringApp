package com.makeen.assignment.service;

import com.makeen.assignment.model.CreateUserRequest;
import com.makeen.assignment.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUser(Long id);

    User create(CreateUserRequest request);

    User updatePassword(Long userId, String newPassword);
}
