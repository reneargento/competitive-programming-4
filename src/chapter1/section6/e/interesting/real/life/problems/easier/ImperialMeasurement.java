package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 02/11/20.
 */
public class ImperialMeasurement {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int value = scanner.nextInt();
        String unit1 = scanner.next();
        scanner.next();
        String unit2 = scanner.next();

        int[] multiplier = {0, 1000, 12, 3, 22, 10, 8, 3};
        Map<String, Integer> unitMap = createUnitMap();
        int startIndex = unitMap.get(unit1);
        int endIndex = unitMap.get(unit2);

        if (startIndex == endIndex) {
            System.out.println(value);
        } else {
            int increment = startIndex < endIndex ? 1 : -1;
            double convertedValue = convert(value, multiplier, startIndex, endIndex, increment);
            System.out.println(convertedValue);
        }
    }

    private static Map<String, Integer> createUnitMap() {
        Map<String, Integer> unitMap = new HashMap<>();
        unitMap.put("thou", 0);
        unitMap.put("th", 0);
        unitMap.put("inch", 1);
        unitMap.put("in", 1);
        unitMap.put("foot", 2);
        unitMap.put("ft", 2);
        unitMap.put("yard", 3);
        unitMap.put("yd", 3);
        unitMap.put("chain", 4);
        unitMap.put("ch", 4);
        unitMap.put("furlong", 5);
        unitMap.put("fur", 5);
        unitMap.put("mile", 6);
        unitMap.put("mi", 6);
        unitMap.put("league", 7);
        unitMap.put("lea", 7);
        return unitMap;
    }

    private static double convert(double value, int[] multiplier, int startIndex, int endIndex, int increment) {
        if (increment > 0) {
            startIndex++;
            endIndex++;
        }

        while (startIndex != endIndex) {
            if (increment > 0) {
                value /= multiplier[startIndex];
            } else {
                value *= multiplier[startIndex];
            }
            startIndex += increment;
        }
        return value;
    }
}
