package com.bank.bremen.service;

import com.bank.bremen.controller.request.RecipientRequest;
import com.bank.bremen.domain.Account;
import com.bank.bremen.domain.Recipient;
import com.bank.bremen.domain.User;
import com.bank.bremen.exception.ConflictException;
import com.bank.bremen.exception.ResourceNotFoundException;
import com.bank.bremen.exception.message.ExceptionMessage;
import com.bank.bremen.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipientService {
@Autowired
private RecipientRepository recipientRepository;
@Autowired
private AccountService accountService;
    public void addRecipient(RecipientRequest request, User user){

        //we check if recipient exists or not
        Account account = accountService.findByAccountNumber(request.getAccountNumber());
        //this code is not to add your own account as recipient
        if(user.getId().equals(account.getUser().getId())){
            throw new ConflictException(ExceptionMessage.RECIPIENT_ADD_ERROR_MESSAGE);
        }
        validateRecipient(request,account);
        Recipient recipient = new Recipient();
        recipient.setUser(user);
        recipient.setAccount(account);
        recipientRepository.save(recipient);
    }

    private void validateRecipient(RecipientRequest request,Account account){
     String name =   account.getUser().getFirstName()+" "+account.getUser().getLastName();
    if(!name.equalsIgnoreCase(request.getName())){
      throw new ResourceNotFoundException(ExceptionMessage.RECIPIENT_VALIDATION_ERROR_MESSAGE);
    }

    }

}
