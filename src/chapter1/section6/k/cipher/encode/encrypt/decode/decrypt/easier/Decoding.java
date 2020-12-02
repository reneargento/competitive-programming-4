package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class Decoding {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            String encodedString = scanner.next();
            String decodedString = decode(encodedString);
            System.out.printf("Case %d: %s\n", t, decodedString);
        }
    }

    private static String decode(String encodedString) {
        StringBuilder decodedString = new StringBuilder();

        for (int i = 0; i < encodedString.length(); i++) {
            char character = encodedString.charAt(i);

            StringBuilder frequencyString = new StringBuilder();

            for (int j = i + 1; j < encodedString.length(); j++) {
                char nextCharacter = encodedString.charAt(j);

                if (Character.isLetter(nextCharacter) || j == encodedString.length() - 1) {
                    if (j == encodedString.length() - 1) {
                        frequencyString.append(nextCharacter);
                    }

                    int frequency = Integer.parseInt(frequencyString.toString());
                    for (int f = 0; f < frequency; f++) {
                        decodedString.append(character);
                    }
                    i += frequencyString.length();
                    break;
                } else {
                    frequencyString.append(nextCharacter);
                }
            }
        }
        return decodedString.toString();
    }
}
