package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long loanId;

    private String name;

    private double maxAmount;

    @ElementCollection
    @Column(name = "loan")
    private List<Integer> loans = new ArrayList<>();

    @OneToMany(mappedBy="loan", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan(){

    }

    public Loan(String name, double maxAmount, List<Integer> loans) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.loans = loans;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getLoans() {
        return loans;
    }
    public void setLoans(List<Integer> loans) {
        this.loans = loans;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Client> getClients() {
        return clientLoans.stream().map(client -> client.getClient()).collect(Collectors.toSet());
    }

}
