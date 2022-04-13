package com.bank.bremen.domain;

import com.bank.bremen.domain.enumeration.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


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

    @Column(length=200,nullable = false)
    private String description;

    private double amount;

    private BigDecimal availableBalance;

    private TransactionType type;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

}
