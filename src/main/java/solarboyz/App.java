package solarboyz;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public final class App {

    private App() {
    }
    public static void main( String[] args )
    {

        List<String[]> info = readFile("/Users/olaf/Downloads/23-44-export.txt");
        List<String[]> sortedPoints = sortPointsByProximity(info);

        List<String[]> renumberedPoints = PointRenumberer.renumberPoints(sortedPoints);

        String outputFilePath = "/Users/olaf/Downloads/sorted.csv";
        CSVExporter.exportToCSV(renumberedPoints, outputFilePath);

    }

    public static  List<String[]> readFile(String file){

        List<String[]> nestedArray = new ArrayList<>();

        try {
            Reader reader = new FileReader(file);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        
            for (CSVRecord csvRecord : csvParser) {
                String[] row = {
                    csvRecord.get(0),
                    csvRecord.get(1),
                    csvRecord.get(2),
                    csvRecord.get(3),
                    csvRecord.get(4),
                };

                nestedArray.add(row);
            }
            
            csvParser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nestedArray;
    }

    public static List<String[]> sortPointsByProximity(List<String[]> values){        
        List<String[]> sortedPoints = new ArrayList<>();
        sortedPoints.add(values.get(0));
        values.remove(0);

        String[] currentPoint = sortedPoints.get(0);

        while (!values.isEmpty()) {
            String[] nextClosestPoint = findClosestPoint(currentPoint, values);
            sortedPoints.add(nextClosestPoint);
            values.remove(nextClosestPoint);
            currentPoint = nextClosestPoint;
        }

        return sortedPoints;
    }

    private static String[] findClosestPoint(String[] currentPoint, List<String[]> points){
        String[] closestPoint = null;
        double minDistance = Double.MAX_VALUE;
        
        double currentNorthing = Double.parseDouble(currentPoint[1]);
        double currentEasting = Double.parseDouble(currentPoint[2]);

        for (String[] point : points) {
            double northing = Double.parseDouble(point[1]);
            double easting = Double.parseDouble(point[2]);
            double distance = calculateDistance(currentNorthing, currentEasting, northing, easting);

            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = point;
            }
        }

        return closestPoint;
    }

    private static double calculateDistance(double northing1, double easting1, double northing2, double easting2) {
        return Math.sqrt(Math.pow(northing2 - northing1, 2) + Math.pow(easting2 - easting1, 2));
    }
}
