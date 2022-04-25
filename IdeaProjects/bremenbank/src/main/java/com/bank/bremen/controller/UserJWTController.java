package com.bank.bremen.controller;

import com.bank.bremen.controller.dto.LoginDTO;
import com.bank.bremen.controller.dto.UserDTO;
import com.bank.bremen.controller.request.RegisterRequest;
import com.bank.bremen.controller.response.LoginResponse;
import com.bank.bremen.controller.response.Response;
import com.bank.bremen.domain.User;
import com.bank.bremen.security.JwtUtils;
import com.bank.bremen.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class UserJWTController {
   @Autowired
   private UserService userService;

   @Autowired
   private AuthenticationManager authenticationManager;

   @Autowired
   private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser (@Valid @RequestBody RegisterRequest registerRequest){
        userService.register(registerRequest);
        Response response = new Response();
        response.setMessage("User registered successfully");
        response.setIsSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(
                        loginDTO.getUserName(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);
        LoginResponse response=new LoginResponse(token);
        return ResponseEntity.ok(response);
    }










}
