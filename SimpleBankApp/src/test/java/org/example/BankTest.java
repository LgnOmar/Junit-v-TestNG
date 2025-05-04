package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class BankTest {

    private Bank bank;
    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        // Pre-add some customers for tests
        customer1 = bank.addCustomer("Alice");
        customer2 = bank.addCustomer("Bob");
    }

    @Test
    @DisplayName("Add customer increases customer count")
    void addCustomer_increasesCustomerListSize() {
        // Arrange
        int initialSize = bank.getAllCustomers().size();

        // Act
        bank.addCustomer("Charlie");

        // Assert
        assertThat(bank.getAllCustomers()).hasSize(initialSize + 1);
    }

    @Test
    @DisplayName("Find existing customer returns correct customer")
    void findCustomer_existingId_returnsCustomerOptional() {
        // Act
        Optional<Customer> found = bank.findCustomer(customer1.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(customer1); // Uses Customer.equals based on ID
        assertThat(found.get().getName()).isEqualTo("Alice");
    }

    @Test
    @DisplayName("Find non-existing customer returns empty optional")
    void findCustomer_nonExistingId_returnsEmptyOptional() {
        // Act
        Optional<Customer> found = bank.findCustomer("non-existent-id");

        // Assert
        assertThat(found).isEmpty();
    }


    @Test
    @DisplayName("Open savings account adds account to bank")
    void openSavingsAccount_validCustomer_addsAccount() {
        // Arrange
        int initialAccountCount = bank.getAllAccounts().size();

        // Act
        Account savings = bank.openSavingsAccount(customer1, 100.0, 0.01);

        // Assert
        assertThat(bank.getAllAccounts()).hasSize(initialAccountCount + 1);
        assertThat(bank.getAllAccounts()).contains(savings);
        assertThat(savings).isInstanceOf(SavingsAccount.class);
        assertThat(savings.getOwner()).isEqualTo(customer1);
    }

    @Test
    @DisplayName("Open checking account adds account to bank")
    void openCheckingAccount_validCustomer_addsAccount() {
        // Arrange
        int initialAccountCount = bank.getAllAccounts().size();

        // Act
        Account checking = bank.openCheckingAccount(customer1, 200.0, 50.0);

        // Assert
        assertThat(bank.getAllAccounts()).hasSize(initialAccountCount + 1);
        assertThat(bank.getAllAccounts()).contains(checking);
        assertThat(checking).isInstanceOf(CheckingAccount.class);
        assertThat(checking.getOwner()).isEqualTo(customer1);
    }

    @Test
    @DisplayName("Open account for non-existent customer throws exception")
    void openAccount_nonExistentCustomer_throwsException() {
        // Arrange
        Customer rogueCustomer = new Customer("Rogue"); // Not added to bank

        // Act & Assert
        assertThatIllegalArgumentException().isThrownBy(() -> {
            bank.openSavingsAccount(rogueCustomer, 100.0, 0.01);
        }).withMessage("Customer does not exist in this bank.");
    }


    @Test
    @DisplayName("Find existing account returns correct account")
    void findAccount_existingNumber_returnsAccountOptional() {
        // Arrange
        Account checking = bank.openCheckingAccount(customer1, 200.0, 50.0);
        String accountNumber = checking.getAccountNumber();

        // Act
        Optional<Account> found = bank.findAccount(accountNumber);

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(checking);
    }

    @Test
    @DisplayName("Find non-existing account returns empty optional")
    void findAccount_nonExistingNumber_returnsEmptyOptional() {
        // Act
        Optional<Account> found = bank.findAccount("0000");

        // Assert
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Get accounts for customer returns correct accounts")
    void getAccountsForCustomer_returnsOnlyTheirAccounts() {
        // Arrange
        Account savingsAlice = bank.openSavingsAccount(customer1, 100.0, 0.01);
        Account checkingAlice = bank.openCheckingAccount(customer1, 200.0, 50.0);
        Account checkingBob = bank.openCheckingAccount(customer2, 500.0, 100.0);

        // Act
        List<Account> aliceAccounts = bank.getAccountsForCustomer(customer1);

        // Assert
        assertThat(aliceAccounts).hasSize(2)
                .containsExactlyInAnyOrder(savingsAlice, checkingAlice); // Order might not be guaranteed
        assertThat(aliceAccounts).doesNotContain(checkingBob);

        // Check Bob's account
        List<Account> bobAccounts = bank.getAccountsForCustomer(customer2);
        assertThat(bobAccounts).hasSize(1)
                .containsExactly(checkingBob);
    }

    @Test
    @DisplayName("Get accounts for non-existent customer throws exception")
    void getAccountsForCustomer_nonExistentCustomer_throwsException() {
        // Arrange
        Customer rogueCustomer = new Customer("Rogue");

        // Act & Assert
        assertThatIllegalArgumentException().isThrownBy(() -> {
            bank.getAccountsForCustomer(rogueCustomer);
        }).withMessage("Customer does not exist in this bank.");
    }
}