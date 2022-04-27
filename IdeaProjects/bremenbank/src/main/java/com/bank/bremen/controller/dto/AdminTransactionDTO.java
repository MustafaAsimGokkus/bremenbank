package com.bank.bremen.controller.dto;

import com.bank.bremen.domain.Account;
import com.bank.bremen.domain.enumeration.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//we will keep all users transaction in this DTO
 @Data
 public class AdminTransactionDTO {
    private Account account;
    private LocalDateTime date;
    private  String description;
    private TransactionType type;
    private BigDecimal availableBalance;

}
