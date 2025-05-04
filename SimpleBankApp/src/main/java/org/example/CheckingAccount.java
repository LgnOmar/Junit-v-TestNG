package org.example;

public class CheckingAccount extends Account {
    private double overdraftLimit; // Example: 100.0

    public CheckingAccount(Customer owner, double initialDeposit, double overdraftLimit) {
        super(owner, initialDeposit);
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        }
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        // Check if withdrawal is possible within balance + overdraft limit
        if (balance + overdraftLimit >= amount) {
            balance -= amount;
            System.out.println("Withdrew: " + amount + " from Checking. New balance: " + balance);
        } else {
            throw new InsufficientFundsException("Insufficient funds including overdraft in Checking Account " + accountNumber + ". Available (incl. overdraft): " + (balance + overdraftLimit) + ", Requested: " + amount);
        }
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", owner=" + owner.getName() +
                ", balance=" + balance +
                ", overdraftLimit=" + overdraftLimit +
                '}';
    }
}