package ua.lviv.iot.spring.rest;

import org.junit.jupiter.api.Test;
import ua.lviv.iot.spring.rest.model.SolarPanel;
import ua.lviv.iot.spring.rest.service.SolarPanelService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolarPanelTest {
    private final SolarPanelService solarPanelService = new SolarPanelService();

    @Test
    void testCreateSolarPanel(){
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setType("Good");
        solarPanel.setPower(230);
        solarPanel.setTimeOfBatteryUsage(2);
        solarPanel.setTimeOfPanelUsage(3);
        solarPanel.setBatteryCapacity(120);

        SolarPanel createdPanel = solarPanelService.createSolarPanel(solarPanel);

        assertNotNull(createdPanel.getId());
        assertEquals(1, solarPanelService.getSolarPanelMap().size());
        assertEquals(createdPanel, solarPanelService.getSolarPanelMap().get(createdPanel.getId()));
    }

    @Test
    void testGetAllPanels() {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setType("Bad");
        solarPanel.setPower(1);
        solarPanel.setTimeOfBatteryUsage(340);
        solarPanel.setTimeOfPanelUsage(400);
        solarPanel.setBatteryCapacity(1000);
        solarPanelService.createSolarPanel(solarPanel);
        List<SolarPanel> listWithPanels = solarPanelService.getAllSolarPanels();

        assertNotNull(listWithPanels);
        assertEquals(1, listWithPanels.size());
    }

    @Test
    void testGetSolarPanelById() {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setType("The best panel");
        solarPanel.setPower(1000);
        solarPanel.setTimeOfBatteryUsage(24);
        solarPanel.setTimeOfPanelUsage(24);
        solarPanel.setBatteryCapacity(3000);
        SolarPanel createdPanel = solarPanelService.createSolarPanel(solarPanel);

        SolarPanel anotherPanel = solarPanelService.getSolarPanelById(createdPanel.getId());

        assertNotNull(anotherPanel);
        assertEquals(createdPanel, anotherPanel);
    }

    @Test
    void testDeletePanelById() {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setType("Not the best panel");
        solarPanel.setPower(740);
        solarPanel.setTimeOfBatteryUsage(37);
        solarPanel.setTimeOfPanelUsage(38);
        solarPanel.setBatteryCapacity(2200);

        SolarPanel createdPanel = solarPanelService.createSolarPanel(solarPanel);

        solarPanelService.deleteSolarPanelById(createdPanel.getId());

        SolarPanel anotherPanel = solarPanelService.getSolarPanelById(createdPanel.getId());

        assertNull(anotherPanel);
        assertEquals(0, solarPanelService.getSolarPanelMap().size());
    }

    @Test
    void testUpdatePanel() {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setType("Great panel");
        solarPanel.setPower(660);
        solarPanel.setTimeOfBatteryUsage(45);
        solarPanel.setTimeOfPanelUsage(12);
        solarPanel.setBatteryCapacity(2500);

        SolarPanel createdPanel = solarPanelService.createSolarPanel(solarPanel);

        String newType = "Way better panel";
        int newPowerOfPanel = 700;
        int newTimeOfBatteryUsage = 45;
        int newTimeOfPanelUsage = 13;
        int newBatteryCapacity = 2800;

        solarPanelService.updateSolarPanel(createdPanel.getId(), newType, newPowerOfPanel, newBatteryCapacity, newTimeOfBatteryUsage, newTimeOfPanelUsage);

        SolarPanel updatedPanel = solarPanelService.getSolarPanelById(createdPanel.getId());

        assertNotNull(updatedPanel);
        assertEquals(newType, updatedPanel.getType());
        assertEquals(newPowerOfPanel, updatedPanel.getPower());
        assertEquals(newBatteryCapacity, updatedPanel.getBatteryCapacity());
        assertEquals(newTimeOfBatteryUsage, updatedPanel.getTimeOfBatteryUsage());
        assertEquals(newTimeOfPanelUsage, updatedPanel.getTimeOfPanelUsage());
    }
}
