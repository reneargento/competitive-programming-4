package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class DecodeTheMadMan {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String words = scanner.nextLine().toLowerCase();

        Map<Character, Character> decoder = createDecoder();

        for (char character : words.toCharArray()) {
            if (decoder.containsKey(character)) {
                System.out.print(decoder.get(character));
            } else {
                System.out.print(character);
            }
        }
        System.out.println();
    }

    private static Map<Character, Character> createDecoder() {
        Map<Character, Character> decoder = new HashMap<>();
        decoder.put('e', 'q');
        decoder.put('r', 'w');
        decoder.put('t', 'e');
        decoder.put('y', 'r');
        decoder.put('u', 't');
        decoder.put('i', 'y');
        decoder.put('o', 'u');
        decoder.put('p', 'i');
        decoder.put('[', 'o');
        decoder.put(']', 'p');
        decoder.put('\\', '[');
        decoder.put('d', 'a');
        decoder.put('f', 's');
        decoder.put('g', 'd');
        decoder.put('h', 'f');
        decoder.put('j', 'g');
        decoder.put('k', 'h');
        decoder.put('l', 'j');
        decoder.put(';', 'k');
        decoder.put('\'', 'l');
        decoder.put('c', 'z');
        decoder.put('v', 'x');
        decoder.put('b', 'c');
        decoder.put('n', 'v');
        decoder.put('m', 'b');
        decoder.put(',', 'n');
        decoder.put('.', 'm');
        decoder.put('/', ',');
        return decoder;
    }
}
