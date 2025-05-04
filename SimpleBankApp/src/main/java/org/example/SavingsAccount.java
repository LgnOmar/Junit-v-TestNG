package org.example;

public class SavingsAccount extends Account {
    private double interestRate; // Example: 0.01 for 1%

    public SavingsAccount(Customer owner, double initialDeposit, double interestRate) {
        super(owner, initialDeposit);
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew: " + amount + " from Savings. New balance: " + balance);
        } else {
            throw new InsufficientFundsException("Insufficient funds in Savings Account " + accountNumber + ". Balance: " + balance + ", Requested: " + amount);
        }
    }

    public void applyInterest() {
        double interest = balance * interestRate;
        deposit(interest); // Reuse deposit logic
        System.out.println("Applied interest: " + interest + ". New balance: " + balance);
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", owner=" + owner.getName() +
                ", balance=" + balance +
                ", interestRate=" + interestRate +
                '}';
    }
}