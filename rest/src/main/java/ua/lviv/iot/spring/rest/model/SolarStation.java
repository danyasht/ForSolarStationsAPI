package ua.lviv.iot.spring.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolarStation {
    private Integer id;
    private Integer solarPanelId;
    private double powerOfStation;
    private String addressOfInstallation;
    private HashMap<Integer, SolarPanel> solarPanelsOfSolarStation = new HashMap<>();

    public void addPanelToMap(Integer idOfSolarPanelToAddToMap, SolarPanel solarPanel) {
        solarPanelsOfSolarStation.put(idOfSolarPanelToAddToMap, solarPanel);
    }
}