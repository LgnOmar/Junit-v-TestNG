package org.example;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong; // For unique account numbers

public abstract class Account {
    private static final AtomicLong accountNumberGenerator = new AtomicLong(1000); // Start account numbers from 1000

    protected final String accountNumber;
    protected final Customer owner;
    protected double balance;

    public Account(Customer owner, double initialDeposit) {
        if (owner == null) {
            throw new IllegalArgumentException("Account must have an owner.");
        }
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative.");
        }
        this.accountNumber = String.valueOf(accountNumberGenerator.getAndIncrement());
        this.owner = owner;
        this.balance = initialDeposit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
        System.out.println("Deposited: " + amount + ". New balance: " + this.balance); // Simple logging
    }

    // Abstract method - must be implemented by subclasses
    public abstract void withdraw(double amount) throws InsufficientFundsException;

    // Common equals/hashCode based on account number
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", owner=" + owner.getName() + // Don't print owner object directly to avoid recursion if owner holds accounts
                ", balance=" + balance +
                '}';
    }
}