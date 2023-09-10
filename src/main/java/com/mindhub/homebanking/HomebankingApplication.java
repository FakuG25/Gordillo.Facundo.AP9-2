package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired AccountRepository accountRepository;
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

/*
	@Autowired
	PasswordEncoder passwordEncoder;


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (args) -> {

			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123"));
			Client client2 = new Client("Jorge", "Alvarez", "AlvarezJorge@gmail.com", passwordEncoder.encode("1234"));
			Client client3 = new Client("admin", "admin", "admin@admin.com", passwordEncoder.encode("admin"));
			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);

			Account account1 = new Account(uniqueRandomNumber(),LocalDate.now(), 5000);
			Account account2 = new Account(uniqueRandomNumber(), LocalDate.now().plusDays(1), 7500);
			Account account3 = new Account(uniqueRandomNumber(), LocalDate.now(), 10000);
			Account account4 = new Account(uniqueRandomNumber(), LocalDate.now().minusDays(10), 4000);

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client2.addAccount(account4);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			Transaction transaction1 = new Transaction(TransactionType.DEBIT, -400, "mov01", LocalDate.now());
			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 600, "mov02", LocalDate.now());
			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 1200, "mov03", LocalDate.now());
			Transaction transaction4 = new Transaction(TransactionType.DEBIT, -100, "mov04", LocalDate.now());
			Transaction transaction5 = new Transaction(TransactionType.CREDIT, 3000, "mov05", LocalDate.now());
			Transaction transaction6 = new Transaction(TransactionType.DEBIT, -1000, "mov06", LocalDate.now());
			Transaction transaction7 = new Transaction(TransactionType.CREDIT, 5000, "mov07", LocalDate.now());
			Transaction transaction8 = new Transaction(TransactionType.DEBIT, -3000, "mov08", LocalDate.now());

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction3);
			account2.addTransaction(transaction4);
			account3.addTransaction(transaction5);
			account3.addTransaction(transaction6);
			account4.addTransaction(transaction7);
			account4.addTransaction(transaction8);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
			transactionRepository.save(transaction7);
			transactionRepository.save(transaction8);

			Loan loan1 = new Loan("Hipotecario", 500000, List.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal", 100000, List.of(6,12,24));
			Loan loan3 = new Loan("Automotriz", 500000, List.of(6,12,24,36));
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(300000,60);
			ClientLoan clientLoan2 = new ClientLoan(50000,12);
			ClientLoan clientLoan3 = new ClientLoan(100000,24);
			ClientLoan clientLoan4 = new ClientLoan(200000,36);
			clientLoan1.setClient(client1);
			clientLoan1.setLoan(loan1);
			clientLoan2.setClient(client1);
			clientLoan2.setLoan(loan2);
			clientLoan3.setClient(client2);
			clientLoan3.setLoan(loan2);
			clientLoan3.setClient(client2);
			clientLoan3.setLoan(loan3);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);



			Card card1 = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.DEBIT, CardColor.GOLD, "4668-3674-5634-8190", 683, LocalDate.now(), LocalDate.now().plusYears(5));
			Card card2 = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.CREDIT, CardColor.SILVER, "4459-4924-3798-1332", 372, LocalDate.now(), LocalDate.now().plusYears(4));
			Card card3 = new Card(client2.getFirstName() + " " + client2.getLastName(), CardType.DEBIT, CardColor.TITANIUM, "4360-6847-7892-5633\n", 616, LocalDate.now(), LocalDate.now().plusYears(5));
			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);



		};

 */
	}

