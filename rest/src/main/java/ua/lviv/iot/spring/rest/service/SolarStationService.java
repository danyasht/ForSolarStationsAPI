package ua.lviv.iot.spring.rest.service;

import lombok.Getter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.lviv.iot.spring.rest.model.Client;
import ua.lviv.iot.spring.rest.model.SolarPanel;
import ua.lviv.iot.spring.rest.model.SolarStation;
import ua.lviv.iot.spring.rest.writer.SolarStationWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RequestMapping("/SolarStation")
@Service
@Getter
public class SolarStationService {
    private final Map<Integer, SolarStation> solarStationMap = new HashMap<>();
    private final AtomicInteger solarStationIdCounter = new AtomicInteger();
    private final SolarStationWriter solarStationWriter = new SolarStationWriter();

    public SolarStation createSolarStation(SolarStation solarStation) {
        solarStation.setId(solarStationIdCounter.incrementAndGet());
        solarStationMap.put(solarStation.getId(), solarStation);
        return solarStation;
    }

    public SolarStation getSolarStationByID(Integer ID) {
        return solarStationMap.getOrDefault(ID, null);
    }

    public void deleteSolarStationById(Integer id) {
        SolarStation solarStation = solarStationMap.get(id);
        if (solarStation != null) {
            solarStationMap.remove(id);
        } else {
            throw new IllegalArgumentException("Solar station not found with ID: " + id);
        }
    }

    public List<SolarStation> getAllSolarStations() {
        return new LinkedList<>(solarStationMap.values());
    }

    public void updateSolarStation(Integer id, Integer solarPanelId, int powerOfStation, String addressOfInstallation, HashMap solarPanelsOfSolarStation) {
        SolarStation solarStation = solarStationMap.get(id);
        if (solarStation != null) {
            solarStation.setSolarPanelId(solarPanelId);
            solarStation.setPowerOfStation(powerOfStation);
            solarStation.setAddressOfInstallation(addressOfInstallation);
            solarStation.setSolarPanelsOfSolarStation(solarPanelsOfSolarStation);
            solarStationMap.put(id, solarStation);
        } else {
            throw new IllegalArgumentException("Solar station not found with ID: " + id);
        }
    }

    public void loadSolarPanelsInformationToSolarStationMapById(Integer id, Integer idOfSolarPanelToLoad, SolarPanel solarPanel){
        SolarStation solarStation = solarStationMap.get(id);
        if(solarStation != null){
            solarStation.addPanelToMap(idOfSolarPanelToLoad, solarPanel);
            solarStationMap.put(id, solarStation);
        } else {
            throw new ObjectNotFoundException(id, "Solar panel not found with ID: " + id);
        }
    }

    public void writeAllStationsToCSV(Map<Integer, SolarStation> solarStationMap, String filePath){
        try {
            solarStationWriter.writeAllStationsToCSV(solarStationMap, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
