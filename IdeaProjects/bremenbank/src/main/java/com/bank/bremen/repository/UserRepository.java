package com.bank.bremen.repository;

import com.bank.bremen.domain.User;
import com.bank.bremen.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserNameAndEnabledTrue(String username) throws ResourceNotFoundException;


    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

    Boolean existsBySsn(String ssn);


    Optional<User> findOneWithAuthoritiesByUserName(String userName);

}