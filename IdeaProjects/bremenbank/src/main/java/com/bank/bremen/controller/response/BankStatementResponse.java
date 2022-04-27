package com.bank.bremen.controller.response;

import com.bank.bremen.controller.dto.AdminTransactionDTO;
import lombok.Data;

import java.util.List;
@Data
public class BankStatementResponse {

   private List<AdminTransactionDTO> list;
   private double totalBalance;
}
