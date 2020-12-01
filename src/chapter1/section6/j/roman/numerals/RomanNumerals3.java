package chapter1.section6.j.roman.numerals;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 23/11/20.
 */
// UVA-12397
public class RomanNumerals3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, String> romanToDecimalMap = createRomanToDecimalMap();
        Map<Character, Integer> numeralToMatchesMap = createNumeralToMatchesMap();

        while (scanner.hasNext()) {
            int number = scanner.nextInt();
            int matches = countMatches(romanToDecimalMap.get(number), numeralToMatchesMap);
            System.out.println(matches);
        }
    }

    private static int countMatches(String romanNumeral, Map<Character, Integer> numeralToMatchesMap) {
        int matches = 0;
        for (char symbol : romanNumeral.toCharArray()) {
            matches += numeralToMatchesMap.get(symbol);
        }
        return matches;
    }

    private static Map<Integer, String> createRomanToDecimalMap() {
        int[] romanValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanDecimals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        Map<Integer, String> romanToDecimalMap = new HashMap<>();

        for (int number = 1; number <= 3999; number++) {
            int value = number;
            StringBuilder romanNumber = new StringBuilder();

            for (int i = 0; i < romanValues.length; i++) {
                while (value >= romanValues[i]) {
                    romanNumber.append(romanDecimals[i]);
                    value -= romanValues[i];
                }
            }
            romanToDecimalMap.put(number, romanNumber.toString());
        }
        return romanToDecimalMap;
    }

    private static Map<Character, Integer> createNumeralToMatchesMap() {
        Map<Character, Integer> numeralToMatchMap = new HashMap<>();
        numeralToMatchMap.put('I', 1);
        numeralToMatchMap.put('V', 2);
        numeralToMatchMap.put('X', 2);
        numeralToMatchMap.put('L', 2);
        numeralToMatchMap.put('C', 2);
        numeralToMatchMap.put('D', 3);
        numeralToMatchMap.put('M', 4);
        return numeralToMatchMap;
    }
}
