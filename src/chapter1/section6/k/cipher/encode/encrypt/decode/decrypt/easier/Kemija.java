package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class Kemija {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String codedSentence = scanner.nextLine();
        StringBuilder decodedSentence = new StringBuilder();

        for (int i = 0; i < codedSentence.length(); i++) {
            char character = codedSentence.charAt(i);
            decodedSentence.append(character);

            if (isVowel(character)) {
                i += 2;
            }
        }
        System.out.println(decodedSentence);
    }

    private static boolean isVowel(char character) {
        return character == 'a' ||
                character == 'e' ||
                character == 'i' ||
                character == 'o' ||
                character == 'u';
    }
}
