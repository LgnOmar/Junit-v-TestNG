package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CheckingAccountTest {

    private Customer testCustomer;
    private CheckingAccount account;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("Checking Customer");
        // Initial setup: 100 balance, 50 overdraft limit
        account = new CheckingAccount(testCustomer, 100.0, 50.0);
    }

    @Test
    @DisplayName("Withdraw less than balance successful")
    void withdraw_lessThanBalance_succeeds() throws InsufficientFundsException {
        // Act
        account.withdraw(50.0);
        // Assert
        assertThat(account.getBalance()).isEqualTo(50.0);
    }

    @Test
    @DisplayName("Withdraw exactly balance successful")
    void withdraw_equalToBalance_succeeds() throws InsufficientFundsException {
        // Act
        account.withdraw(100.0);
        // Assert
        assertThat(account.getBalance()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("Withdraw into overdraft but within limit successful")
    void withdraw_intoOverdraftWithinLimit_succeeds() throws InsufficientFundsException {
        // Act
        account.withdraw(130.0); // 100 balance + 50 limit = 150 available
        // Assert
        assertThat(account.getBalance()).isEqualTo(-30.0); // Balance can go negative
    }

    @Test
    @DisplayName("Withdraw exactly up to overdraft limit successful")
    void withdraw_upToOverdraftLimit_succeeds() throws InsufficientFundsException {
        // Act
        account.withdraw(150.0);
        // Assert
        assertThat(account.getBalance()).isEqualTo(-50.0);
    }

    @Test
    @DisplayName("Withdraw exceeding overdraft limit throws InsufficientFundsException")
    void withdraw_exceedingOverdraftLimit_throwsInsufficientFundsException() {
        // Arrange
        double withdrawalAmount = 150.1;

        // Act & Assert
        assertThatExceptionOfType(InsufficientFundsException.class).isThrownBy(() -> {
            account.withdraw(withdrawalAmount);
        }).withMessageContaining("Insufficient funds including overdraft");

        // Check balance is unchanged
        assertThat(account.getBalance()).isEqualTo(100.0);
    }

    @Test
    @DisplayName("Account creation with negative overdraft throws exception")
    void createAccount_negativeOverdraft_throwsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new CheckingAccount(testCustomer, 100.0, -50.0);
        }).withMessage("Overdraft limit cannot be negative.");
    }
}