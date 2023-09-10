package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public String uniqueRandomNumber(){
        String number;
        do {
            number = ("VIN-" + getRandomNumber(1, 99999999));
            return number;
        } while (accountRepository.existsByNumber(number));

    }

        @Autowired
        private ClientRepository clientRepository;

        @Autowired
        private AccountService accountService;

        @Autowired
        private ClientService clientService;

        @RequestMapping("/accounts")
        public List<AccountDTO> getAccounts() {
            return accountService.getAccounts();
        }

        @GetMapping("/accounts/id")
        public AccountDTO getAccount(@PathVariable Long id){
            return accountService.getAccount(id);
        }

        @GetMapping("/clients/current/id")
        public List<AccountDTO> getAccountsCurrent(Authentication authentication){
            return accountService.getAccountsCurrent(authentication.getName());
        }
        @PostMapping("/clients/current/accounts")
        public ResponseEntity<Object> createAccount (Authentication authentication){
            Client client = this.clientRepository.findByEmail((authentication.getName()));

            if (client.getAccounts().size() < 3) {
               Account account =  accountRepository.save(new Account(uniqueRandomNumber(), LocalDate.now(), 0));
                client.addAccount(account);
                accountService.save(account);
                return new ResponseEntity<>("La cuenta fue creada con exito", HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>("No se pudo crear la cuenta ya que el maximo son 3", HttpStatus.FORBIDDEN);
            }

        }
        @GetMapping("/clients/current/accounts")
        public List<AccountDTO> getCurrentAccounts (Authentication authentication){
            return accountService.getAccountsCurrent(authentication.getName());
        }


    }
