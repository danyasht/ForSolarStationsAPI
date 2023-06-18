package ua.lviv.iot.spring.rest.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.spring.rest.model.Client;
import ua.lviv.iot.spring.rest.model.SolarPanel;
import ua.lviv.iot.spring.rest.model.SolarStation;
import ua.lviv.iot.spring.rest.service.SolarPanelService;
import ua.lviv.iot.spring.rest.service.SolarStationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/SolarStationController")
@AllArgsConstructor
@NoArgsConstructor
public class SolarStationController {
    @Autowired
    private SolarStationService solarStationService;
    @Autowired
    private SolarPanelService solarPanelService;

    private SolarPanel solarPanelToAdd = null;

    @PostMapping("/create")
    public ResponseEntity<SolarStation> CreateNewSolarStation(@RequestBody SolarStation solarStation) {
        SolarStation addedStation = solarStationService.createSolarStation(solarStation);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedStation);
    }

    @GetMapping("/solarStation/{id}")
    public SolarStation getSolarStationById(@PathVariable Integer id) {
        return solarStationService.getSolarStationByID(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSolarStationById(@PathVariable Integer id) {
        solarStationService.deleteSolarStationById(id);
        return ResponseEntity.ok("Solar station deleted successfully");
    }

    @GetMapping("/solarStations")
    public List<SolarStation> getAllSolarStations() {
        return solarStationService.getAllSolarStations();
    }

    @Getter
    @Setter
    private static class SolarStationUpdateRequest {
        private Integer solarPanelId;
        private int powerOfStation;
        private String addressOfInstallation;
        private HashMap<Integer, SolarPanel> panelsOfStation;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSolarStation(@PathVariable("id") Integer id, @RequestBody SolarStationController.SolarStationUpdateRequest request) {
        solarStationService.updateSolarStation(id, request.getSolarPanelId(),
                request.getPowerOfStation(), request.getAddressOfInstallation(), request.getPanelsOfStation());
        return ResponseEntity.ok("Solar Station updated successfully");
    }

    @PostMapping(path = "/load/solarPanelsInfo/{idOfStation}/{idOfSolarPanel}")
    public ResponseEntity<String> loadSolarPanelsInformationToSolarStationMapById(@PathVariable("idOfStation") Integer id,
                                                        @PathVariable("idOfSolarPanel") Integer idOfSolarPanel) {
        Map<Integer, SolarPanel> solarPanelMap = solarPanelService.getSolarPanelMap();
        try {
            solarPanelToAdd = solarPanelMap.get(idOfSolarPanel);
        } catch (ObjectNotFoundException e){
            ResponseEntity.badRequest();
        }

        solarStationService.loadSolarPanelsInformationToSolarStationMapById(id, idOfSolarPanel, solarPanelToAdd);
        return ResponseEntity.ok("Solar panels all info loaded successfully");
    }

    @PostMapping(path = "/export")
    public ResponseEntity<String> writeAllStationsToCSV(@RequestParam("folderpath") String folderpath){
        Map<Integer, SolarStation> solarStationMap = solarStationService.getSolarStationMap();
        try {
            solarStationService.writeAllStationsToCSV(solarStationMap, folderpath);
            return ResponseEntity.ok("All stations files were created");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
}
