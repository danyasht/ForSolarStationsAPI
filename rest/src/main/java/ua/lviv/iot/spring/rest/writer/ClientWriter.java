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

public class ClientWriter {

    private static final String HEADERS_FOR_CLIENT = "id, name, surname";
    private static final String HEADERS_FOR_STATION = "id, power, address, panelId";
    private static final String HEADERS_FOR_PANEL = "id, type, power, batteryCapacity, timeOfBatteryUsage, timeOfPanelUsage";

    public void writeAllObjectsToCSV(Map<Integer, Client> clientMap, String filePath) throws IOException {
        DateTimeFormatter formatterFile = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();

        for (Client client : clientMap.values()) {
            String filename = client.getName() + "-" + currentDate.format(formatterFile) + ".csv";
            String pathToFile = filePath + "/" + filename;
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile));

            writer.write(HEADERS_FOR_CLIENT);

            String line = String.format("%d,%s,%s", client.getId(), client.getName(),
                    client.getSurname());
            writer.newLine();
            writer.write(line);
            writer.newLine();
            writer.write("Client's stations:");
            writer.newLine();
            writer.write(HEADERS_FOR_STATION);
            for (SolarStation solarStation : client.getSolarStationsOfClient().values()) {
                String lineTwo = String.format("%d,%.2f,%s,%d",
                        solarStation.getId(), solarStation.getPowerOfStation(), solarStation.getAddressOfInstallation(),
                        solarStation.getSolarPanelId());
                writer.newLine();
                writer.write(lineTwo);
                writer.newLine();
                writer.write("Station's panels:");
                writer.newLine();
                writer.write(HEADERS_FOR_PANEL);
                writer.newLine();
                for (SolarPanel solarPanel : solarStation.getSolarPanelsOfSolarStation().values()) {
                    String lineThree = String.format("%d,%s,%.2f,%.2f,%.2f,%.2f", solarPanel.getId(), solarPanel.getType(),
                            solarPanel.getPower(), solarPanel.getBatteryCapacity(), solarPanel.getTimeOfBatteryUsage(),
                            solarPanel.getTimeOfPanelUsage());
                    writer.write(lineThree);
                    writer.newLine();
                }
            }
            writer.close();
        }
    }
}