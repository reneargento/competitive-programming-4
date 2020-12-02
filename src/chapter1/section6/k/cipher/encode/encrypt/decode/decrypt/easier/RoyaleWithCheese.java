package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class RoyaleWithCheese {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String houseId = scanner.next();
            System.out.println(codifyId(houseId));
        }
    }

    private static String codifyId(String houseId) {
        Map<Character, Integer> mapper = new HashMap<>();
        int houseNumber = 1;
        StringBuilder codifiedId = new StringBuilder();

        for (char character : houseId.toCharArray()) {
            if (mapper.containsKey(character)) {
                codifiedId.append(mapper.get(character));
            } else {
                int value = getValue(houseNumber);
                mapper.put(character, value);
                codifiedId.append(value);
                houseNumber++;
            }
        }
        return codifiedId.toString();
    }

    private static int getValue(int houseNumber) {
        StringBuilder value = new StringBuilder();

        while (houseNumber > 0) {
            int digit = houseNumber % 10;

            if (digit == 2) {
                value.insert(0, 5);
            } else if (digit == 5) {
                value.insert(0, 2);
            } else if (digit == 6) {
                value.insert(0, 9);
            } else if (digit == 9) {
                value.insert(0, 6);
            } else {
                value.insert(0, digit);
            }

            houseNumber /= 10;
        }
        return Integer.parseInt(value.toString());
    }
}
