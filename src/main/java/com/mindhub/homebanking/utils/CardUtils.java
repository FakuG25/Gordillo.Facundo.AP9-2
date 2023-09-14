package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CardUtils {
    @Autowired
    static AccountRepository accountRepository;

    @Autowired
    static CardRepository cardRepository;

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public static String uniqueRandomNumberAccount(){
        String number;
        do {
            number = ("VIN-" + getRandomNumber(1, 99999999));
            return number;
        } while (accountRepository.existsByNumber(number));

    }

    public static String createNumberAccount(){
        String number = "VIN-" + getRandomNumber(1, 99999999);
        return number;
    }

    public static String uniqueRandomNumberCard(){
        String number;
        do {
            number = (getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999));
            return number;
        } while (cardRepository.existsByNumber(number));

    }
}
