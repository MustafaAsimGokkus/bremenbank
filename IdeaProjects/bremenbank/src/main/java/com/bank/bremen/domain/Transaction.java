package com.bank.bremen.domain;

import com.bank.bremen.domain.enumeration.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tbl_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private LocalDateTime date;

    @Column(length=200,nullable = false)
    private String description;

    private double amount;

    private BigDecimal availableBalance;

    private TransactionType type;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    //constructor without id parameter
    public Transaction(String description,
                       LocalDateTime date,
                       double amount,
                       @NotNull Double requestAmount, BigDecimal availableBalance,
                       TransactionType type,
                       Account account) {

        this.description = description;
        this.date=date;
        this.amount = amount;
        this.availableBalance = availableBalance;
        this.type = type;
        this.account = account;
    }
}
