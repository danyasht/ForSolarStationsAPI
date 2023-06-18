package ua.lviv.iot.spring.rest.writer;

import ua.lviv.iot.spring.rest.model.Client;
import ua.lviv.iot.spring.rest.model.SolarPanel;
import ua.lviv.iot.spring.rest.model.SolarStation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SolarStationWriter {
    private static final String HEADERS_FOR_STATION = "id, power, address, panelId";
    private static final String HEADERS_FOR_PANEL = "id, type, power, batteryCapacity, timeOfBatteryUsage, timeOfPanelUsage";

    public void writeAllStationsToCSV(Map<Integer, SolarStation> stationMap, String filePath) throws IOException {
        DateTimeFormatter formatterFile = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();

        for (SolarStation solarStation : stationMap.values()) {
            String filename = solarStation.getAddressOfInstallation() + "-" + currentDate.format(formatterFile) + ".csv";
            String pathToFile = filePath + "/" + filename;
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile));

            writer.write(HEADERS_FOR_STATION);
            for (SolarPanel solarPanel : solarStation.getSolarPanelsOfSolarStation().values()) {
                String line = String.format("%d,%.2f,%s,%d",
                        solarStation.getId(), solarStation.getPowerOfStation(), solarStation.getAddressOfInstallation(),
                        solarStation.getSolarPanelId());
                writer.newLine();
                writer.write(line);
                writer.newLine();
                writer.write("Station's panels:");
                writer.newLine();
                writer.write(HEADERS_FOR_PANEL);
                writer.newLine();
                    String lineTwo = String.format("%d,%s,%.2f,%.2f,%.2f,%.2f", solarPanel.getId(), solarPanel.getType(),
                            solarPanel.getPower(), solarPanel.getBatteryCapacity(), solarPanel.getTimeOfBatteryUsage(),
                            solarPanel.getTimeOfPanelUsage());
                    writer.write(lineTwo);
                    writer.newLine();
                }
            writer.close();
        }
    }
}