package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionService {
    void save(Transaction transaction);

    List<TransactionDTO> getTransactions();

    TransactionDTO getTransaction(@PathVariable Long id);


}
