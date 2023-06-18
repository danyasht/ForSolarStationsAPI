package ua.lviv.iot.spring.rest;

import org.junit.jupiter.api.Test;
import ua.lviv.iot.spring.rest.model.SolarStation;
import ua.lviv.iot.spring.rest.service.SolarStationService;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SolarStationTest {
    private final SolarStationService solarStationService = new SolarStationService();

    @Test
    void testCreateNewSolarStation(){
        SolarStation solarStation = new SolarStation();
        solarStation.setSolarPanelId(1);
        solarStation.setPowerOfStation(234.00);
        solarStation.setAddressOfInstallation("Lviv");

        SolarStation createdStation = solarStationService.createSolarStation(solarStation);

        assertNotNull(createdStation.getId());
        assertEquals(1, solarStationService.getSolarStationMap().size());
        assertEquals(createdStation, solarStationService.getSolarStationMap().get(createdStation.getId()));
    }

    @Test
    void testGetAllStations() {
        SolarStation solarStation = new SolarStation();
        solarStation.setSolarPanelId(1);
        solarStation.setPowerOfStation(234.00);
        solarStation.setAddressOfInstallation("Lviv");
        solarStationService.createSolarStation(solarStation);
        List<SolarStation> listWithStations = solarStationService.getAllSolarStations();

        assertNotNull(listWithStations);
        assertEquals(1, listWithStations.size());
    }

    @Test
    void testGetStationById() {
        SolarStation solarStation = new SolarStation();
        solarStation.setSolarPanelId(1);
        solarStation.setPowerOfStation(234.00);
        solarStation.setAddressOfInstallation("Lviv");
        SolarStation createdStation = solarStationService.createSolarStation(solarStation);

        SolarStation anotherStation = solarStationService.getSolarStationByID(createdStation.getId());

        assertNotNull(anotherStation);
        assertEquals(createdStation, anotherStation);
    }

    @Test
    void testDeleteStationById() {
        SolarStation solarStation = new SolarStation();
        solarStation.setSolarPanelId(1);
        solarStation.setPowerOfStation(234.00);
        solarStation.setAddressOfInstallation("Lviv");

        SolarStation createdStation = solarStationService.createSolarStation(solarStation);

        solarStationService.deleteSolarStationById(createdStation.getId());

        SolarStation anotherStation = solarStationService.getSolarStationByID(createdStation.getId());

        assertNull(anotherStation);
        assertEquals(0, solarStationService.getSolarStationMap().size());
    }

    @Test
    void testUpdateStation() {
        SolarStation solarStation = new SolarStation();
        solarStation.setSolarPanelId(1);
        solarStation.setPowerOfStation(234.00);
        solarStation.setAddressOfInstallation("Lviv");

        SolarStation createdStation = solarStationService.createSolarStation(solarStation);

        int newSolarPanelId = 1;
        int newPowerOfStation = 275;
        String newAddressOfInstallation = "Ternopil";
        solarStationService.updateSolarStation(createdStation.getId(), newSolarPanelId, newPowerOfStation, newAddressOfInstallation, new HashMap<>());

        SolarStation updatedStation = solarStationService.getSolarStationByID(createdStation.getId());

        assertNotNull(updatedStation);
        assertEquals(newSolarPanelId, updatedStation.getSolarPanelId());
        assertEquals(newPowerOfStation, updatedStation.getPowerOfStation());
        assertEquals(newAddressOfInstallation, updatedStation.getAddressOfInstallation());
    }
}
