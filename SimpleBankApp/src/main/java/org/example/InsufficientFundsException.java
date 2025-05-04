package org.example;

public class InsufficientFundsException extends Exception { // Or RuntimeException if you prefer unchecked
    public InsufficientFundsException(String message) {
        super(message);
    }
}