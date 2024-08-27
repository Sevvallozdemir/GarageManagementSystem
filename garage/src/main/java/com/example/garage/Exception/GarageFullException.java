package com.example.garage.Exception;

public class GarageFullException extends RuntimeException {
    public GarageFullException(String message) {
        super(message);
    }
}