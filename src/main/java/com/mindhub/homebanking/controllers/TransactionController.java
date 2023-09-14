package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@RequestMapping("/api")
@RestController
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionService.getTransactions();
    }

    @GetMapping("/transactions/id")
        public TransactionDTO getTransaction(@PathVariable Long id){
        return transactionService.getTransaction(id);
    }
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransfer(Authentication authentication,
                                                 @RequestParam Double amount,
                                                 @RequestParam String description,
                                                 @RequestParam String fromAccountNumber,
                                                 @RequestParam String toAccountNumber) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Account fromAccount = accountRepository.findByNumber(fromAccountNumber);
        Account toAccount = accountRepository.findByNumber(toAccountNumber);


        if (description.isEmpty()) {
            return new ResponseEntity<>("La descripcion no puede estar vacia", HttpStatus.FORBIDDEN);
        }
        if (amount == null) {
            return new ResponseEntity<>("El monto no puede estar vacio", HttpStatus.FORBIDDEN);
        }
        if(!accountRepository.existsByNumber(toAccountNumber)){
            return new ResponseEntity<>("El numero de cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }

        if(!accountRepository.existsByNumber(fromAccountNumber)){
            return new ResponseEntity<>("El numero de cuenta de origen no existe", HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("El numero de cuenta no puede estar vacio", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber == null){
            return new ResponseEntity<>("El numero de la cuenta de origen no puede estar vacia", HttpStatus.FORBIDDEN);
        }
        if (toAccountNumber == null){
            return new ResponseEntity<>("El numero de la cuenta de destino no puede estar vacia", HttpStatus.FORBIDDEN);

        }
        if (!fromAccount.getClient().equals(client)){
            return new ResponseEntity<>("La cuenta de origen no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }
        if (fromAccount.getBalance() == 0){
            return new ResponseEntity<>("La cuenta no tiene saldo suficiente", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("La cuenta de origen es la misma que la cuenta de destino", HttpStatus.FORBIDDEN);
        }
      Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -amount,description, LocalDate.now());
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount,description, LocalDate.now());

        fromAccount.addTransaction(debitTransaction);
        toAccount.addTransaction(creditTransaction);

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        transactionService.save(debitTransaction);
        transactionService.save(creditTransaction);

        accountService.save(fromAccount);
        accountService.save(toAccount);

        return new ResponseEntity<>("Transferencia realizada con exito", HttpStatus.CREATED);
    }
    }

