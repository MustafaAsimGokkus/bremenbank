package com.bank.bremen.service;

import com.bank.bremen.controller.dto.AccountDTO;
import com.bank.bremen.controller.dto.AdminTransactionDTO;
import com.bank.bremen.controller.dto.TransactionDTO;
import com.bank.bremen.controller.response.BankStatementResponse;
import com.bank.bremen.controller.response.DashboardInfoResponse;
import com.bank.bremen.domain.Account;
import com.bank.bremen.domain.Transaction;
import com.bank.bremen.domain.enumeration.TransactionType;
import com.bank.bremen.repository.AccountRepository;
import com.bank.bremen.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountRepository accountRepository;


    public void saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }
    //sDate: 03.26.2022 00:00:00, eDate 03.27.2022 23:59:59
    public DashboardInfoResponse calculateCustomerStatement(
        Long accountId, LocalDate startDate, LocalDate endDate){
        LocalDateTime sDate = startDate.atStartOfDay();
        LocalDateTime eDate = endDate.atTime(LocalTime.MAX);

        List <Transaction> transactions =
                transactionRepository.getCustomerTransactions(accountId,sDate,eDate);

        List <Transaction> deposit= transactions
                .stream()
                .filter(t->t.getType()== TransactionType.DEPOSIT)
                .collect(Collectors.toList());
        List <Transaction> withdraw = transactions
                .stream()
                .filter(t->t.getType()==TransactionType.WITHDRAW)
                .collect(Collectors.toList());

        List <Transaction> transfer = transactions
                .stream()
                .filter(t->t.getType()==TransactionType.TRANSFER)
                .collect(Collectors.toList());

     double sumDeposit  = deposit.stream().mapToDouble (t->t.getAmount()).sum();
     double sumWithdraw = withdraw.stream().mapToDouble(t->t.getAmount()).sum();
     double sumTransfer = transfer.stream().mapToDouble(t->t.getAmount()).sum();

     List<TransactionDTO> transactionDTOList=
             transactions.stream().map(this::convertToDTO).collect(Collectors.toList());

     Account account = accountRepository.getById(accountId);
     AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);

     //dbi to be returned
        DashboardInfoResponse dbiResponse
           = new DashboardInfoResponse(transactionDTOList
                                       ,sumDeposit
                                       ,sumWithdraw
                                       ,sumTransfer,accountDTO);
     return dbiResponse;
    }

  public BankStatementResponse calculateBankStatement(LocalDate startDate,LocalDate endDate){
       LocalDateTime sDate = startDate.atStartOfDay();
       LocalDateTime eDate = endDate.atTime(LocalTime.MAX);

   List <Transaction> transactions=
           transactionRepository.getBankStatement(sDate,eDate);

   List <AdminTransactionDTO> adminTransactionsDTO =
           transactions.stream().map(this::convertToAdminDTO).collect(Collectors.toList());


    Double totalBalance = accountRepository.getTotalBalance();
    BankStatementResponse response = new BankStatementResponse();
    response.setTotalBalance(totalBalance.doubleValue());
    response.setList(adminTransactionsDTO);
    return response;
  }








    public TransactionDTO convertToDTO(Transaction transaction){
       return  modelMapper.map(transaction,TransactionDTO.class);
    }

    public AdminTransactionDTO convertToAdminDTO(Transaction transaction){
        return modelMapper.map(transaction, AdminTransactionDTO.class);
    }

}
