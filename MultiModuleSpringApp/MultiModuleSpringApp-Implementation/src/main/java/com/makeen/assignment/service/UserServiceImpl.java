package com.makeen.assignment.service;

import com.makeen.assignment.model.CreateUserRequest;
import com.makeen.assignment.model.LastUsedPassword;
import com.makeen.assignment.model.User;
import com.makeen.assignment.repository.LastUsedPasswordRepository;
import com.makeen.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.*;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LastUsedPasswordRepository lastUsedPasswordRepository;

    @Transactional
    @Override
    public User create(CreateUserRequest request) {
        if (userRepo.findByUsername( request.getUsername() ) != null) {
            throw new ValidationException( "Username exists!" );
        }
        if (!request.getPassword().equals( request.getRePassword() )) {
            throw new ValidationException( "Passwords don't match!" );
        }
        if (request.getAuthorities() == null) {
            request.setAuthorities( new HashSet<>() );
        }
        User user = User.builder().username( request.getUsername() ).password( passwordEncoder.encode(request.getPassword()) ).email( request.getEmail() ).build();

        user = userRepo.save( user );

        return user;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername( username );
        if (user == null) {
            throw new UsernameNotFoundException( "Invalid username or password." );
        }
        return new org.springframework.security.core.userdetails.User( user.getUsername(), user.getPassword(),
                Arrays.asList( new SimpleGrantedAuthority( "ROLE_USER" ) ) );
    }

    @Override
    public User getUser(Long id) {
        return userRepo.getById( id );
    }

    @Override
    @Transactional
    public User updatePassword(Long userId, String newPassword) throws ValidationException {
        User user = userRepo.getById( userId );
        String encodedPassword = passwordEncoder.encode( newPassword );

        boolean latestThreePass = user.getLastUsedPassword().stream().
                sorted( Comparator.comparing( LastUsedPassword::getCreateDt, Comparator.nullsLast( Comparator.naturalOrder() ) ) ).
                limit( 3 ).anyMatch( lastUsedUserPass -> passwordEncoder.matches( encodedPassword, lastUsedUserPass.getLastUsedPassword() ) );

        if(latestThreePass){
            throw new ValidationException("Invalid Password. Password already used. ");
        }

        user.setPassword( encodedPassword );
        LastUsedPassword lastUsedUserPass = LastUsedPassword.builder().lastUsedPassword( encodedPassword ).userId( user.getId() ).createDt( new Date() ).build();
        lastUsedPasswordRepository.save( lastUsedUserPass );
        return userRepo.save( user );
    }

}
