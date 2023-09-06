package com.mindhub.homebanking.models;

public class LoanApplication {
    private Long loanId;

    private double amount;

    private int payments;

    private String toAccountNumber;

    public LoanApplication(){
    }

    public LoanApplication(double amount, int payments, String toAccountNumber) {
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;
    }

    public Long getLoanId() {
        return loanId;
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

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }
}
