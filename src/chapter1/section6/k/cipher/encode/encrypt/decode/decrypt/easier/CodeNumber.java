package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class CodeNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();
        Map<Character, Character> decoder = getDecoder();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                System.out.println();
            }

            String line = scanner.nextLine();

            while (!line.isEmpty()) {
                for (char character : line.toCharArray()) {
                    if (decoder.containsKey(character)) {
                        System.out.print(decoder.get(character));
                    } else {
                        System.out.print(character);
                    }
                }
                System.out.println();

                if (!scanner.hasNextLine()) {
                    break;
                }
                line = scanner.nextLine();
            }
        }
    }

    private static Map<Character, Character> getDecoder() {
        Map<Character, Character> decoder = new HashMap<>();
        decoder.put('0', 'O');
        decoder.put('1', 'I');
        decoder.put('2', 'Z');
        decoder.put('3', 'E');
        decoder.put('4', 'A');
        decoder.put('5', 'S');
        decoder.put('6', 'G');
        decoder.put('7', 'T');
        decoder.put('8', 'B');
        decoder.put('9', 'P');
        return decoder;
    }
}
