package chapter1.section6.j.roman.numerals;

import java.util.*;

/**
 * Created by Rene Argento on 24/11/20.
 */
public class RomanHolidays {

    private static final long INDEXES_UNTIL_M = 946;
    private static final long INDEXES_AFTER_M = -54;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        Map<String, Integer> romanToDecimalMap = generateRomanToDecimalMap();
        List<String> romanNumerals = new ArrayList<>(romanToDecimalMap.keySet());
        Collections.sort(romanNumerals);

        Map<Long, Integer> indexMap = getIndexMap(romanToDecimalMap, romanNumerals);

        for (int t = 0; t < tests; t++) {
            long number = scanner.nextLong();
            long index = 0;

            if (number % 1000 == 0) {
                index = INDEXES_UNTIL_M * (number / 1000);
            } else {
                long multiplesOf1000 = number / 1000;
                long remainingValue = number % 1000;

                if (!isLocatedInTheEnd(remainingValue)) {
                    index += multiplesOf1000 * INDEXES_UNTIL_M;
                    index += indexMap.get(remainingValue);
                } else {
                    index += multiplesOf1000 * INDEXES_AFTER_M;
                    index += (indexMap.get(remainingValue) - 1001);
                }
            }
            System.out.println(index);
        }
    }

    private static boolean isLocatedInTheEnd(long number) {
        return (5 <= number && number <= 8) ||
                (10 <= number && number <= 49) ||
                (90 <= number && number <= 99);
    }

    private static Map<String, Integer> generateRomanToDecimalMap() {
        Map<String, Integer> romanToDecimalMap = new HashMap<>();

        int[] romanValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanDecimals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int number = 1; number <= 1000; number++) {
            int value = number;
            StringBuilder romanNumeral = new StringBuilder();

            for (int i = 0; i < romanValues.length; i++) {
                while (value >= romanValues[i]) {
                    romanNumeral.append(romanDecimals[i]);
                    value -= romanValues[i];
                }
            }
            romanToDecimalMap.put(romanNumeral.toString(), number);
        }
        return romanToDecimalMap;
    }

    private static Map<Long, Integer> getIndexMap(Map<String, Integer> romanToDecimalMap, List<String> romanNumerals) {
        Map<Long, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < romanNumerals.size(); i++) {
            String romanNumeral = romanNumerals.get(i);
            long decimalValue = romanToDecimalMap.get(romanNumeral);
            indexMap.put(decimalValue, i + 1);
        }
        return indexMap;
    }
}
