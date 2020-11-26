package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.*;

/**
 * Created by Rene Argento on 09/11/20.
 */
public class TouchscreenKeyboard {

    private static class Key {
        int row;
        int column;

        public Key(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class Entry implements Comparable<Entry> {
        String word;
        int distance;

        public Entry(String word, int distance) {
            this.word = word;
            this.distance = distance;
        }

        @Override
        public int compareTo(Entry other) {
            if (distance != other.distance) {
                return distance - other.distance;
            }
            return word.compareTo(other.word);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        char[][] keyboard = getKeyboard();
        Map<Character, Key> keyboardMap = getKeyboardMap(keyboard);

        for (int t = 0; t < tests; t++) {
            String word = scanner.next();
            int entries = scanner.nextInt();
            List<Entry> entryList = new ArrayList<>();

            for (int i = 0; i < entries; i++) {
                String entryWord = scanner.next();
                int distance = distanceBetweenWords(word, entryWord, keyboardMap);
                entryList.add(new Entry(entryWord, distance));
            }

            Collections.sort(entryList);

            for (Entry entryWord : entryList) {
                System.out.printf("%s %d\n", entryWord.word, entryWord.distance);
            }
        }
    }

    private static char[][] getKeyboard() {
        return new char[][]{
                "qwertyuiop ".toCharArray(),
                "asdfghjkl".toCharArray(),
                "zxcvbnm".toCharArray()
        };
    }

    private static Map<Character, Key> getKeyboardMap(char[][] keyValues) {
        Map<Character, Key> keyboardMap = new HashMap<>();

        for (int row = 0; row < keyValues.length; row++) {
            for (int column = 0; column < keyValues[row].length; column++) {
                char character = keyValues[row][column];
                keyboardMap.put(character, new Key(row, column));
            }
        }
        return keyboardMap;
    }

    private static int distanceBetweenWords(String word1, String word2, Map<Character, Key> keyboardMap) {
        int distance = 0;

        for (int i = 0; i < word1.length(); i++) {
            char character1 = word1.charAt(i);
            char character2 = word2.charAt(i);
            Key key1 = keyboardMap.get(character1);
            Key key2 = keyboardMap.get(character2);
            distance += distanceBetweenKeys(key1, key2);
        }
        return distance;
    }

    private static double distanceBetweenKeys(Key key1, Key key2) {
        return Math.abs(key1.row - key2.row) + Math.abs(key1.column - key2.column);
    }
}
