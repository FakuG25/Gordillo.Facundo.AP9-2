package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.*;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
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
    ClientRepository clientRepository;

    @Autowired
    LoanRepository loanRepository;


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;


    @Autowired
    ClientLoanRepository clientLoanRepository;

    @GetMapping("/loans")
    public ResponseEntity<List<LoanDTO>> getLoans() {
        List<LoanDTO> loanDTOs = loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(loanDTOs, HttpStatus.OK);
    }

    @RequestMapping("/loans/{id}")
    public LoanDTO getLoansId(@PathVariable Long id) {
        return new LoanDTO(loanRepository.findById(id).orElse(null));
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
        clientLoanRepository.save(newClientLoan);

        Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(),
                                    loan.getName() + " loan approved", LocalDate.now());
        transactionRepository.save(transaction);

                // Actualizar el saldo de la cuenta de destino
        double newBalance = destinationAccount.getBalance() + loanApplicationDTO.getAmount();
        destinationAccount.setBalance(newBalance);
        accountRepository.save(destinationAccount);

        return new ResponseEntity<>("El prestamo fue creado con exito", HttpStatus.CREATED);

            }
    }



