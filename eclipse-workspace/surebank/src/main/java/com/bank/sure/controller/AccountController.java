package com.bank.sure.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.sure.controller.dto.RecipientDTO;
import com.bank.sure.controller.request.RecipientRequest;
import com.bank.sure.controller.response.RecipientListResponse;
import com.bank.sure.controller.response.Response;
import com.bank.sure.domain.Recipient;
import com.bank.sure.domain.User;
import com.bank.sure.security.service.UserDetailsImpl;
import com.bank.sure.service.AccountService;
import com.bank.sure.service.RecipientService;
import com.bank.sure.service.UserService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipientService recipientService;
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/recipient")
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<Response> addRecipient(@Valid @RequestBody RecipientRequest recipientRequest){
		UserDetailsImpl userDetail=(UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user=userService.findById(userDetail.getId());
		recipientService.addRecipient(recipientRequest, user);
		
		Response response=new Response();
		response.setMessage("Recipient added successfully");
		response.setSuccess(true);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/recipient")
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	
	public ResponseEntity<RecipientListResponse> getRecipient(){
	//1st get current user
		UserDetailsImpl userDetails=(UserDetailsImpl) SecurityContextHolder.
				getContext().
				getAuthentication().
				getPrincipal();
		
		User user =	userService.findById(userDetails.getId());
	   //in User class we keep recipients ==> private List<Recipient> recipients;
		//lets get recipients and put them in local variable
	      List<Recipient> recipients=user.getRecipients();//now we should convert this to DTO
	     
	      List<RecipientDTO> recipientsDTOList =recipients.stream().
	    		 map(this::convertToDTO).
	    		 collect(Collectors.toList());
	
	     RecipientListResponse response = new RecipientListResponse(recipientsDTOList);
	     
	     return new ResponseEntity<> (response,HttpStatus.OK);
	
	}
	
	@DeleteMapping("/recipient/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<Response> deleteRecipient(@PathVariable Long id){
		//1st get current user	
	
	UserDetailsImpl userDetails= (UserDetailsImpl)SecurityContextHolder.
			                       getContext().
		                           getAuthentication().
			                       getPrincipal();
		
	User user = userService.findById(userDetails.getId());   
	
	recipientService.removeRecipient(user, id) ;
	    
	Response response = new Response();
	response.setMessage("Recipient deleted successfully");
	response.setSuccess(true);
	
	return ResponseEntity.ok(response);
		
	}
	
	
	
	
	
	
	
	
	
	//method to convert recipient list to dto
	private RecipientDTO convertToDTO(Recipient recipient) {
		RecipientDTO recipientDTO = new RecipientDTO();
		
		User user = recipient.getAccount().getUser();
		recipientDTO.setId(recipient.getId());
		recipientDTO.setFirstName(user.getFirstName());
		recipientDTO.setLastName(user.getLastName());
		recipientDTO.setPhoneNumber(user.getPhoneNumber());
		recipientDTO.setEmail(user.getEmail());
		recipientDTO.setAccountNumber(recipient.getAccount().getAccountNumber());
		return recipientDTO;
		
	}
	
	
	
	
	
	
	
	

}