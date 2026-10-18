package chapter6.section7.palindrome.checking;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 16/10/2026.
 */
public class Palindromes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<Character, Character> reverseMap = buildReverseMap();
        String string = FastReader.getLine();
        while (string != null) {
            String result = evaluateString(reverseMap, string);
            outputWriter.printLine(result + "\n");
            string = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String evaluateString(Map<Character, Character> reverseMap, String string) {
        boolean isPalindrome = isPalindrome(string);
        boolean isMirror = isMirror(reverseMap, string);

        String result = string;
        if (!isPalindrome && !isMirror) {
            result += " -- is not a palindrome.";
        } else if (isPalindrome && !isMirror) {
            result += " -- is a regular palindrome.";
        } else if (!isPalindrome && isMirror) {
            result += " -- is a mirrored string.";
        } else {
            result += " -- is a mirrored palindrome.";
        }
        return result;
    }

    private static Map<Character, Character> buildReverseMap() {
        Map<Character, Character> reverseMap = new HashMap<>();
        reverseMap.put('A', 'A');
        reverseMap.put('E', '3');
        reverseMap.put('H', 'H');
        reverseMap.put('I', 'I');
        reverseMap.put('J', 'L');
        reverseMap.put('L', 'J');
        reverseMap.put('M', 'M');
        reverseMap.put('O', 'O');
        reverseMap.put('S', '2');
        reverseMap.put('T', 'T');
        reverseMap.put('U', 'U');
        reverseMap.put('V', 'V');
        reverseMap.put('W', 'W');
        reverseMap.put('X', 'X');
        reverseMap.put('Y', 'Y');
        reverseMap.put('Z', '5');
        reverseMap.put('1', '1');
        reverseMap.put('2', 'S');
        reverseMap.put('3', 'E');
        reverseMap.put('5', 'Z');
        reverseMap.put('8', '8');
        return reverseMap;
    }

    private static boolean isPalindrome(String string) {
        for (int i = 0; i < string.length() / 2; i++) {
            int endIndex = string.length() - i - 1;
            if (string.charAt(i) != string.charAt(endIndex)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isMirror(Map<Character, Character> reverseMap, String string) {
        StringBuilder mirrorString = new StringBuilder();

        for (char character : string.toCharArray()) {
            if (!reverseMap.containsKey(character)) {
                return false;
            }
            char reverseCharacter = reverseMap.get(character);
            mirrorString.append(reverseCharacter);
        }
        return mirrorString.reverse().toString().equals(string);
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}