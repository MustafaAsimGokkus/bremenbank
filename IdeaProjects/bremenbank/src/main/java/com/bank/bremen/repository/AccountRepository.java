package com.bank.bremen.repository;

import com.bank.bremen.domain.Account;
import com.bank.bremen.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {


    Optional<Account> findByAccountNumber(Long accountNumber);
    Optional<Account> findByUser(User user);

    @Query("select SUM (a.accountBalance) FROM Account a")
    Double getTotalBalance();


}
