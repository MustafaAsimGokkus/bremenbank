package com.bank.sure.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name="tbl_recipient")
@Entity
public class Recipient {
 @Id
 @GeneratedValue(strategy=GenerationType.IDENTITY)
 
 private Long id;
 
 @ManyToOne
 @JoinColumn(name="user_id")
 private User user;
 
 @OneToOne
 @JoinColumn(name="account_id",unique = true)
 private Account account;
 
 
 
 
}
