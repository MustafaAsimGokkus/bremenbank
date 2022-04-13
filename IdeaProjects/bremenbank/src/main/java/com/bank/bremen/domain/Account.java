package com.bank.bremen.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Table(name="tbl_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false,unique = true)
  private Long accountNumber;

  private BigDecimal accountBalance;

   @OneToMany(mappedBy = "account", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
   @JsonIgnore
   private List <Transaction> transactions;

   @OneToOne
    @JoinColumn(name="user_id")
    private User user;

}
