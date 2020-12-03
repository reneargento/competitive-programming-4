package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 30/11/20.
 */
public class ANewAlphabet {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        Map<Character, String> newAlphabetMapper = createNewAlphabetMapper();
        text = text.toLowerCase();

        for (char character : text.toCharArray()) {
            if (newAlphabetMapper.containsKey(character)) {
                System.out.print(newAlphabetMapper.get(character));
            } else {
                System.out.print(character);
            }
        }
    }

    private static Map<Character, String> createNewAlphabetMapper() {
        Map<Character, String> newAlphabetMapper = new HashMap<>();
        newAlphabetMapper.put('a', "@");
        newAlphabetMapper.put('b', "8");
        newAlphabetMapper.put('c', "(");
        newAlphabetMapper.put('d', "|)");
        newAlphabetMapper.put('e', "3");
        newAlphabetMapper.put('f', "#");
        newAlphabetMapper.put('g', "6");
        newAlphabetMapper.put('h', "[-]");
        newAlphabetMapper.put('i', "|");
        newAlphabetMapper.put('j', "_|");
        newAlphabetMapper.put('k', "|<");
        newAlphabetMapper.put('l', "1");
        newAlphabetMapper.put('m', "[]\\/[]");
        newAlphabetMapper.put('n', "[]\\[]");
        newAlphabetMapper.put('o', "0");
        newAlphabetMapper.put('p', "|D");
        newAlphabetMapper.put('q', "(,)");
        newAlphabetMapper.put('r', "|Z");
        newAlphabetMapper.put('s', "$");
        newAlphabetMapper.put('t', "\'][\'");
        newAlphabetMapper.put('u', "|_|");
        newAlphabetMapper.put('v', "\\/");
        newAlphabetMapper.put('w', "\\/\\/");
        newAlphabetMapper.put('x', "}{");
        newAlphabetMapper.put('y', "`/");
        newAlphabetMapper.put('z', "2");
        return newAlphabetMapper;
    }
}
