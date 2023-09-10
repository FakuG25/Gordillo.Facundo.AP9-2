package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.*;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientLoanService;
import com.mindhub.homebanking.services.LoanApplicationService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class LoanApplicationController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoanRepository loanRepository;


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/loans")
    public ResponseEntity<List<LoanDTO>> getLoans() {
        return loanApplicationService.getLoans();
    }

    @RequestMapping("/loans/{id}")
    public LoanDTO getLoansId(@PathVariable Long id) {
        return loanApplicationService.getLoansId(id);
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<String> applyForLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Account destinationAccount = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());
        Long loanId = loanApplicationDTO.getLoanId();
        Loan loan = loanRepository.findByLoanId(loanId);

        if (loanApplicationDTO.getAmount() == 0){
            return new ResponseEntity<>("El monto no puede ser 0", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getPayments() == 0){
            return new ResponseEntity<>("La cantidad de cuotas no puede ser 0", HttpStatus.FORBIDDEN);
        }

        if (loan == null) {
            return new ResponseEntity<>("El préstamo con el ID " + loanId + " no existe.", HttpStatus.NOT_FOUND);
        }

        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("La cantidad solicitada supera el tope", HttpStatus.FORBIDDEN);
        }

        if (!loan.getLoans().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("La cantidad de cuotas solicitada no estan disponibles para este prestamo", HttpStatus.FORBIDDEN);
        }

        if (destinationAccount == null) {
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.NOT_FOUND);
        }

        if (!destinationAccount.getClient().equals(client)){
            return new ResponseEntity<>("La cuenta de destino no le pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }
        ClientLoan newClientLoan = new ClientLoan(loanApplicationDTO.getAmount() * 1.20,
                                                loanApplicationDTO.getPayments());
        clientLoanService.save(newClientLoan);

        Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(),
                                    loan.getName() + " loan approved", LocalDate.now());
        transactionService.save(transaction);

                // Actualizar el saldo de la cuenta de destino
        double newBalance = destinationAccount.getBalance() + loanApplicationDTO.getAmount();
        destinationAccount.setBalance(newBalance);
        accountService.save(destinationAccount);

        return new ResponseEntity<>("El prestamo fue creado con exito", HttpStatus.CREATED);

            }
    }



