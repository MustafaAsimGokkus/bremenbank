package com.bank.bremen.repository;

import com.bank.bremen.controller.dto.UserDTO;
import com.bank.bremen.domain.User;
import com.bank.bremen.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
   // I get a user(FROM User u) and put it inside UserDTO constructor==> (u) it is the feature of jpa repository)
    //write exact address of PackageDTO
    @Query("SELECT new com.bank.bremen.controller.dto.UserDTO(u.id,u) FROM User u ")
    Page<UserDTO> findUsersPage (Pageable pageable );
    Optional<User> findByUserNameAndEnabledTrue(String username)
            throws ResourceNotFoundException;

    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
    Boolean existsBySsn(String ssn);
    Optional<User> findOneWithAuthoritiesByUserName(String userName);

}