package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 26/01/21.
 */
public class MasteringMastermind {

    private static final char MATCHED_CHAR = '*';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextInt();
        char[] code = scanner.next().toCharArray();
        char[] guess = scanner.next().toCharArray();

        int matches = countMatches(code, guess);
        int almostMatches = countAlmostMatches(code, guess);
        System.out.printf("%d %d\n", matches, almostMatches);
    }

    private static int countMatches(char[] code, char[] guess) {
        int matches = 0;

        for (int i = 0; i < code.length; i++) {
            if (code[i] == guess[i]) {
                matches++;
                code[i] = MATCHED_CHAR;
                guess[i] = MATCHED_CHAR;
            }
        }
        return matches;
    }

    private static int countAlmostMatches(char[] code, char[] guess) {
        Map<Character, Integer> codeSymbols = new HashMap<>();
        Map<Character, Integer> guessSymbols = new HashMap<>();

        for (char symbol : code) {
            updateMap(symbol, codeSymbols);
        }
        for (char symbol : guess) {
            updateMap(symbol, guessSymbols);
        }

        int almostMatches = 0;

        for (char symbol : codeSymbols.keySet()) {
            int codeFrequency = codeSymbols.get(symbol);
            int guessFrequency = guessSymbols.getOrDefault(symbol, 0);
            almostMatches += Math.min(codeFrequency, guessFrequency);
        }
        return almostMatches;
    }

    private static void updateMap(char symbol, Map<Character, Integer> mapSymbols) {
        if (symbol == MATCHED_CHAR) {
            return;
        }
        int frequency = mapSymbols.getOrDefault(symbol, 0);
        mapSymbols.put(symbol, frequency + 1);
    }
}
