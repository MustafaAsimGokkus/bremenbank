package com.bank.bremen.service;

import com.bank.bremen.controller.response.Response;
import com.bank.bremen.domain.Message;
import com.bank.bremen.exception.ResourceNotFoundException;
import com.bank.bremen.exception.message.ExceptionMessage;
import com.bank.bremen.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
   // Logger logger = LoggerFactory.getLogger(MessageService.class);
    public void createMessage (Message message){
    messageRepository.save(message);
  }

   public List<Message> getAll(){
        return messageRepository.findAll();
   }

   public Message getMessage (Long id ){
     Message message =  messageRepository.findById(id).orElseThrow(
             ()-> new ResourceNotFoundException(String.format(
                     ExceptionMessage.MESSAGE_NOT_FOUND_MESSAGE,id)));
     return message;
   }

   public void deleteMessage (Long id){
   Message message = messageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                String.format(ExceptionMessage.MESSAGE_NOT_FOUND_MESSAGE,id)));
   messageRepository.deleteById(message.getId());

   }

  public void updateMessage (Long id, Message message){
      Message foundMessage = getMessage(id);

      foundMessage.setName(message.getName());
      foundMessage.setSubject(message.getSubject());
      foundMessage.setEmail(message.getEmail());
      foundMessage.setBody(message.getBody());
      foundMessage.setPhoneNumber(message.getPhoneNumber());

      messageRepository.save(foundMessage);
  }

}
