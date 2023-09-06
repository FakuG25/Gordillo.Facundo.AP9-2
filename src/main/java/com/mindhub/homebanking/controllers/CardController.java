package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.CardColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RequestMapping("/api")
@RestController
public class CardController {
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public String uniqueRandomNumber(){
        String number;
        do {
            number = (getRandomNumber(1, 9999) + "-" + getRandomNumber(1, 9999) + "-" + getRandomNumber(1, 9999) + "-" + getRandomNumber(1, 9999));
            return number;
        } while (cardRepository.existsByNumber(number));

    }

@Autowired
private ClientRepository clientRepository;

@Autowired
private CardRepository cardRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication, CardColor cardColor, CardType cardType){
        Client client = this.clientRepository.findByEmail((authentication.getName()));
        if (client.getCards().size() >= 6) {
            return new ResponseEntity<>("Se alcanzo el limite maximo de tarjetas.", HttpStatus.FORBIDDEN);
        }

        // Usando client accedo al cliente autenticado, obtengo sus tarjetas, las filtro y las cuento
        long creditDebitCardCount = client.getCards().stream()
                .filter(card -> card.getType() == CardType.CREDIT || card.getType() == CardType.DEBIT)
                .count();

        if (creditDebitCardCount >= 6) {
            return new ResponseEntity<>("Se alcanzo el limite maximo de tarjetas de tipo CREDIT y DEBIT.", HttpStatus.FORBIDDEN);
        }

        // Filtro las tarjetas segun su tipo y las cuento
        long sameTypeCardCount = client.getCards().stream()
                .filter(card -> card.getType() == cardType)
                .count();

        if (sameTypeCardCount >= 3) {
            return new ResponseEntity<>("Se alcanzo el limite maximo de tarjetas de este tipo.", HttpStatus.FORBIDDEN);
        }

        // Filtro las tarjetas y cuento que tarjetas de que color hay en el respectivo tipo de tarjeta
        long sameColorCardCount = client.getCards().stream()
                .filter(card -> card.getType() == cardType && card.getColor() == cardColor)
                .count();

        if (sameColorCardCount >= 2) {
            return new ResponseEntity<>("Se alcanzo el limite maximo de tarjetas de este color", HttpStatus.FORBIDDEN);
        }

        Card card = new Card(client.getFirstName() + " " + client.getLastName(),
                cardType, cardColor, uniqueRandomNumber(),
                getRandomNumber(100, 999), LocalDate.now(), LocalDate.now().plusYears(5));
        client.addCard(card);
        cardRepository.save(card);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}



