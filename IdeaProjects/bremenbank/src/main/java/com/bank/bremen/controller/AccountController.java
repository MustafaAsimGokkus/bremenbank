package com.bank.bremen.controller;

import com.bank.bremen.controller.dto.RecipientDTO;
import com.bank.bremen.controller.request.RecipientRequest;
import com.bank.bremen.controller.response.RecipientListResponse;
import com.bank.bremen.controller.response.Response;
import com.bank.bremen.domain.Recipient;
import com.bank.bremen.domain.User;
import com.bank.bremen.security.service.UserDetailsImpl;
import com.bank.bremen.service.RecipientService;
import com.bank.bremen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

   @RestController
   @RequestMapping("/account")
   public class AccountController {

       @Autowired
       private UserService userService;

       @Autowired
       private RecipientService recipientService;

       @PostMapping("/recipient")
       @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
       public ResponseEntity<Response> addRecipient(@Valid @RequestBody
                                         RecipientRequest recipientRequest){
        //current user is the user that makes the request
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder
                                       .getContext()
                                       .getAuthentication()
                                       .getPrincipal();

       //current user
       User user =  userService.findById(userDetails.getId());
       recipientService.addRecipient(recipientRequest, user);
        Response response = new Response();
        response.setMessage("Recipient added succesfully");
        response.setIsSuccess(true);

       return ResponseEntity.ok(response);

       }
     @GetMapping("/recipient")
     @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")

       public ResponseEntity<RecipientListResponse> getRecipient(){
  UserDetailsImpl userDetails=
         (UserDetailsImpl)SecurityContextHolder
          .getContext()
          .getAuthentication()
          .getPrincipal(); //this code will give us current user

  User user = userService.findById(userDetails.getId()); //this code will give us user object by id

      List <Recipient> recipients = user.getRecipients();
     List<RecipientDTO> recipientDTOList=
      recipients.stream().map(this::convertToDTO).collect(Collectors.toList());
       RecipientListResponse response = new RecipientListResponse(recipientDTOList);
      return new ResponseEntity<>(response,HttpStatus.CREATED);
  }

  private RecipientDTO convertToDTO(Recipient recipient){
           RecipientDTO recipientDTO = new RecipientDTO();
           User user = recipient.getAccount().getUser();

           recipientDTO.setId(recipient.getId());
           recipientDTO.setFirstName(user.getFirstName());
           recipientDTO.setLastName(user.getLastName());
           recipientDTO.setEmail(user.getEmail());
           recipientDTO.setPhoneNumber(user.getPhoneNumber());
           recipientDTO.setAccountNumber(recipient.getAccount().getAccountNumber());

    return recipientDTO;

  }

  @DeleteMapping("/recipient/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")

  public ResponseEntity<Response> deleteRecipient(@PathVariable Long id){
  UserDetailsImpl userDetails =
     (UserDetailsImpl)SecurityContextHolder
             .getContext()
             .getAuthentication()
             .getPrincipal();
  User user = userService.findById(userDetails.getId());
  recipientService.removeRecipient(user,id);

  Response response = new Response();
  response.setMessage("Recipient deleted successfully");
  response.setIsSuccess(true);
  return ResponseEntity.ok(response);

  }







}

