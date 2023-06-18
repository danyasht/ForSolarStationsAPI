package ua.lviv.iot.spring.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolarPanel {
    private Integer id;
    private String type;
    private double power;
    private double batteryCapacity;
    private double timeOfBatteryUsage;
    private double timeOfPanelUsage;

}
