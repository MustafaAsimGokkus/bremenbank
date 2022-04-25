package com.bank.bremen.repository;

import com.bank.bremen.domain.Account;
import com.bank.bremen.domain.Recipient;
import com.bank.bremen.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient,Long> {
    Boolean existsByUserAndAccount(User user, Account account);
    //user : exact parameter and we match this with query param
  @Query("SELECT r FROM Recipient r WHERE r.user.id=:userId and r.account.id=:accountId")
  Optional<Recipient> findRecipientByUserAndAccount(@Param("userId") Long userId,
  @Param ("accountId") Long accountId);

}
