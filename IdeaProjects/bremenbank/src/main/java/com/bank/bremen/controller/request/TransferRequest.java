package com.bank.bremen.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data

public class TransferRequest {
    @NotNull
    private Long recipientNumber;
    @NotNull
    private Double amount;
    @NotNull
    @Size(min=1,max=50,message="Please provide a comment")
    private String comment;

}
