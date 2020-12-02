package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class FindTheTelephone {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Character, Character> symbolMapper = createSymbolMapper();

        while (scanner.hasNext()) {
            String phone = scanner.next();
            for (char symbol : phone.toCharArray()) {
                System.out.print(symbolMapper.get(symbol));
            }
            System.out.println();
        }
    }

    private static Map<Character, Character> createSymbolMapper() {
        Map<Character, Character> symbolMapper = new HashMap<>();
        symbolMapper.put('A', '2');
        symbolMapper.put('B', '2');
        symbolMapper.put('C', '2');
        symbolMapper.put('D', '3');
        symbolMapper.put('E', '3');
        symbolMapper.put('F', '3');
        symbolMapper.put('G', '4');
        symbolMapper.put('H', '4');
        symbolMapper.put('I', '4');
        symbolMapper.put('J', '5');
        symbolMapper.put('K', '5');
        symbolMapper.put('L', '5');
        symbolMapper.put('M', '6');
        symbolMapper.put('N', '6');
        symbolMapper.put('O', '6');
        symbolMapper.put('P', '7');
        symbolMapper.put('Q', '7');
        symbolMapper.put('R', '7');
        symbolMapper.put('S', '7');
        symbolMapper.put('T', '8');
        symbolMapper.put('U', '8');
        symbolMapper.put('V', '8');
        symbolMapper.put('W', '9');
        symbolMapper.put('X', '9');
        symbolMapper.put('Y', '9');
        symbolMapper.put('Z', '9');
        symbolMapper.put('0', '0');
        symbolMapper.put('1', '1');
        symbolMapper.put('-', '-');
        return symbolMapper;
    }
}
