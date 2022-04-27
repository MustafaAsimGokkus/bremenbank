package com.bank.bremen.service;

import com.bank.bremen.controller.request.TransactionRequest;
import com.bank.bremen.controller.request.TransferRequest;
import com.bank.bremen.domain.Account;
import com.bank.bremen.domain.Transaction;
import com.bank.bremen.domain.User;
import com.bank.bremen.domain.enumeration.TransactionType;
import com.bank.bremen.exception.ResourceNotFoundException;
import com.bank.bremen.exception.message.BalanceNotAvailableException;
import com.bank.bremen.exception.message.ExceptionMessage;
import com.bank.bremen.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    TransactionService transactionService;

    public Account createAccount(User user) {
        Account account = new Account();
        Long accountNumber = getAccountNumber();
        account.setAccountBalance(BigDecimal.valueOf(0.0));
        account.setAccountNumber(accountNumber);
        account.setUser(user);
        accountRepository.save(account);

        return accountRepository.findByAccountNumber(accountNumber).

                orElseThrow(() -> new ResourceNotFoundException(String.format(
                        ExceptionMessage.ACCOUNT_NOT_FOUND_MESSAGE, accountNumber
                )));
    }

    public Account getAccount(User user){
       Account account=
       accountRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException(
               String.format(ExceptionMessage.USERACCOUNT_NOT_FOUND_MESSAGE,user.getId())));

       return account;
    }




    public Account findByAccountNumber(Long number) {
        return accountRepository.findByAccountNumber(number).
                orElseThrow(() -> new ResourceNotFoundException(String.format(
                        ExceptionMessage.ACCOUNT_NOT_FOUND_MESSAGE, number)));

    }


    private Long getAccountNumber() {
        long smallest = 1000_0000_0000_0000L;
        long biggest = 9999_9999_9999_9999L;
        return ThreadLocalRandom.current().nextLong(smallest, biggest);
    }

    public void deposit(TransactionRequest request, User user) {
        Account account = getAccount(user);


        double amount = request.getAmount();
        account
                .setAccountBalance(account.getAccountBalance()
                        .add(BigDecimal.valueOf(amount)));

        Transaction transaction = new Transaction(
                request.getComment()
                , LocalDateTime.now()
                , amount
                , request.getAmount()
                , account.getAccountBalance()
                , TransactionType.DEPOSIT
                , account);
        transactionService.saveTransaction(transaction); //1st transaction
        accountRepository.save(account);                 //2nd transaction
    }

    public void withdraw(TransactionRequest request, User user) {
        Account account = getAccount(user);

        double amount = request.getAmount();
        if (account.getAccountBalance().longValue() < request.getAmount()) {
            throw new BalanceNotAvailableException(String.format(ExceptionMessage.BALANCE_NOT_AVAILABLE_MESSAGE));
        }
        account.setAccountBalance(
                account
                        .getAccountBalance()
                        .subtract(BigDecimal
                                .valueOf(request.getAmount())));

        Transaction transaction = new Transaction(
                request.getComment()
                , LocalDateTime.now()
                , amount
                , request.getAmount()
                , account.getAccountBalance()
                , TransactionType.WITHDRAW
                , account);
        transactionService.saveTransaction(transaction);
        accountRepository.save(account);


    }

    public void transfer(TransferRequest request, User user) {
        Account account = getAccount(user);

        if (account.getAccountBalance().longValue() < request.getAmount()) {
            throw new BalanceNotAvailableException(
                    ExceptionMessage.BALANCE_NOT_AVAILABLE_MESSAGE);
        }
        Account toAccount = accountRepository.findByAccountNumber(request.getRecipientNumber()).orElseThrow(
        () -> new ResourceNotFoundException(String.format(
         ExceptionMessage.USERACCOUNT_NOT_FOUND_MESSAGE, request.getRecipientNumber())));

        double amount = request.getAmount();

        //it is the sender
        account
                .setAccountBalance(
                        account.getAccountBalance()
                                .subtract(BigDecimal.valueOf(amount)));
        //it is the receiver
        toAccount
                .setAccountBalance(
                        account.getAccountBalance()
                           .add(BigDecimal.valueOf(amount)));
        //sender transaction
        Transaction transactionFrom = new Transaction(
                request.getComment()+" Transferred to: "+toAccount.getAccountNumber()
                , LocalDateTime.now()
                , amount
                , request.getAmount()
                , account.getAccountBalance()
                , TransactionType.TRANSFER
                , account);
        //receiver transaction
        Transaction transactionTo = new Transaction(
                request.getComment()+" Received from: "+account.getAccountNumber()
                , LocalDateTime.now()
                , amount
                , request.getAmount()
                , toAccount.getAccountBalance()
                , TransactionType.TRANSFER
                , toAccount);
        transactionService.saveTransaction(transactionFrom);
        transactionService.saveTransaction(transactionTo);
        accountRepository.save(account);
        accountRepository.save(toAccount);
        }

    }
