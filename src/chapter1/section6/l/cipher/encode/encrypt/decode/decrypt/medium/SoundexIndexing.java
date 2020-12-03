package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 01/12/20.
 */
public class SoundexIndexing {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%9s%-25s%s\n", "", "NAME", "SOUNDEX CODE");

        while (scanner.hasNext()) {
            String name = scanner.next();
            String soundexCode = encodeToSoundexCode(name);
            System.out.printf("%9s%-25s%s\n", "", name, soundexCode);
        }
        System.out.printf("%19sEND OF OUTPUT\n", "");
    }

    private static String encodeToSoundexCode(String name) {
        StringBuilder soundexCode = new StringBuilder();
        soundexCode.append(name.charAt(0));
        boolean canRepeat = false;

        for (int i = 1; i < name.length(); i++) {
            char character = name.charAt(i);
            if (shouldIgnore(character)) {
                canRepeat = true;
                continue;
            }

            int code = getCode(character);

            int previousCode;
            char previousCharacter = soundexCode.charAt(soundexCode.length() - 1);
            if (Character.isDigit(previousCharacter)) {
                previousCode = Character.getNumericValue(previousCharacter);
            } else {
                previousCode = getCode(previousCharacter);
            }

            if (previousCode == code && !canRepeat) {
                continue;
            }

            soundexCode.append(code);
            canRepeat = false;
        }

        while (soundexCode.length() < 4) {
            soundexCode.append('0');
        }
        return soundexCode.substring(0, 4);
    }

    private static int getCode(char character) {
        if (character == 'B' ||
                character == 'P' ||
                character == 'F' ||
                character == 'V') {
            return 1;
        }
        if (character == 'C' ||
                character == 'S' ||
                character == 'K' ||
                character == 'G' ||
                character == 'J' ||
                character == 'Q' ||
                character == 'X' ||
                character == 'Z') {
            return 2;
        }
        if (character == 'D' || character == 'T') {
            return 3;
        }
        if (character == 'L') {
            return 4;
        }
        if (character == 'M' || character == 'N') {
            return 5;
        }
        if (character == 'R') {
            return 6;
        }
        return -1;
    }

    private static boolean shouldIgnore(char character) {
        return character == 'A' ||
                character == 'E' ||
                character == 'I' ||
                character == 'O' ||
                character == 'U' ||
                character == 'Y' ||
                character == 'W' ||
                character == 'H';
    }
}
