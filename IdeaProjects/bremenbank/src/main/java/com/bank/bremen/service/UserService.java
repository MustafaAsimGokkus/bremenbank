package com.bank.bremen.service;

import com.bank.bremen.controller.request.RegisterRequest;
import com.bank.bremen.controller.request.UserUpdateRequest;
import com.bank.bremen.domain.Role;
import com.bank.bremen.domain.User;
import com.bank.bremen.domain.enumeration.UserRole;
import com.bank.bremen.exception.ConflictException;
import com.bank.bremen.exception.ResourceNotFoundException;
import com.bank.bremen.exception.message.ExceptionMessage;
import com.bank.bremen.repository.RoleRepository;
import com.bank.bremen.repository.UserRepository;
import com.bank.bremen.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;


    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByUserName(registerRequest.getUserName())) {
            throw new ConflictException(String.format(ExceptionMessage.USERNAME_ALREADY_EXISTS_MESSAGE, registerRequest.getUserName()));
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException(String.format(ExceptionMessage.EMAIL_ALREADY_EXIST_MESSAGE, registerRequest.getEmail()));
        }


        if (userRepository.existsBySsn(registerRequest.getSsn())) {
            throw new ConflictException(String.format(ExceptionMessage.SSN_ALREADY_EXIST_MESSAGE, registerRequest.getSsn()));
        }

        Set<Role> userRoles = new HashSet<>();

        //We add ROLE_CUSTOMER for every registered user by default
        Role role = roleRepository.findByName(UserRole.ROLE_CUSTOMER).
                orElseThrow(() -> new ResourceNotFoundException(String.format(ExceptionMessage.ROLE_NOT_EXIST_MESSAGE,
                        UserRole.ROLE_CUSTOMER.name())));

        userRoles.add(role);

        User user = new User(
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getSsn(),
                registerRequest.getUserName(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                registerRequest.getPhoneNumber(),
                registerRequest.getAddress(),
                registerRequest.getDateOfBirth(),
                userRoles
        );

        userRepository.save(user);

        User userSaved = userRepository.findById(user.getId()).
                orElseThrow(() -> new ResourceNotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND_MESSAGE, user.getId())));

        accountService.createAccount(userSaved);
    }

    public User findOneWithAuthoritiesByUserName() {
        String currentUserLogin = SecurityUtils.getCurrentUserLogin().
                orElseThrow(() -> new ResourceNotFoundException(ExceptionMessage.CURRENTUSER_NOT_FOUND_MESSAGE));

        User user = userRepository.findOneWithAuthoritiesByUserName(currentUserLogin).
                orElseThrow(() -> new ResourceNotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND_MESSAGE, currentUserLogin)));

        return user;
    }

    public User findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format(ExceptionMessage.USERID_NOT_FOUND_MESSAGE, id)
        ));
        return user;
    }

    public void updateUser(Long id, UserUpdateRequest request) {
        boolean emailExists = userRepository.existsByEmail(request.getEmail());

        User foundUser = findById(id);
        //we check if there is user using this email or not
        if (emailExists && !foundUser.getEmail().equals(request.getEmail())) {
            throw new ConflictException(
                    String.format(ExceptionMessage.EMAIL_ALREADY_IN_USE_MESSAGE,
                            request.getEmail()));
        }

        Boolean ssnExists = userRepository.existsBySsn(request.getSsn());
        if (ssnExists && !foundUser.getSsn().equals(request.getSsn())) {
            throw new ConflictException(
                    String.format(ExceptionMessage.SSN_ALREADY_IN_USE_MESSAGE, request.getSsn()));
        }

        foundUser.setFirstName(request.getFirstName());
        foundUser.setLastName(request.getLastName());
        foundUser.setSsn(request.getSsn());
        foundUser.setUserName(request.getUserName());
        foundUser.setEmail(request.getEmail());
        foundUser.setPassword(request.getPhoneNumber());
        foundUser.setAddress(request.getAddress());
        foundUser.setDateOfBirth(request.getDateOfBirth());
        foundUser.setEnabled(request.getEnabled());
        Set<Role> roles = addRoles(request.getRoles());
        foundUser.setRoles(roles);
        userRepository.save(foundUser);
    }

    private Set<Role> addRoles(Set<String> userRoles) {
        Set<Role> roles = new HashSet<>();

        if (userRoles == null) {
            Role role = roleRepository.findByName(UserRole.ROLE_CUSTOMER).orElseThrow(
                    () -> new ResourceNotFoundException(String.format
                            (ExceptionMessage.ROLE_NOT_EXIST_MESSAGE, UserRole.ROLE_CUSTOMER.name())));

            roles.add(role);
        } else {
            userRoles.forEach(role -> {
                        switch (role) {
                            case "Admin":
                                Role adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN).orElseThrow(
                                        () -> new ResourceNotFoundException(String.format(
                                                ExceptionMessage.ROLE_NOT_EXIST_MESSAGE, UserRole.ROLE_ADMIN.name())));
                                roles.add(adminRole);
                                break;

                            default:
                                Role customerRole = roleRepository.findByName(UserRole.ROLE_CUSTOMER).orElseThrow(
                                        () -> new ResourceNotFoundException(String.format
                                                (ExceptionMessage.ROLE_NOT_EXIST_MESSAGE, UserRole.ROLE_CUSTOMER.name())));
                                roles.add(customerRole);
                        }
                    }
            );

        }
        return roles;

    }







}