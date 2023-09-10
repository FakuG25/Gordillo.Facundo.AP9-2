package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAccounts();

    AccountDTO getAccount(@PathVariable Long id);

    List<AccountDTO> getAccountsCurrent(String email);
    void save(Account account);
}
