package com.makeen.assignment.repository;

import com.makeen.assignment.model.LastUsedPassword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastUsedPasswordRepository extends CrudRepository<LastUsedPassword, Long> {

}