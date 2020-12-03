package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 01/12/20.
 */
public class CompressionII {

    private static class BurrowsWheelerTransform {
        String[] strings;
        String transformedString;
        int indexOfString1;

        BurrowsWheelerTransform(String string) {
            generateStrings(string);
        }

        private void generateStrings(String string) {
            int length = string.length();
            strings = new String[length];
            String string1 = null;

            for (int i = 0; i < length; i++) {
                strings[i] = string;

                if (i == 1) {
                    string1 = string;
                }

                string = string.substring(1) + string.charAt(0);
            }

            Arrays.sort(strings);
            getTransformedString();
            indexOfString1 = Arrays.binarySearch(strings, string1);
        }

        private void getTransformedString() {
            StringBuilder transform = new StringBuilder();
            int length = strings[0].length();

            for (String string : strings) {
                transform.append(string.charAt(length - 1));
            }
            transformedString = transform.toString();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            int length = scanner.nextInt();
            scanner.nextLine();

            String string = readString(scanner, length);
            BurrowsWheelerTransform burrowsWheelerTransform = new BurrowsWheelerTransform(string);

            if (t > 0) {
                System.out.println();
            }

            System.out.println(burrowsWheelerTransform.indexOfString1);
            printString(burrowsWheelerTransform.transformedString);
        }
    }

    private static String readString(Scanner scanner, int length) {
        StringBuilder string = new StringBuilder();
        while (string.length() < length) {
            string.append(scanner.nextLine());
        }

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        return string.toString();
    }

    private static void printString(String string) {
        int printedCharacters = 0;
        while (printedCharacters < string.length()) {
            for (int i = 0; i < 50 && printedCharacters < string.length(); i++) {
                System.out.print(string.charAt(printedCharacters));
                printedCharacters++;
            }
            System.out.println();
        }
    }
}
