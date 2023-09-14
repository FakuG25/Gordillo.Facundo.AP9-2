package com.mindhub.homebanking.homebanking;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
@DataJpaTest

@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {
        @Autowired
        private  LoanRepository loanRepository;

        @Autowired
        private AccountRepository accountRepository;

        @Autowired
        private CardRepository cardRepository;

        @Autowired
        private ClientRepository clientRepository;

        @Autowired
        private TransactionRepository transactionRepository;



    @Test
        public void existLoans(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans,is(not(empty())));
        }

        @Test
        public void existPersonalLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
        }

        @Test
        public void existAnyAccount(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts, not(empty()));
        }

        @Test
        public void hasSizeAccount(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts, hasSize(5));
        }

        @Test
        public void isAnythingCard(){
            List<Card> cards = cardRepository.findAll();
            assertThat(cards, anything());
        }
        @Test
        public void containsCard(){
            List<Card> cards = cardRepository.findAll();
            assertThat(cards, hasItem(hasProperty("number", is("4668-3674-5634-8190"))));
        }

    @Test
    public void FindByEmail() {
        Optional<Client> foundClient = Optional.ofNullable(clientRepository.findByEmail("melba@mindhub.com"));
        assertThat(foundClient.isPresent(), is(true));
    }

    @Test
    public void HasClientsSize() {
        List<Client> allClients = clientRepository.findAll();
        assertThat(allClients, hasSize(3));
    }

    @Test
    public void transactionsEqualTo() {
        Transaction transaction1 = new Transaction(TransactionType.CREDIT, 100.0, "Transaction 1", LocalDate.now());
        transactionRepository.save(transaction1);
        assertThat(transaction1.getAmount(), equalTo(100.0));
    }

    @Test
    public void containsTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(hasProperty("description", is("mov01"))));
    }

}


