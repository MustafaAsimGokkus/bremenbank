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

import java.util.Optional;

@Service
public class RecipientService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RecipientRepository recipientRepository;


    public void addRecipient(RecipientRequest recipientRequest, User user){
        //check if recipient exists in our account table
        Account account =
          accountService.findByAccountNumber(recipientRequest.getAccountNumber());
        //you cannot add your own account number as a recipient
        if(user.getId().equals(account.getUser().getId())){
            throw new ConflictException(
                    ExceptionMessage.RECIPIENT_ADD_ERROR_MESSAGE);
        }

//      Boolean isExist = recipientRepository.existsByUserAndAccount(user,account);
//    if(isExist) {
//        throw new ConflictException("Recipient already has been saved ");
//    }

        Optional<Recipient> foundRecipient=

        recipientRepository.findRecipientByUserAndAccount(user.getId(), account.getId());

        if(foundRecipient.isPresent()){
            throw new ConflictException(
                    ExceptionMessage.RECIPIENT_ADD_ERROR_MESSAGE);
        }

       validateRecipient(recipientRequest,account);
        Recipient recipient = new Recipient();
        recipient.setUser(user);
        recipient.setAccount(account);
        recipientRepository.save(recipient);

    }


    private void validateRecipient(RecipientRequest recipientRequest,Account account){
   String name = account.getUser().getFirstName()+" "+ account.getUser().getLastName();

   if(!name.equalsIgnoreCase(recipientRequest.getName())){
       throw new ResourceNotFoundException(
               ExceptionMessage.RECIPIENT_VALIDATION_ERROR_MESSAGE);
     }

    }

  public void removeRecipient(User user, Long id){ //id for recipient user is current user
  Recipient recipient =
        recipientRepository.findById(id).orElseThrow(
          ()-> new ResourceNotFoundException(
                  String.format(ExceptionMessage.RECIPIENT_NOT_FOUND_MESSAGE,id)));

  //we need to check if this recipient has this user
   if(user.getId().equals(recipient.getUser().getId())){
      recipientRepository.deleteById(recipient.getId());
   }else{
       throw new ConflictException("You don't have permission to delete recipient");
   }
  }

}
