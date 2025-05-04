package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*; // Import AssertJ assertions

class CustomerTest {

    @Test
    @DisplayName("Customer creation successful with valid name")
    void createCustomer_withValidName_shouldSucceed() {
        // Arrange
        String name = "Alice";

        // Act
        Customer customer = new Customer(name);

        // Assert
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getId()).isNotNull().isNotEmpty(); // Check ID is generated
    }

    @Test
    @DisplayName("Customer creation throws exception for null name")
    void createCustomer_withNullName_shouldThrowException() {
        // Arrange
        String name = null;

        // Act & Assert
        assertThatThrownBy(() -> {
            new Customer(name);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Customer name cannot be empty");
    }

    @Test
    @DisplayName("Customer creation throws exception for empty name")
    void createCustomer_withEmptyName_shouldThrowException() {
        // Arrange
        String name = "   "; // Whitespace only

        // Act & Assert
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new Customer(name); // Using specific AssertJ exception assertion
        }).withMessage("Customer name cannot be empty.");
    }

    @Test
    @DisplayName("Setting a new valid name updates the customer name")
    void setName_withValidName_shouldUpdateName() {
        // Arrange
        Customer customer = new Customer("Initial Name");
        String newName = "Updated Name";

        // Act
        customer.setName(newName);

        // Assert
        assertThat(customer.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("Setting null name throws exception")
    void setName_withNullName_shouldThrowException() {
        // Arrange
        Customer customer = new Customer("Initial Name");

        // Act & Assert
        assertThatIllegalArgumentException().isThrownBy(() -> {
            customer.setName(null);
        }).withMessage("Customer name cannot be empty.");
    }
}