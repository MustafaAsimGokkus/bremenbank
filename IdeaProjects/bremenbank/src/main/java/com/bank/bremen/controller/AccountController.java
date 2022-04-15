package com.bank.bremen.controller;

import com.bank.bremen.controller.request.RecipientRequest;
import com.bank.bremen.controller.response.Response;
import com.bank.bremen.domain.User;
import com.bank.bremen.security.service.UserDetailsImpl;
import com.bank.bremen.service.RecipientService;
import com.bank.bremen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

   @RestController
   @RequestMapping("/account")
   public class AccountController {
      @Autowired
      private UserService userService;
   @Autowired
   private RecipientService recipientService;


@PostMapping("/recipient")
@PreAuthorize("hasRole('ADMIN')or hasRole('Customer')")
public ResponseEntity<Response> addRecipient(
        @Valid @RequestBody RecipientRequest request ){

   UserDetailsImpl userDetails =(UserDetailsImpl)
                                 SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();

  User user = userService.findById(userDetails.getId());
    recipientService.addRecipient(request, user);

    Response response=new Response();
    response.setMessage("Recipient added successfully");
    response.setIsSuccess(true);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
}
}

