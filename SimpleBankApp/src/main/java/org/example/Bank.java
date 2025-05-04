package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional; // Good practice for methods that might not find something

public class Bank {
    private final List<Customer> customers;
    private final List<Account> accounts;

    public Bank() {
        this.customers = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    public Customer addCustomer(String name) {
        Customer customer = new Customer(name);
        customers.add(customer);
        System.out.println("Added customer: " + customer);
        return customer;
    }

    public Account openSavingsAccount(Customer owner, double initialDeposit, double interestRate) {
        if (!customers.contains(owner)) {
            throw new IllegalArgumentException("Customer does not exist in this bank.");
        }
        Account account = new SavingsAccount(owner, initialDeposit, interestRate);
        accounts.add(account);
        System.out.println("Opened savings account: " + account);
        return account;
    }

    public Account openCheckingAccount(Customer owner, double initialDeposit, double overdraftLimit) {
        if (!customers.contains(owner)) {
            throw new IllegalArgumentException("Customer does not exist in this bank.");
        }
        Account account = new CheckingAccount(owner, initialDeposit, overdraftLimit);
        accounts.add(account);
        System.out.println("Opened checking account: " + account);
        return account;
    }

    public Optional<Account> findAccount(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return Optional.of(acc);
            }
        }
        return Optional.empty(); // Not found
    }

    public Optional<Customer> findCustomer(String customerId) {
        for (Customer cust : customers) {
            if (cust.getId().equals(customerId)) {
                return Optional.of(cust);
            }
        }
        return Optional.empty(); // Not found
    }

    public List<Account> getAccountsForCustomer(Customer customer) {
        if (!customers.contains(customer)) {
            throw new IllegalArgumentException("Customer does not exist in this bank.");
        }
        List<Account> customerAccounts = new ArrayList<>();
        for (Account acc : accounts) {
            if (acc.getOwner().equals(customer)) {
                customerAccounts.add(acc);
            }
        }
        return Collections.unmodifiableList(customerAccounts); // Return immutable view
    }

    public List<Customer> getAllCustomers() {
        return Collections.unmodifiableList(customers);
    }

    public List<Account> getAllAccounts() {
        return Collections.unmodifiableList(accounts);
    }
}