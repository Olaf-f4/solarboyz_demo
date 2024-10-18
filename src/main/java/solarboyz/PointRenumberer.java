package solarboyz;

import java.util.List;

public class PointRenumberer {
    public static List<String[]> renumberPoints(List<String[]> sortedPoints) {
        for (int i = 0; i < sortedPoints.size(); i++) {
            // Update the first element (point number) to be the new index (starting from 1)
            sortedPoints.get(i)[0] = String.valueOf(i + 1);
        }
        return sortedPoints;
    } 
}
