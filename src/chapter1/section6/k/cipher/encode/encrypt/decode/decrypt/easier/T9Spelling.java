package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 26/11/20.
 */
public class T9Spelling {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        Map<Character, String> t9Map = createT9Map();

        for (int t = 1; t <= tests; t++) {
            String message = scanner.nextLine();
            String keyPresses = getKeyPresses(message, t9Map);
            System.out.printf("Case #%d: %s\n", t, keyPresses);
        }
    }

    private static String getKeyPresses(String message, Map<Character, String> t9Map) {
        StringBuilder keyPresses = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char letter = message.charAt(i);
            String keys = t9Map.get(letter);

            if (i > 0 && keys.charAt(0) == keyPresses.charAt(keyPresses.length() - 1)) {
                keyPresses.append(" ");
            }
            keyPresses.append(keys);
        }
        return keyPresses.toString();
    }

    private static Map<Character, String> createT9Map() {
        Map<Character, String> t9Map = new HashMap<>();
        t9Map.put('a', "2");
        t9Map.put('b', "22");
        t9Map.put('c', "222");
        t9Map.put('d', "3");
        t9Map.put('e', "33");
        t9Map.put('f', "333");
        t9Map.put('g', "4");
        t9Map.put('h', "44");
        t9Map.put('i', "444");
        t9Map.put('j', "5");
        t9Map.put('k', "55");
        t9Map.put('l', "555");
        t9Map.put('m', "6");
        t9Map.put('n', "66");
        t9Map.put('o', "666");
        t9Map.put('p', "7");
        t9Map.put('q', "77");
        t9Map.put('r', "777");
        t9Map.put('s', "7777");
        t9Map.put('t', "8");
        t9Map.put('u', "88");
        t9Map.put('v', "888");
        t9Map.put('w', "9");
        t9Map.put('x', "99");
        t9Map.put('y', "999");
        t9Map.put('z', "9999");
        t9Map.put(' ', "0");
        return t9Map;
    }
}
