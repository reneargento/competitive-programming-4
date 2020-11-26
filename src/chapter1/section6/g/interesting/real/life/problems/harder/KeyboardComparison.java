package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 08/11/20.
 */
public class KeyboardComparison {

    private static class Key {
        double row;
        double column;

        public Key(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[][] qwertyKeyboard = getQwertyKeyboard();
        char[][] dvorakKeyboard = getDvorakKeyboard();
        Map<Character, Key> qwertyKeyboardMap = getKeyboardMap(qwertyKeyboard);
        Map<Character, Key> dvorakKeyboardMap = getKeyboardMap(dvorakKeyboard);

        Map<Character, Double> twoHandsQwertyDistances = getDistancesFromTwoHandsQwerty(qwertyKeyboardMap);
        Map<Character, Double> oneHandQwertyDistances = getDistancesFromOneHandQwerty(qwertyKeyboardMap);
        Map<Character, Double> dvorakDistances = getDistancesFromDvorak(dvorakKeyboardMap);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            line = line.toLowerCase();
            double twoHandsQwertyDistance = 0;
            double oneHandQwertyDistance = 0;
            double dvorakDistance = 0;

            for (char character : line.toCharArray()) {
                if (!twoHandsQwertyDistances.containsKey(character)) {
                    continue;
                }
                twoHandsQwertyDistance += twoHandsQwertyDistances.get(character);
                oneHandQwertyDistance += oneHandQwertyDistances.get(character);
                dvorakDistance += dvorakDistances.get(character);
            }
            System.out.printf("%.2f %.2f %.2f\n", twoHandsQwertyDistance, oneHandQwertyDistance, dvorakDistance);
        }
    }

    private static char[][] getQwertyKeyboard() {
        return new char[][]{
                "`1234567890-= ".toCharArray(),
                " qwertyuiop[]\\".toCharArray(),
                " asdfghjkl;'  ".toCharArray(),
                " zxcvbnm,./   ".toCharArray()
        };
    }

    private static char[][] getDvorakKeyboard() {
        return new char[][]{
                "`123qjlmfp/[] ".toCharArray(),
                " 456.orsuyb;=\\".toCharArray(),
                " 789aehtdck-  ".toCharArray(),
                " 0zx,inwvg'   ".toCharArray()
        };
    }

    private static Map<Character, Key> getKeyboardMap(char[][] keyValues) {
        Map<Character, Key> keyboardMap = new HashMap<>();

        for (int row = 0; row < keyValues.length; row++) {
            for (int column = 0; column < keyValues[0].length; column++) {
                char character = keyValues[row][column];
                if (character != ' ') {
                    keyboardMap.put(character, new Key(row, column));
                }
            }
        }
        addExtraKeys(keyboardMap);
        return keyboardMap;
    }

    private static void addExtraKeys(Map<Character, Key> keyboardMap) {
        keyboardMap.put('~', keyboardMap.get('`'));
        keyboardMap.put('!', keyboardMap.get('1'));
        keyboardMap.put('@', keyboardMap.get('2'));
        keyboardMap.put('#', keyboardMap.get('3'));
        keyboardMap.put('$', keyboardMap.get('4'));
        keyboardMap.put('%', keyboardMap.get('5'));
        keyboardMap.put('^', keyboardMap.get('6'));
        keyboardMap.put('&', keyboardMap.get('7'));
        keyboardMap.put('*', keyboardMap.get('8'));
        keyboardMap.put('(', keyboardMap.get('9'));
        keyboardMap.put(')', keyboardMap.get('0'));
        keyboardMap.put('_', keyboardMap.get('-'));
        keyboardMap.put('+', keyboardMap.get('='));
        keyboardMap.put('{', keyboardMap.get('['));
        keyboardMap.put('}', keyboardMap.get(']'));
        keyboardMap.put('|', keyboardMap.get('\\'));
        keyboardMap.put(':', keyboardMap.get(';'));
        keyboardMap.put('"', keyboardMap.get('\''));
        keyboardMap.put('<', keyboardMap.get(','));
        keyboardMap.put('>', keyboardMap.get('.'));
        keyboardMap.put('?', keyboardMap.get('/'));
    }

    private static Map<Character, Double> getDistancesFromTwoHandsQwerty(Map<Character, Key> qwertyKeyboardMap) {
        Key[] homeKeys = {
                qwertyKeyboardMap.get('a'), qwertyKeyboardMap.get('s'), qwertyKeyboardMap.get('d'),
                qwertyKeyboardMap.get('f'), qwertyKeyboardMap.get('j'), qwertyKeyboardMap.get('k'),
                qwertyKeyboardMap.get('l'), qwertyKeyboardMap.get(';')
        };
        return getDistances(qwertyKeyboardMap, homeKeys);
    }

    private static Map<Character, Double> getDistancesFromOneHandQwerty(Map<Character, Key> qwertyKeyboardMap) {
        Key[] homeKeys = {
                qwertyKeyboardMap.get('f'), qwertyKeyboardMap.get('g'), qwertyKeyboardMap.get('h'),
                qwertyKeyboardMap.get('j')
        };
        return getDistances(qwertyKeyboardMap, homeKeys);
    }

    private static Map<Character, Double> getDistancesFromDvorak(Map<Character, Key> dvorakKeyboardMap) {
        Key[] homeKeys = {
                dvorakKeyboardMap.get('e'), dvorakKeyboardMap.get('h'), dvorakKeyboardMap.get('t'),
                dvorakKeyboardMap.get('d')
        };
        return getDistances(dvorakKeyboardMap, homeKeys);
    }

    private static Map<Character, Double> getDistances(Map<Character, Key> keyboardMap, Key[] homeKeys) {
        Map<Character, Double> distances = new HashMap<>();

        for (Character keyValue : keyboardMap.keySet()) {
            Key key = keyboardMap.get(keyValue);

            double minDistance = Double.MAX_VALUE;
            for (Key homeKey : homeKeys) {
                double distance = distanceBetweenKeys(key, homeKey);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
            distances.put(keyValue, minDistance * 2);
        }
        return distances;
    }

    private static double distanceBetweenKeys(Key key1, Key key2) {
        return Math.sqrt(Math.pow(key1.row - key2.row, 2) + Math.pow(key1.column - key2.column, 2));
    }
}
