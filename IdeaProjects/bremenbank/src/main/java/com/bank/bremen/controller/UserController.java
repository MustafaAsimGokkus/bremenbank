package com.bank.bremen.controller;

import com.bank.bremen.controller.dto.UserDTO;
import com.bank.bremen.controller.request.UserUpdateRequest;
import com.bank.bremen.controller.response.Response;
import com.bank.bremen.controller.response.UserInfoResponse;
import com.bank.bremen.domain.User;
import com.bank.bremen.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    private UserDTO convertToDTO(User user) {
        UserDTO userDTO=modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    @GetMapping("/userInfo")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<UserInfoResponse> getUserInfo(){
        User user = userService.findOneWithAuthoritiesByUserName();
        UserDTO userDTO=convertToDTO(user);
        UserInfoResponse response=new UserInfoResponse(userDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
   @PreAuthorize("hasRole('ADMIN')")

        public ResponseEntity<UserDTO> getUser(@PathVariable Long id){

        try{
            User user = userService.findById(id);
            UserDTO userDTO = convertToDTO(user);
            return ResponseEntity.ok(userDTO);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.ok(new UserDTO());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable){
      Page <UserDTO> userPage = userService.getUsers(pageable);
      return new ResponseEntity<>(userPage, HttpStatus.OK);

    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> updateUser(@PathVariable Long id,
    @Valid @RequestBody UserUpdateRequest updateRequest){
      User user = userService.findById(id);
      userService.updateUser(id,updateRequest);
      Response response = new Response();
      response.setMessage("User updated successfully");
      response.setIsSuccess(true);
      return ResponseEntity.ok(response);

    }




}
