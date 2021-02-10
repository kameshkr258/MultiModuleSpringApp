package com.makeen.assignment.repository;

import com.makeen.assignment.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Cacheable
    User findByUsername(String username);

    @Cacheable
    Optional<User> findById(Long id);

    @Cacheable
    default User getById(Long id) {
        if(findById( id ).isPresent()){
            return findById( id ).get();
        }else return null;
    }
}