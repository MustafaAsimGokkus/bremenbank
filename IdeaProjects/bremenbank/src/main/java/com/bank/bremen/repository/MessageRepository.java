package com.bank.bremen.repository;

import com.bank.bremen.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository <Message, Long> {

}
