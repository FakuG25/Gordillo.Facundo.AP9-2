package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccount(Long id) {
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }

   public List<AccountDTO> getAccountsCurrent(String email) {
       Client client = this.clientRepository.findByEmail(email);
       return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
   }

    public void save(Account account){
        accountRepository.save(account);
    }
}
