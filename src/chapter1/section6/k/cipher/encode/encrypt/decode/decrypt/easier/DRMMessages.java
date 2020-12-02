package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class DRMMessages {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String DRMMessage = scanner.next();
        int length = DRMMessage.length();

        String firstHalf = DRMMessage.substring(0, length / 2);
        String secondHalf = DRMMessage.substring(length / 2);

        String rotatedFirstHalf = rotate(firstHalf);
        String rotatedSecondHalf = rotate(secondHalf);

        System.out.println(mergeStrings(rotatedFirstHalf, rotatedSecondHalf));
    }

    private static String rotate(String string) {
        int rotationValue = 0;
        for (char character : string.toCharArray()) {
            rotationValue += getValue(character);
        }

        StringBuilder rotatedString = new StringBuilder();
        for (char character : string.toCharArray()) {
            rotatedString.append(rotate(character, rotationValue));
        }
        return rotatedString.toString();
    }

    private static int getValue(char character) {
        return character - 'A';
    }

    private static char rotate(char character, char baseRotation) {
        return rotate(character, getValue(baseRotation));
    }

    private static char rotate(char character, int positions) {
        int newValue = (getValue(character) + positions) % 26;
        return (char) ('A' + newValue);
    }

    private static String mergeStrings(String string1, String string2) {
        StringBuilder mergedString = new StringBuilder();
        for (int i = 0; i < string1.length(); i++) {
            char rotatedChar = rotate(string1.charAt(i), string2.charAt(i));
            mergedString.append(rotatedChar);
        }
        return mergedString.toString();
    }
}
