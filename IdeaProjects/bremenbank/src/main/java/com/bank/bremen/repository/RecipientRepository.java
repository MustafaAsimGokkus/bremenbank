package com.bank.bremen.repository;

import com.bank.bremen.domain.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient,Long> {
}
