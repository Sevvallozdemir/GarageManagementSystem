package com.example.garage.Controller;


import com.example.garage.Exception.GarageFullException;
import com.example.garage.Exception.InvalidSlotNumberException;
import com.example.garage.Exception.InvalidVehicleException;
import com.example.garage.Service.VehicleFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.garage.Model.Vehicle;
import com.example.garage.Service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/garage")
public class GarageController {
    @Autowired
    private GarageService garageService;

    @PostMapping("/park")
    public ResponseEntity<String> park(@RequestParam String plate, @RequestParam String color, @RequestParam String type) {
        try {
            Vehicle vehicle = VehicleFactory.createVehicle(plate, color, type);
            return ResponseEntity.ok(garageService.park(vehicle));
        } catch (InvalidVehicleException | GarageFullException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/leave")
    public ResponseEntity<String> leave(@RequestParam int slotNumber) {
        try {
            return ResponseEntity.ok(garageService.leave(slotNumber));
        } catch (InvalidSlotNumberException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok(garageService.status());
    }

    private int getSizeByType(String type) {
        switch (type.toLowerCase()) {
            case "car":
                return 1;
            case "jeep":
                return 2;
            case "truck":
                return 4;
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + type);
        }
    }

}
