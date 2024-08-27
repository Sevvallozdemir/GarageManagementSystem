package com.example.garage.Exception;


public class InvalidSlotNumberException extends RuntimeException {
    public InvalidSlotNumberException(String message) {
        super(message);
    }
}