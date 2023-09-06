package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {
    void saveTransaction(Transaction transaction);

    List<TransactionDTO> getTransactionDTO();

    TransactionDTO getTransactionDTO(Long id);

    Transaction findById(Long id);

    void  saveAccount(Account account);

    List<AccountDTO> getAccountDTO();

    AccountDTO findByNumber(String number);

}
