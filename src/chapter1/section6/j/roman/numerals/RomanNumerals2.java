package chapter1.section6.j.roman.numerals;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 23/11/20.
 */
// UVA-11616
public class RomanNumerals2 {

    private static class RomanDecimalMapper {
        Map<String, Integer> romanToDecimalMap;
        Map<Integer, String> decimalToRomanMap;

        public RomanDecimalMapper(Map<String, Integer> romanToDecimalMap, Map<Integer, String> decimalToRomanMap) {
            this.romanToDecimalMap = romanToDecimalMap;
            this.decimalToRomanMap = decimalToRomanMap;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RomanDecimalMapper romanDecimalMapper = createRomanDecimalMapper();

        while (scanner.hasNext()) {
            String value = scanner.next();
            if (Character.isDigit(value.charAt(0))) {
                int decimalValue = Integer.parseInt(value);
                System.out.println(romanDecimalMapper.decimalToRomanMap.get(decimalValue));
            } else {
                System.out.println(romanDecimalMapper.romanToDecimalMap.get(value));
            }
        }
    }

    private static RomanDecimalMapper createRomanDecimalMapper() {
        int[] romanValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanDecimals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        Map<String, Integer> romanToDecimalMap = new HashMap<>();
        Map<Integer, String> decimalToRomanMap = new HashMap<>();

        for (int number = 1; number <= 3999; number++) {
            int value = number;
            StringBuilder romanNumber = new StringBuilder();

            for (int i = 0; i < romanValues.length; i++) {
                while (value >= romanValues[i]) {
                    romanNumber.append(romanDecimals[i]);
                    value -= romanValues[i];
                }
            }
            romanToDecimalMap.put(romanNumber.toString(), number);
            decimalToRomanMap.put(number, romanNumber.toString());
        }
        return new RomanDecimalMapper(romanToDecimalMap, decimalToRomanMap);
    }
}
