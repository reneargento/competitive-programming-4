package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 31/10/20.
 */
public class SMSTyping {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();
        Map<Character, Integer> pressesMap = createPressesMap();

        for (int t = 1; t <= tests; t++) {
            String message = scanner.nextLine();
            int presses = 0;

            for (int i = 0; i < message.length(); i++) {
                presses += pressesMap.get(message.charAt(i));
            }
            System.out.printf("Case #%d: %d\n", t, presses);
        }
    }

    private static Map<Character, Integer> createPressesMap() {
        Map<Character, Integer> pressesMap = new HashMap<>();
        char character = 'a';
        int presses = 1;

        for (int i = 0; i < 24; i++) {
            pressesMap.put(character, presses);
            character++;

            if (character == 's' || character == 'z') {
                pressesMap.put(character, 4);
                character++;
                presses = 0;
            }

            presses++;
            if (presses == 4) {
                presses = 1;
            }
        }
        pressesMap.put(' ', 1);
        return pressesMap;
    }

}
