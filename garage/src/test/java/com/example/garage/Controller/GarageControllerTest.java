package com.example.garage.Controller;

import com.example.garage.Exception.GarageFullException;
import com.example.garage.Exception.InvalidSlotNumberException;
import com.example.garage.Exception.InvalidVehicleException;
import com.example.garage.Model.Vehicle;
import com.example.garage.Service.GarageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(GarageController.class)
public class GarageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GarageService garageService;

    @Test
    public void testPark() throws Exception {
        String plate = "34ABC34";
        String color = "Blue";
        String type = "car";

        Vehicle vehicle = new Vehicle(plate, color, 1);
        Mockito.when(garageService.park(vehicle)).thenReturn("Allocated 1 slot(s). (başarılı)");

        mockMvc.perform(post("/garage/park")
                        .param("plate", plate)
                        .param("color", color)
                        .param("type", type)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Allocated 1 slot(s). (başarılı)"));
    }

    @Test
    public void testParkWithInvalidVehicle() throws Exception {
        Mockito.when(garageService.park(Mockito.any())).thenThrow(new InvalidVehicleException("Invalid vehicle"));

        mockMvc.perform(post("/garage/park")
                        .param("plate", "")
                        .param("color", "Blue")
                        .param("type", "car")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid vehicle"));
    }

    @Test
    public void testLeave() throws Exception {
        int slotNumber = 1;
        Mockito.when(garageService.leave(slotNumber)).thenReturn("Slot 1 is now free");

        mockMvc.perform(post("/garage/leave")
                        .param("slotNumber", String.valueOf(slotNumber))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Slot 1 is now free"));
    }

    @Test
    public void testLeaveWithInvalidSlot() throws Exception {
        Mockito.when(garageService.leave(Mockito.anyInt())).thenThrow(new InvalidSlotNumberException("Invalid slot number"));

        mockMvc.perform(post("/garage/leave")
                        .param("slotNumber", "999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid slot number"));
    }

    @Test
    public void testStatus() throws Exception {
        Mockito.when(garageService.status()).thenReturn("Status:\nSlot 1 is free.\n");

        mockMvc.perform(get("/garage/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("Status:\nSlot 1 is free.\n"));
    }
}
