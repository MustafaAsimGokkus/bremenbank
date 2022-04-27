package com.bank.bremen.controller;

import com.bank.bremen.controller.dto.MessageDTO;
import com.bank.bremen.domain.Message;
import com.bank.bremen.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageControllerTest {

    //if I call a method from message service use mock message service
    @Mock
    MessageService mockMessageService;

    @Spy //we dont create a mock it is like real object
    ModelMapper modelMapper;

    @InjectMocks
    MessageController underTest;

    @Test
    void getMessages_shouldReturnMessageDTOList(){
      Message message = new Message();
      message.setId(1l);
      message.setPhoneNumber("456-566-8914");
      message.setBody("This is message body");
      message.setSubject("This is subject");
      message.setName("This is name");
      message.setEmail("aaa@amail.com");

        Message message2 = new Message();
        message2.setId(1l);
        message2.setPhoneNumber("132-566-8547");
        message2.setBody("This is message body2");
        message2.setSubject("This is subject2");
        message2.setName("This is name2");
        message2.setEmail("aaa@amail.com2");

        List <Message> expected = Arrays.asList(message,message2);
        when(mockMessageService.getAll()).thenReturn(expected);
        ResponseEntity<List<MessageDTO>> response = underTest.getAll();
        List <MessageDTO> actual =response.getBody();

        assertAll(

                     ()-> assertNotNull(actual),
                     ()-> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                     ()->assertEquals(expected.size(),actual.size())
                );
    }
}
