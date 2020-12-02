package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class DecodingTheMessage {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();

        for (int t = 1; t <= tests; t++) {
            if (t > 1) {
                System.out.println();
            }
            System.out.printf("Case #%d:\n", t);

            String line = scanner.nextLine();

            while (!line.isEmpty()) {
                List<String> words = getWords(line);
                int secretIndex = 0;
                StringBuilder decodedMessage = new StringBuilder();

                for (String word : words) {
                    if (word.length() > secretIndex) {
                        decodedMessage.append(word.charAt(secretIndex));
                        secretIndex++;
                    }
                }
                System.out.println(decodedMessage);
                line = scanner.nextLine();
            }
        }
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
    }
}
