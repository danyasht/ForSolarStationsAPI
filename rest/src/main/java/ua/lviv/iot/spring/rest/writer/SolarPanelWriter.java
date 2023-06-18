package ua.lviv.iot.spring.rest.writer;

import ua.lviv.iot.spring.rest.model.SolarPanel;
import ua.lviv.iot.spring.rest.model.SolarStation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SolarPanelWriter {
    private static final String HEADERS_FOR_PANEL = "id, type, power, batteryCapacity, timeOfBatteryUsage, timeOfPanelUsage";

    public void writeAllPanelsToCSV(Map<Integer, SolarPanel> panelMap, String filePath) throws IOException {
        DateTimeFormatter formatterFile = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();

        for (SolarPanel solarPanel : panelMap.values()) {
            String filename = solarPanel.getType() + "-" + currentDate.format(formatterFile) + ".csv";
            String pathToFile = filePath + "/" + filename;
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile));

            writer.write(HEADERS_FOR_PANEL);
            String line = String.format("%d,%s,%.2f,%.2f,%.2f",
                    solarPanel.getId(), solarPanel.getType(), solarPanel.getPower(),
                    solarPanel.getTimeOfPanelUsage(), solarPanel.getTimeOfBatteryUsage());
            writer.newLine();
            writer.write(line);
            writer.newLine();
            writer.close();
        }
    }
}