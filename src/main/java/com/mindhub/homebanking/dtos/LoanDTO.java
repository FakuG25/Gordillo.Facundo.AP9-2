package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {
    private Long id;

    private String name;

    private double maxAmount;

    private List<Integer> payments;


    public LoanDTO(Loan loan) {
        this.id = loan.getLoanId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getLoans();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
