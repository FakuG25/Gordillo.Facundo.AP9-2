package com.mindhub.homebanking.controllers;



import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class ClientController {
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public String createNumberAccount(){
        String number = "VIN-" + getRandomNumber(1, 99999999);
        return number;
    }
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }
            if (clientRepository.findByEmail(email) != null) {
                return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
            }
            Client client = clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
           Account account = accountRepository.save(new Account(createNumberAccount(),LocalDate.now(), 0));
            client.addAccount(account);
            accountRepository.save(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    @RequestMapping(path = "/clients/current", method = RequestMethod.GET)
    public ClientDTO getAll(Authentication authentication) {
    return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }



}
