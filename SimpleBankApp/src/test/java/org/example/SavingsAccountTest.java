package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SavingsAccountTest {

    private Customer testCustomer;
    private SavingsAccount account;

    // @BeforeEach runs before every @Test method in this class
    @BeforeEach
    void setUp() {
        testCustomer = new Customer("Test Customer");
        // Initial setup for most tests
        account = new SavingsAccount(testCustomer, 100.0, 0.01); // 100 balance, 1% interest
    }

    @Test
    @DisplayName("Deposit increases balance correctly")
    void deposit_positiveAmount_increasesBalance() {
        // Arrange
        double depositAmount = 50.0;
        double expectedBalance = 100.0 + 50.0;

        // Act
        account.deposit(depositAmount);

        // Assert
        assertThat(account.getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    @DisplayName("Deposit zero amount throws exception")
    void deposit_zeroAmount_throwsException() {
        // Act & Assert
        assertThatIllegalArgumentException().isThrownBy(() -> {
            account.deposit(0.0);
        }).withMessage("Deposit amount must be positive.");
    }

    @Test
    @DisplayName("Withdraw successful decreases balance")
    void withdraw_validAmount_decreasesBalance() throws InsufficientFundsException { // Test can throw checked exception
        // Arrange
        double withdrawalAmount = 30.0;
        double expectedBalance = 100.0 - 30.0;

        // Act
        account.withdraw(withdrawalAmount);

        // Assert
        assertThat(account.getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    @DisplayName("Withdraw more than balance throws InsufficientFundsException")
    void withdraw_amountExceedingBalance_throwsInsufficientFundsException() {
        // Arrange
        double withdrawalAmount = 150.0;

        // Act & Assert
        assertThatExceptionOfType(InsufficientFundsException.class).isThrownBy(() -> {
            account.withdraw(withdrawalAmount);
        }).withMessageContaining("Insufficient funds"); // Check part of the message
        // Check balance remains unchanged
        assertThat(account.getBalance()).isEqualTo(100.0);
    }

    @Test
    @DisplayName("Withdraw negative amount throws IllegalArgumentException")
    void withdraw_negativeAmount_throwsIllegalArgumentException() {
        // Arrange
        double withdrawalAmount = -50.0;

        // Act & Assert
        assertThatIllegalArgumentException().isThrownBy(() -> {
            account.withdraw(withdrawalAmount);
        }).withMessage("Withdrawal amount must be positive.");
        // Check balance remains unchanged
        assertThat(account.getBalance()).isEqualTo(100.0);
    }

    @Test
    @DisplayName("Apply interest increases balance correctly")
    void applyInterest_increasesBalanceByCorrectAmount() {
        // Arrange
        double expectedInterest = 100.0 * 0.01; // 1.0
        double expectedBalance = 100.0 + expectedInterest;

        // Act
        account.applyInterest();

        // Assert
        assertThat(account.getBalance()).isEqualTo(expectedBalance, within(0.001)); // Use offset for floating point comparison
    }

    @Test
    @DisplayName("Account creation with negative interest rate throws exception")
    void createAccount_negativeInterestRate_throwsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new SavingsAccount(testCustomer, 100.0, -0.01);
        }).withMessage("Interest rate cannot be negative.");
    }
}