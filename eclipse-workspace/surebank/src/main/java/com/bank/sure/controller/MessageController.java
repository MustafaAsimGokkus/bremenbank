package com.bank.sure.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.sure.controller.response.Response;
import com.bank.sure.domain.Message;
import com.bank.sure.service.MessageService;

@RestController
@RequestMapping("/message")//match the url
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	
	@PostMapping
	public ResponseEntity <Response> createMessage(@Valid @RequestBody Message message){
		messageService.createMessage(message);
		
		Response response = new Response();
		response.setMessage("Message saved successfully");
		response.setSuccess(true);
		
		
		
		//static
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
	}
	
      @GetMapping
      public ResponseEntity <List<Message>> getAll(){
	  
    	 List <Message> allMessage=messageService.getAll();
    	 
    	  return new ResponseEntity<>(allMessage,HttpStatus.OK);
}   
      
      //localhost/8080/message/id
	@GetMapping("/{id}")
	public ResponseEntity <Message> getMessage(@PathVariable Long id){
		Message msg = messageService.getMessage(id);
		return ResponseEntity.ok(msg);
	}
	
	@GetMapping("/request")
	public ResponseEntity <Message> getMessagebyRequest(@RequestParam Long id){
		Message msg = messageService.getMessage(id);
		return ResponseEntity.ok(msg);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity <Response> deleteMessage(@PathVariable Long id){
		
		messageService.deleteMessage(id);
		
		Response response = new Response ();
		response.setMessage("Message deleted successfully");
		response.setSuccess(true);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response> updateMessage (@PathVariable Long id, @Valid @RequestBody Message message){
		messageService.updateMessage(id,message);
		
		Response response = new Response();
		response.setMessage("Update successfull");
		response.setSuccess(true);
		return ResponseEntity.ok(response);
	}
}
