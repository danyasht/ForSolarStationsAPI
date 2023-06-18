package ua.lviv.iot.spring.rest.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.spring.rest.model.Client;
import ua.lviv.iot.spring.rest.model.SolarPanel;
import ua.lviv.iot.spring.rest.service.SolarPanelService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/SolarPanelController")
@AllArgsConstructor
@NoArgsConstructor
public class SolarPanelController {
    @Autowired
    private SolarPanelService solarPanelService;

    @PostMapping("/create")
    public ResponseEntity<SolarPanel> CreateNewSolarPanel(@RequestBody SolarPanel solarPanel) {
        SolarPanel addedPanel = solarPanelService.createSolarPanel(solarPanel);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedPanel);
    }

    @GetMapping("/get/{id}")
    public SolarPanel getSolarPanelById(@PathVariable Integer id) {
        return solarPanelService.getSolarPanelById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSolarPanelById(@PathVariable Integer id) {
        solarPanelService.deleteSolarPanelById(id);
        return ResponseEntity.ok("Solar panel deleted successfully");
    }

    @GetMapping("/solarPanels")
    public List<SolarPanel> getAllSolarPanels() {
        return solarPanelService.getAllSolarPanels();
    }

    @Getter
    @Setter
    private static class SolarPanelUpdateRequest {
        private String type;
        private int power;
        private int batteryCapacity;
        private int timeOfBatteryUsage;
        private int timeOfPanelUsage;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSolarPanel(@PathVariable("id") Integer id, @RequestBody SolarPanelController.SolarPanelUpdateRequest request) {
        solarPanelService.updateSolarPanel(id, request.getType(), request.getPower(), request.getBatteryCapacity(), request.getTimeOfBatteryUsage(), request.getTimeOfPanelUsage());
        return ResponseEntity.ok("Solar Panel updated successfully");
    }

    @PostMapping(path = "/export")
    public ResponseEntity<String> writeAllPanelsToCSV(@RequestParam("folderpath") String folderpath){
        Map<Integer, SolarPanel> panelMap = solarPanelService.getSolarPanelMap();
        try {
            solarPanelService.writeAllPanelsToCSV(panelMap, folderpath);
            return ResponseEntity.ok("All panels files were created");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
}
