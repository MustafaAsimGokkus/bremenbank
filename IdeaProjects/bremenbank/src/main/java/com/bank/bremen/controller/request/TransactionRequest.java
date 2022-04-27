package com.bank.bremen.controller.request;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Transactional
@Data
public class TransactionRequest {

    @NotNull
    private Double amount;


    @NotNull
    @Size(min=1,max=50,message="Please provide a comment")
    private String comment;
}
