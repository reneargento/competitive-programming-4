package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.*;

/**
 * Created by Rene Argento on 01/12/20.
 */
public class FalseSenseOfSecurity {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Character, String> morseCodeMapper = createMorseCodeMapper();
        Map<String, Character> inverseMorseCodeMapper = createInverseMorseCodeMapper(morseCodeMapper);

        while (scanner.hasNext()) {
            String message = scanner.nextLine();
            StringBuilder morseCode = new StringBuilder();
            List<Integer> sizes = new ArrayList<>();

            for (char character : message.toCharArray()) {
                String code = morseCodeMapper.get(character);
                morseCode.append(code);
                sizes.add(code.length());
            }

            int index = 0;
            for (int i = sizes.size() - 1; i >= 0; i--) {
                int symbolSize = sizes.get(i);

                String code = morseCode.substring(index, index + symbolSize);
                char symbol = inverseMorseCodeMapper.get(code);
                System.out.print(symbol);
                index += symbolSize;
            }
            System.out.println();
        }
    }

    private static Map<Character, String> createMorseCodeMapper() {
        Map<Character, String> morseCodeMapper = new HashMap<>();
        morseCodeMapper.put('A', ".-");
        morseCodeMapper.put('B', "-...");
        morseCodeMapper.put('C', "-.-.");
        morseCodeMapper.put('D', "-..");
        morseCodeMapper.put('E', ".");
        morseCodeMapper.put('F', "..-.");
        morseCodeMapper.put('G', "--.");
        morseCodeMapper.put('H', "....");
        morseCodeMapper.put('I', "..");
        morseCodeMapper.put('J', ".---");
        morseCodeMapper.put('K', "-.-");
        morseCodeMapper.put('L', ".-..");
        morseCodeMapper.put('M', "--");
        morseCodeMapper.put('N', "-.");
        morseCodeMapper.put('O', "---");
        morseCodeMapper.put('P', ".--.");
        morseCodeMapper.put('Q', "--.-");
        morseCodeMapper.put('R', ".-.");
        morseCodeMapper.put('S', "...");
        morseCodeMapper.put('T', "-");
        morseCodeMapper.put('U', "..-");
        morseCodeMapper.put('V', "...-");
        morseCodeMapper.put('W', ".--");
        morseCodeMapper.put('X', "-..-");
        morseCodeMapper.put('Y', "-.--");
        morseCodeMapper.put('Z', "--..");
        morseCodeMapper.put('_', "..--");
        morseCodeMapper.put(',', ".-.-");
        morseCodeMapper.put('.', "---.");
        morseCodeMapper.put('?', "----");
        return morseCodeMapper;
    }

    private static Map<String, Character> createInverseMorseCodeMapper(Map<Character, String> morseCodeMapper) {
        Map<String, Character> inverseMorseCodeMapper = new HashMap<>();
        for (Character key : morseCodeMapper.keySet()) {
            String code = morseCodeMapper.get(key);
            inverseMorseCodeMapper.put(code, key);
        }
        return inverseMorseCodeMapper;
    }
}
