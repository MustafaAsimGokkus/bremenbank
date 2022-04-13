package com.bank.bremen.service;

import com.bank.bremen.domain.Account;
import com.bank.bremen.domain.User;
import com.bank.bremen.exception.ResourceNotFoundException;
import com.bank.bremen.exception.message.ExceptionMessage;
import com.bank.bremen.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

    public Account createAccount(User user){
        Account account = new Account();
        Long accountNumber = getAccountNumber();
        account.setAccountBalance(BigDecimal.valueOf(0.0));
        account.setAccountNumber(accountNumber);
        account.setUser(user);
        accountRepository.save(account);

       return accountRepository.findByAccountNumber(accountNumber).

           orElseThrow(()->new ResourceNotFoundException(String.format(
                       ExceptionMessage.ACCOUNT_NOT_FOUND_MESSAGE,accountNumber
               )));
    }

    private Long getAccountNumber(){
        long smallest = 1000_0000_0000_0000L;
         long biggest = 9999_9999_9999_9999L;
         return ThreadLocalRandom.current().nextLong(smallest,biggest);
    }
}
