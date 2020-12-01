package chapter1.section6.j.roman.numerals;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 23/11/20.
 */
public class RomanDigititis {

    private static class RomanCharacters {
        int i;
        int v;
        int x;
        int l;
        int c;

        public RomanCharacters(int i, int v, int x, int l, int c) {
            this.i = i;
            this.v = v;
            this.x = x;
            this.l = l;
            this.c = c;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, RomanCharacters> romanCharactersMap = createRomanCharactersMap();

        int number = scanner.nextInt();

        while (number != 0) {
            RomanCharacters romanCharacters = romanCharactersMap.get(number);
            System.out.printf("%d: %d i, %d v, %d x, %d l, %d c\n", number, romanCharacters.i, romanCharacters.v,
                    romanCharacters.x, romanCharacters.l, romanCharacters.c);

            number = scanner.nextInt();
        }
    }

    private static Map<Integer, RomanCharacters> createRomanCharactersMap() {
        int[] romanValues = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanDecimals = {"c", "xc", "l", "xl", "x", "ix", "v", "iv", "i"};
        int[] characterCounts = new int[5];

        Map<Integer, RomanCharacters> romanCharactersMap = new HashMap<>();

        for (int number = 1; number <= 100; number++) {
            int value = number;
            StringBuilder romanNumber = new StringBuilder();

            for (int i = 0; i < romanValues.length; i++) {
                while (value >= romanValues[i]) {
                    romanNumber.append(romanDecimals[i]);
                    value -= romanValues[i];
                }
            }
            RomanCharacters romanCharacters = getTotalRomanCharacters(romanNumber.toString(), characterCounts);
            romanCharactersMap.put(number, romanCharacters);
        }
        return romanCharactersMap;
    }

    private static RomanCharacters getTotalRomanCharacters(String romanNumeral, int[] characterCounts) {
        for (char symbol : romanNumeral.toCharArray()) {
            switch (symbol) {
                case 'i': characterCounts[0]++; break;
                case 'v': characterCounts[1]++; break;
                case 'x': characterCounts[2]++; break;
                case 'l': characterCounts[3]++; break;
                case 'c': characterCounts[4]++; break;
            }
        }
        return new RomanCharacters(characterCounts[0], characterCounts[1], characterCounts[2], characterCounts[3],
                characterCounts[4]);
    }
}
