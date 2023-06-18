package ua.lviv.iot.spring.rest.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.lviv.iot.spring.rest.model.Client;
import ua.lviv.iot.spring.rest.model.SolarPanel;
import ua.lviv.iot.spring.rest.writer.SolarPanelWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RequestMapping("/SolarPanel")
@Service
@Getter
public class SolarPanelService {
    private final Map<Integer, SolarPanel> solarPanelMap = new HashMap<>();
    private final AtomicInteger solarPanelIdCounter = new AtomicInteger();
    private final SolarPanelWriter solarPanelWriter = new SolarPanelWriter();


    public SolarPanel createSolarPanel(SolarPanel solarPanel) {
        solarPanel.setId(solarPanelIdCounter.incrementAndGet());
        solarPanelMap.put(solarPanel.getId(), solarPanel);
        return solarPanel;
    }

    public SolarPanel getSolarPanelById(Integer id) {
        return solarPanelMap.getOrDefault(id, null);
    }

    public void deleteSolarPanelById(Integer id) {
        SolarPanel solarPanel = solarPanelMap.get(id);
        if (solarPanel != null) {
            solarPanelMap.remove(id);
        } else {
            throw new IllegalArgumentException("Solar panel not found with ID: " + id);
        }
    }

    public List<SolarPanel> getAllSolarPanels() {
        return new LinkedList<>(solarPanelMap.values());
    }

    public void updateSolarPanel(Integer id, String type, int power, int batteryCapacity, int timeOfBatteryUsage, int timeOfPanelUsage) {
        SolarPanel solarPanel = solarPanelMap.get(id);
        if (solarPanel != null) {
            solarPanel.setType(type);
            solarPanel.setPower(power);
            solarPanel.setBatteryCapacity(batteryCapacity);
            solarPanel.setTimeOfBatteryUsage(timeOfBatteryUsage);
            solarPanel.setTimeOfPanelUsage(timeOfPanelUsage);
            solarPanelMap.put(id, solarPanel);
        } else {
            throw new IllegalArgumentException("Solar panel not found with ID: " + id);
        }
    }

    public void writeAllPanelsToCSV(Map<Integer, SolarPanel> solarPanelMap, String filePath){
        try {
            solarPanelWriter.writeAllPanelsToCSV(solarPanelMap, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
