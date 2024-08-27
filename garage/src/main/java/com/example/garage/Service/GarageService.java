package com.example.garage.Service;

import com.example.garage.Exception.InvalidSlotNumberException;
import com.example.garage.Exception.InvalidVehicleException;
import com.example.garage.Model.Slot;
import com.example.garage.Model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;


@Slf4j
@Service
public class GarageService {
    private static GarageService instance;
    private final List<Slot> slots;

    public GarageService() {
        this.slots = Collections.synchronizedList(new ArrayList<>()); // Thread-safe list
        for (int i = 1; i <= 10; i++) {
            slots.add(new Slot(i, false));
        }
        log.info("Garage initialized with {} slots", slots.size());
    }

    public static synchronized GarageService getInstance() {
        if (instance == null) {
            instance = new GarageService();
        }
        return instance;
    }

    public synchronized String park(Vehicle vehicle) {
        // Validation
        if (vehicle == null || vehicle.getSize() < 1) {
            log.error("Invalid vehicle or vehicle size: {}", vehicle);
            throw new InvalidVehicleException("Invalid vehicle or vehicle size: " + vehicle);
        }

        log.info("Trying to park vehicle: {}", vehicle);
        int requiredSlots = vehicle.getSize();
        int availableSlotIndex = findAvailableSlots(requiredSlots);

        if (availableSlotIndex == -1) {
            log.warn("Garage is full. Unable to park vehicle {}", vehicle);
            return "Garage is full";
        }

        for (int i = availableSlotIndex; i < availableSlotIndex + requiredSlots; i++) {
            slots.get(i).setOccupied(true);
        }

        log.info("Vehicle parked. Allocated {} slot(s) from slot number {} to {}.",
                requiredSlots, availableSlotIndex + 1, availableSlotIndex + requiredSlots);
        return "Allocated " + requiredSlots + " slot(s). (başarılı)";
    }

    public synchronized String leave(int slotNumber) {
        // Validation
        if (slotNumber < 1 || slotNumber > slots.size()) {
            log.error("Invalid slot number: {}", slotNumber);
            throw new InvalidSlotNumberException("Invalid slot number: " + slotNumber);
        }

        log.info("Trying to free slot: {}", slotNumber);
        Optional<Slot> slot = slots.stream().filter(s -> s.getNumber() == slotNumber).findFirst();

        if (slot.isPresent() && slot.get().isOccupied()) {
            int index = slot.get().getNumber() - 1;
            slots.get(index).setOccupied(false);
            log.info("Slot {} is now free", slotNumber);
            return "Slot " + slotNumber + " is now free";
        }
        log.warn("Slot {} is already free or does not exist.", slotNumber);
        throw new InvalidSlotNumberException("Slot " + slotNumber + " is already free or does not exist.");
    }

    public synchronized String status() {
        log.info("Checking garage status");
        StringBuilder status = new StringBuilder("Status:\n");
        for (Slot slot : slots) {
            if (slot.isOccupied()) { // Slot doluysa
                status.append("Slot ").append(slot.getNumber()).append(" is occupied\n");
            } else {  // Slot boşsa
                status.append("Slot ").append(slot.getNumber()).append(" is free.\n");
            }
        }
        log.info("Final status: {}", status.toString());
        return status.toString();
    }

    private synchronized int findAvailableSlots(int requiredSlots) {
        log.debug("Finding available slots for a vehicle that needs {} slot(s).", requiredSlots);
        for (int i = 0; i <= slots.size() - requiredSlots; i++) {
            boolean canPark = true;
            for (int j = i; j < i + requiredSlots; j++) {
                if (slots.get(j).isOccupied()) {
                    canPark = false;
                    break;
                }
            }
            if (canPark) {
                log.debug("Found available slots starting from slot number {}.", i + 1);
                return i;
            }
        }
        log.warn("No available slots found for a vehicle that needs {} slot(s).", requiredSlots);
        return -1;
    }
}
