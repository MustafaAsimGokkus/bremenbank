package com.bank.sure.controller.response;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bank.sure.controller.dto.UserDTO;
import com.bank.sure.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoResponse { //this is just a wrapper class to create an answer to the client
 private UserDTO user;
}

//@GetMapping
//@PreAuthorize("hasRole('ADMIN')")
//public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
//	UserService.find
//}