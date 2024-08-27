package com.example.garage.Service;

import com.example.garage.Model.Vehicle;

public class VehicleFactory {
    public static Vehicle createVehicle(String plate, String color, String type) {
        switch (type.toLowerCase()) {
            case "car":
                return new Vehicle(plate, color, 1);
            case "jeep":
                return new Vehicle(plate, color, 2);
            case "truck":
                return new Vehicle(plate, color, 4);
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + type);
        }
    }
}
