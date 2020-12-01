package chapter1.section6.j.roman.numerals;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 23/11/20.
 */
public class TheReturnOfTheRomanEmpire {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> romanToDecimalMap = createRomanToDecimalMap();

        while (scanner.hasNextLine()) {
            String romanNumerals = scanner.nextLine();
            if (romanToDecimalMap.containsKey(romanNumerals)) {
                System.out.println(romanToDecimalMap.get(romanNumerals));
            } else {
                System.out.println("This is not a valid number");
            }
        }
    }

    private static Map<String, Integer> createRomanToDecimalMap() {
        int[] romanValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanDecimals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        Map<String, Integer> romanToDecimalMap = new HashMap<>();
        romanToDecimalMap.put("", 0);

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
        }
        return romanToDecimalMap;
    }
}
