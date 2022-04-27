package com.bank.bremen.exception.message;


public class BalanceNotAvailableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BalanceNotAvailableException(String message) {

        super(message);
    }
}
