package com.bank.bremen.controller.response;

import com.bank.bremen.controller.dto.AccountDTO;
import com.bank.bremen.controller.dto.TransactionDTO;
import com.bank.bremen.domain.Account;
import com.bank.bremen.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DashboardInfoResponse {
    List <TransactionDTO> list;
    double totalDeposit;
    double totalWithdraw;
    double totalTransfer;
    AccountDTO account;
}
