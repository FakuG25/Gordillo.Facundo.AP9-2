package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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
        @RequestMapping("/accounts")
        public List<AccountDTO> getAccounts() {
            return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
        }
        @GetMapping("/accounts/id")
        public AccountDTO getAccount(@PathVariable Long id){
            return this.accountRepository.findById(id).map(AccountDTO::new).orElse(null);
        }
        @GetMapping("/clients/current/id")
        public List<AccountDTO> getAccounts (Authentication authentication){
            Client client = this.clientRepository.findByEmail(authentication.getName());
            return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
        }
        @PostMapping("/clients/current/accounts")
        public ResponseEntity<Object> createAccount (Authentication authentication){
            Client client = this.clientRepository.findByEmail((authentication.getName()));

            if (client.getAccounts().size() < 3) {
               Account account =  accountRepository.save(new Account(uniqueRandomNumber(), LocalDate.now(), 0));
                client.addAccount(account);
                accountRepository.save(account);
                return new ResponseEntity<>("La cuenta fue creada con exito", HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>("No se pudo crear la cuenta ya que el maximo son 3", HttpStatus.FORBIDDEN);
            }

        }
        @GetMapping("/clients/current/accounts")
        public List<AccountDTO> getCurrentAccounts (Authentication authentication){
            Client client = this.clientRepository.findByEmail(authentication.getName());
            return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
        }


    }
