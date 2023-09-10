package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.controllers.ClientController;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
   private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id) {

        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }

    @Override
    public ClientDTO getAll(String email) {
        return new ClientDTO(clientRepository.findByEmail(email));
    }

}
