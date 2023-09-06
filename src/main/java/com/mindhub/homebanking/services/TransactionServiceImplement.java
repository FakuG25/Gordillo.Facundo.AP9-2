package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImplement implements TransactionService{

    @Override
    public void saveTransaction(Transaction transaction) {

    }

    @Override
    public List<TransactionDTO> getTransactionDTO() {
        return null;
    }

    @Override
    public TransactionDTO getTransactionDTO(Long id) {
        return null;
    }

    @Override
    public Transaction findById(Long id) {
        return null;
    }

    @Override
    public void saveAccount(Account account) {

    }

    @Override
    public List<AccountDTO> getAccountDTO() {
        return null;
    }

    @Override
    public AccountDTO findByNumber(String number) {
        return null;
    }
}
