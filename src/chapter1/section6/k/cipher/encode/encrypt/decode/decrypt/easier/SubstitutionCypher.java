package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class SubstitutionCypher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                System.out.println();
            }

            String plaintextAlphabet = scanner.nextLine();
            String substitutionAlphabet = scanner.nextLine();
            Map<Character, Character> alphabetMap = createAlphabetMap(plaintextAlphabet, substitutionAlphabet);

            System.out.println(substitutionAlphabet);
            System.out.println(plaintextAlphabet);


            String line = scanner.nextLine();

            while (!line.isEmpty()) {
                encrypt(line, alphabetMap);

                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                } else {
                    break;
                }
            }
        }
    }

    private static void encrypt(String message, Map<Character, Character> alphabetMap) {
        for (char character : message.toCharArray()) {
            if (alphabetMap.containsKey(character)) {
                System.out.print(alphabetMap.get(character));
            } else {
                System.out.print(character);
            }
        }
        System.out.println();
    }

    private static Map<Character, Character> createAlphabetMap(String plaintextAlphabet, String substitutionAlphabet) {
        Map<Character, Character> alphabetMap = new HashMap<>();
        for (int i = 0; i < plaintextAlphabet.length(); i++) {
            alphabetMap.put(plaintextAlphabet.charAt(i), substitutionAlphabet.charAt(i));
        }
        return alphabetMap;
    }
}
