package solarboyz;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {
    public static void exportToCSV(List<String[]> sortedPoints, String outputFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            for (String[] point : sortedPoints) {
                String line = String.join(",", point);
                writer.write(line);
                writer.newLine();
            }

            System.out.println("CSV export completed: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error while writing to file: " + e.getMessage());
        }
    }
}
