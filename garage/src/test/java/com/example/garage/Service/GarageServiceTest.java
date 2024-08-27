package com.example.garage.Service;

import com.example.garage.Exception.InvalidSlotNumberException;
import com.example.garage.Exception.InvalidVehicleException;
import com.example.garage.Model.Slot;
import com.example.garage.Model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GarageServiceTest {

    private GarageService garageService;

    @BeforeEach
    public void setUp() {
        garageService = new GarageService();
    }

    @Test
    public void testParkValidVehicle() {
        Vehicle vehicle = new Vehicle("34ABC34", "Blue", 1);
        String result = garageService.park(vehicle);
        assertEquals("Allocated 1 slot(s). (başarılı)", result);
    }

    @Test
    public void testParkInvalidVehicle() {
        Vehicle vehicle = new Vehicle("", "Blue", 0);
        assertThrows(InvalidVehicleException.class, () -> garageService.park(vehicle));
    }

    @Test
    public void testLeaveValidSlot() {
        Vehicle vehicle = new Vehicle("34ABC34", "Blue", 1);
        garageService.park(vehicle);
        String result = garageService.leave(1);
        assertEquals("Slot1 is now free", result);
    }

    @Test
    public void testLeaveInvalidSlot() {
        assertThrows(InvalidSlotNumberException.class, () -> garageService.leave(999));
    }

    @Test
    public void testStatus() {
        String result = garageService.status();
        assertTrue(result.contains("Slot 1 is free"));
    }
}
