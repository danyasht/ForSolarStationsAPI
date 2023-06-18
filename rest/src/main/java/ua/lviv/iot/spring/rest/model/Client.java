package ua.lviv.iot.spring.rest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Client {
    private Integer id;
    private String name;
    private String surname;
    private HashMap<Integer, SolarStation> solarStationsOfClient = new HashMap<>();

    public void addStationToMap(Integer idOfStationToAdd, SolarStation solarStation) {
        solarStationsOfClient.put(idOfStationToAdd, solarStation);
    }
}
