package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 12/11/20.
 */
public class ODahDahDah {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int t = 1; t <= tests; t++) {
            String message = scanner.nextLine();
            String decodedMessage = decode(message);

            if (t > 1) {
                System.out.println();
            }
            System.out.printf("Message #%d\n", t);
            System.out.println(decodedMessage);
        }
    }

    private static String decode(String message) {
        StringBuilder decodedMessage = new StringBuilder();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char character = message.charAt(i);
            if (character != ' ') {
                word.append(character);
            }
            if (character == ' ' || i == message.length() - 1) {
                char decodedSymbol = decodeSymbols(word.toString());
                decodedMessage.append(decodedSymbol);
                word = new StringBuilder();

                if (i != message.length() - 1) {
                    if (message.charAt(i + 1) == ' ') {
                        decodedMessage.append(' ');
                        i++;
                    }
                }
            }
        }
        return decodedMessage.toString();
    }

    private static Character decodeSymbols(String symbols) {
        switch (symbols) {
            case ".-": return 'A';
            case "-...": return 'B';
            case "-.-.": return 'C';
            case "-..": return 'D';
            case ".": return 'E';
            case "..-.": return 'F';
            case "--.": return 'G';
            case "....": return 'H';
            case "..": return 'I';
            case ".---": return 'J';
            case "-.-": return 'K';
            case ".-..": return 'L';
            case "--": return 'M';
            case "-.": return 'N';
            case "---": return 'O';
            case ".--.": return 'P';
            case "--.-": return 'Q';
            case ".-.": return 'R';
            case "...": return 'S';
            case "-": return 'T';
            case "..-": return 'U';
            case "...-": return 'V';
            case ".--": return 'W';
            case "-..-": return 'X';
            case "-.--": return 'Y';
            case "--..": return 'Z';
            case "-----": return '0';
            case ".----": return '1';
            case "..---": return '2';
            case "...--": return '3';
            case "....-": return '4';
            case ".....": return '5';
            case "-....": return '6';
            case "--...": return '7';
            case "---..": return '8';
            case "----.": return '9';
            case ".-.-.-": return '.';
            case "--..--": return ',';
            case "..--..": return '?';
            case ".----.": return '\'';
            case "-.-.--": return '!';
            case "-..-.": return '/';
            case "-.--.": return '(';
            case "-.--.-": return ')';
            case ".-...": return '&';
            case "---...": return ':';
            case "-.-.-.": return ';';
            case "-...-": return '=';
            case ".-.-.": return '+';
            case "-....-": return '-';
            case "..--.-": return '_';
            case ".-..-.": return '"';
            case ".--.-.": return '@';
        }
        return null;
    }
}
