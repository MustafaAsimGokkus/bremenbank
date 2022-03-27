package com.bank.sure.controller.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Data
public class RegisterRequest { //similar to dto..it is like a wrapper class
	//delete annotations about the db
	 
	 @NotBlank(message="Please provide the first name not blank")	
	 @NotNull(message="Please provide your first name")
	 @Size(min=1,max=50, message="Your firstname'${validatedValue}' must be between {min} and {max} chars long")
	 private String firstName;
	 
	 @NotBlank(message="Please provide the last name not blank")	
	 @NotNull(message="Please provide your last name")
	 @Size(min=1,max=50, message="Your lastname'${validatedValue}' must be between {min} and {max} chars long")
	 private String lastName;
	 
	 @Pattern(regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$")
	 private String ssn;
	 
	 @NotBlank(message="Please provide the username not blank")	
	 @NotNull(message="Please provide your username")
	 @Size(min=5,max=50, message="Your username'${validatedValue}' must be between {min} and {max} chars long")
	 private String userName;
	 
	 @Email(message="please provide a valid email")
	 @Size(min=5,max=100, message="Your email'${validatedValue}' must be between {min} and {max} chars long")
	 private String email;
	 
	 @NotBlank(message="Please provide the password not blank")	
	 @NotNull(message="Please provide your password")
	 @Size(min=5,max=50, message="Your password '${validatedValue}' must be between {min} and {max} chars long")
	 private String password;
	 
	 @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
		     message = "Please provide valid phone number")
	 private String phoneNumber;
	 
	 @NotBlank(message="Please provide the address not blank")	
	 @NotNull(message="Please provide your address")
	 @Size(min=20,max=200, message="Your address '${validatedValue}' must be between {min} and {max} chars long")
	 private String address;

	 @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
	 private String dateOfBirth;
	 
	 
	 private Set<String> roles;
	 
	
	 
}
