package com.bank.bremen.controller;

import com.bank.bremen.controller.dto.RecipientDTO;
import com.bank.bremen.controller.request.RecipientRequest;
import com.bank.bremen.controller.request.TransactionRequest;
import com.bank.bremen.controller.request.TransferRequest;
import com.bank.bremen.controller.response.BankStatementResponse;
import com.bank.bremen.controller.response.DashboardInfoResponse;
import com.bank.bremen.controller.response.RecipientListResponse;
import com.bank.bremen.controller.response.Response;
import com.bank.bremen.domain.Account;
import com.bank.bremen.domain.Recipient;
import com.bank.bremen.domain.User;
import com.bank.bremen.security.service.UserDetailsImpl;
import com.bank.bremen.service.AccountService;
import com.bank.bremen.service.RecipientService;
import com.bank.bremen.service.TransactionService;
import com.bank.bremen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

   @RestController
   @RequestMapping("/account")
   public class AccountController {

       @Autowired
       private UserService userService;

       @Autowired
       private RecipientService recipientService;

       @Autowired
       private AccountService accountService;

       @Autowired
       private TransactionService transactionService;


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

   @GetMapping("/customerstatement")
   @PreAuthorize("hasRole('CUSTOMER')")
   public ResponseEntity<DashboardInfoResponse> getCustomerStatement(
   @RequestParam(value="startDate")
           @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate startDate,
   @RequestParam(value="endDate")
           @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate endDate)   {

      UserDetailsImpl userDetails=(UserDetailsImpl) SecurityContextHolder
              .getContext()
              .getAuthentication()
              .getPrincipal();

       User user = userService.findById(userDetails.getId());
       Account account = accountService.getAccount(user);

       DashboardInfoResponse dbiResponse =
       transactionService.calculateCustomerStatement(account.getId(),startDate,endDate);

       return ResponseEntity.ok(dbiResponse);

   }
       @GetMapping("/bankstatement")
       @PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<BankStatementResponse> getBankStatement(
     //parameters for method
      @RequestParam(value="startDate")
                      @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate startDate,

     @RequestParam(value="endDate")
                      @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate endDate){

      BankStatementResponse bankStatement=
              transactionService.calculateBankStatement(startDate, endDate);
      return ResponseEntity.ok(bankStatement);


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

  @PostMapping("/deposit")
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity <Response> deposit (@Valid @RequestBody
                                            TransactionRequest transactionRequest){

  // 1. get the current user
     UserDetailsImpl userDetails =(UserDetailsImpl) SecurityContextHolder
             .getContext()
             .getAuthentication()
             .getPrincipal();
    User user = userService.findById(userDetails.getId());
    accountService.deposit(transactionRequest,user);

    Response response = new Response();
    response.setMessage("Amount successfully deposited");
    response.setIsSuccess(true);

    return new ResponseEntity<>(response,HttpStatus.CREATED);
  }

       @PostMapping("/withdraw")
       @PreAuthorize("hasRole('CUSTOMER')")
       public ResponseEntity <Response> withdraw (@Valid @RequestBody
                                                         TransactionRequest transactionRequest){

           // 1. get the current user
           UserDetailsImpl userDetails =(UserDetailsImpl) SecurityContextHolder
                   .getContext()
                   .getAuthentication()
                   .getPrincipal();
           User user = userService.findById(userDetails.getId());
           accountService.withdraw(transactionRequest,user);

           Response response = new Response();
           response.setMessage("Amount successfully withdrawn");
           response.setIsSuccess(true);

           return new ResponseEntity<>(response,HttpStatus.CREATED);
       }

       @PostMapping("/transfer")
       @PreAuthorize("hasRole('CUSTOMER')")
       public ResponseEntity <Response> transfer (@Valid @RequestBody
                                                  TransferRequest transferRequest){

           // 1. get the current user
           UserDetailsImpl userDetails =(UserDetailsImpl) SecurityContextHolder
                   .getContext()
                   .getAuthentication()
                   .getPrincipal();
           User user = userService.findById(userDetails.getId());
           accountService.transfer(transferRequest,user);

           Response response = new Response();
           response.setMessage("Amount successfully transferred");
           response.setIsSuccess(true);

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




}

