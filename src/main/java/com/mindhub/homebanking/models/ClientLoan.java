package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private int payments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan")
    private Loan loan;

    // Constructor sin parámetros
    public ClientLoan() {
    }

    public ClientLoan(double amount, int payments) {
        this.amount = amount;
        this.payments = payments;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClientObject(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoanObject(Loan loan) {
        this.loan = loan;
    }

    // Métodos para establecer relaciones
    public void setClient(Client client) {
        this.client = client;
        client.getClientLoans().add(this); // Agregar esta instancia de ClientLoan a la lista de Client
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
        loan.getClientLoans().add(this); // Agregar esta instancia de ClientLoan a la lista de Loan
    }
}
