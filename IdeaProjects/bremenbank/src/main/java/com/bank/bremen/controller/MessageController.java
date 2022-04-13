package com.bank.bremen.controller;

import com.bank.bremen.controller.dto.MessageDTO;
import com.bank.bremen.controller.response.Response;
import com.bank.bremen.domain.Message;
import com.bank.bremen.service.MessageService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
public class MessageController {
  @Autowired
  private MessageService messageService;

  @Autowired
  private ModelMapper modelMapper;

  private static Logger logger = LoggerFactory.getLogger(MessageController.class);

//  public MessageController(MessageService messageService, ModelMapper modelMapper){
//      this.messageService=messageService;
//      this.modelMapper=modelMapper;
//  }


  private Message convertTo(MessageDTO messageDTO){
     Message message = modelMapper.map(messageDTO,Message.class);
     return message;
  }

  private MessageDTO convertToDTO(Message message){
      MessageDTO messageDTO= modelMapper.map(message,MessageDTO.class);
      return messageDTO;
  }


  @PostMapping
   public ResponseEntity<Response> createMessage(@Valid @RequestBody MessageDTO messageDTO){
//      Message message = new Message();
//      message.setId(messageDTO.getId());
//      message.setSubject(messageDTO.getSubject());
//      message.setBody(messageDTO.getBody());
//      message.setEmail(messageDTO.getEmail());
//      message.setPhoneNumber(messageDTO.getPhoneNumber());
    Message message = convertTo(messageDTO);
      messageService.createMessage(message);
      Response response = new Response();
      response.setMessage("Message saved successfully");
      response.setIsSuccess(true);

      return new ResponseEntity<>(response,HttpStatus.OK);
  }
 @GetMapping
    public ResponseEntity<List<MessageDTO>> getAll(){
       List <Message> allMessages = messageService.getAll();
       List <MessageDTO> messageDTOList=allMessages
               .stream()
               .map(this::convertToDTO)
               .collect(Collectors.toList());

       return new ResponseEntity<>(messageDTOList, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity <MessageDTO> getMessage(@PathVariable Long id){

      Message message = messageService.getMessage(id);
      MessageDTO messageDTO = convertToDTO(message);
      return ResponseEntity.ok(messageDTO);
    }

    @GetMapping("/request")
    public ResponseEntity <MessageDTO> getMessageByRequest(@RequestParam Long id){
        Message message = messageService.getMessage(id);
        MessageDTO messageDTO = convertToDTO(message);

        return ResponseEntity.ok(messageDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteMessage(@PathVariable Long id){
      logger.info("Client want to delete message id: {}", id);
      messageService.deleteMessage(id);
      Response response = new Response();
      response.setMessage("Message deleted successfully");
      response.setIsSuccess(true);
     return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateMessage (
            @PathVariable Long id, @Valid @RequestBody  MessageDTO messageDTO){

       Message message = convertTo(messageDTO);
        messageService.updateMessage(id,message);
        Response response = new Response();
        response.setMessage("Message updated successfully");
        response.setIsSuccess(true);
        return  ResponseEntity.ok(response);

    }



}
