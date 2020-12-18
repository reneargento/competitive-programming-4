package chapter1.section6.o.time.waster.problems.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 17/12/20.
 */
public class PrintingCosts {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Character, Integer> characterToTonerMap = buildCharacterToTonerMap();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int amountOfToner = computeAmountOfToner(line, characterToTonerMap);
            System.out.println(amountOfToner);
        }
    }

    private static Map<Character, Integer> buildCharacterToTonerMap() {
        Map<Character, Integer> characterToTonerMap = new HashMap<>();
        characterToTonerMap.put(' ', 0);
        characterToTonerMap.put('!', 9);
        characterToTonerMap.put('"', 6);
        characterToTonerMap.put('#', 24);
        characterToTonerMap.put('$', 29);
        characterToTonerMap.put('%', 22);
        characterToTonerMap.put('&', 24);
        characterToTonerMap.put('\'', 3);
        characterToTonerMap.put('(', 12);
        characterToTonerMap.put(')', 12);
        characterToTonerMap.put('*', 17);
        characterToTonerMap.put('+', 13);
        characterToTonerMap.put(',', 7);
        characterToTonerMap.put('-', 7);
        characterToTonerMap.put('.', 4);
        characterToTonerMap.put('/', 10);
        characterToTonerMap.put('0', 22);
        characterToTonerMap.put('1', 19);
        characterToTonerMap.put('2', 22);
        characterToTonerMap.put('3', 23);
        characterToTonerMap.put('4', 21);
        characterToTonerMap.put('5', 27);
        characterToTonerMap.put('6', 26);
        characterToTonerMap.put('7', 16);
        characterToTonerMap.put('8', 23);
        characterToTonerMap.put('9', 26);
        characterToTonerMap.put(':', 8);
        characterToTonerMap.put(';', 11);
        characterToTonerMap.put('<', 10);
        characterToTonerMap.put('=', 14);
        characterToTonerMap.put('>', 10);
        characterToTonerMap.put('?', 15);
        characterToTonerMap.put('@', 32);
        characterToTonerMap.put('A', 24);
        characterToTonerMap.put('B', 29);
        characterToTonerMap.put('C', 20);
        characterToTonerMap.put('D', 26);
        characterToTonerMap.put('E', 26);
        characterToTonerMap.put('F', 20);
        characterToTonerMap.put('G', 25);
        characterToTonerMap.put('H', 25);
        characterToTonerMap.put('I', 18);
        characterToTonerMap.put('J', 18);
        characterToTonerMap.put('K', 21);
        characterToTonerMap.put('L', 16);
        characterToTonerMap.put('M', 28);
        characterToTonerMap.put('N', 25);
        characterToTonerMap.put('O', 26);
        characterToTonerMap.put('P', 23);
        characterToTonerMap.put('Q', 31);
        characterToTonerMap.put('R', 28);
        characterToTonerMap.put('S', 25);
        characterToTonerMap.put('T', 16);
        characterToTonerMap.put('U', 23);
        characterToTonerMap.put('V', 19);
        characterToTonerMap.put('W', 26);
        characterToTonerMap.put('X', 18);
        characterToTonerMap.put('Y', 14);
        characterToTonerMap.put('Z', 22);
        characterToTonerMap.put('[', 18);
        characterToTonerMap.put('\\', 10);
        characterToTonerMap.put(']', 18);
        characterToTonerMap.put('^', 7);
        characterToTonerMap.put('_', 8);
        characterToTonerMap.put('`', 3);
        characterToTonerMap.put('a', 23);
        characterToTonerMap.put('b', 25);
        characterToTonerMap.put('c', 17);
        characterToTonerMap.put('d', 25);
        characterToTonerMap.put('e', 23);
        characterToTonerMap.put('f', 18);
        characterToTonerMap.put('g', 30);
        characterToTonerMap.put('h', 21);
        characterToTonerMap.put('i', 15);
        characterToTonerMap.put('j', 20);
        characterToTonerMap.put('k', 21);
        characterToTonerMap.put('l', 16);
        characterToTonerMap.put('m', 22);
        characterToTonerMap.put('n', 18);
        characterToTonerMap.put('o', 20);
        characterToTonerMap.put('p', 25);
        characterToTonerMap.put('q', 25);
        characterToTonerMap.put('r', 13);
        characterToTonerMap.put('s', 21);
        characterToTonerMap.put('t', 17);
        characterToTonerMap.put('u', 17);
        characterToTonerMap.put('v', 13);
        characterToTonerMap.put('w', 19);
        characterToTonerMap.put('x', 13);
        characterToTonerMap.put('y', 24);
        characterToTonerMap.put('z', 19);
        characterToTonerMap.put('{', 18);
        characterToTonerMap.put('|', 12);
        characterToTonerMap.put('}', 18);
        characterToTonerMap.put('~', 9);
        return characterToTonerMap;
    }

    private static int computeAmountOfToner(String line, Map<Character, Integer> characterToTonerMap) {
        int toner = 0;
        for (char character : line.toCharArray()) {
            toner += characterToTonerMap.get(character);
        }
        return toner;
    }
}
