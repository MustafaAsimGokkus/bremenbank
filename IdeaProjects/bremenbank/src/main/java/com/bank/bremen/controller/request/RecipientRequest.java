package com.bank.bremen.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Set;

@Data
public class RecipientRequest {

    @NotBlank(message="Please provide not blank first name")
    @NotNull(message="Please provide your first name")
    @Size(min=1,  max=50,message="Your first name '${validatedValue}' must be between {min} and {max} chars long")
    private String name;

    @NotNull
    private Long accountNumber;

}
