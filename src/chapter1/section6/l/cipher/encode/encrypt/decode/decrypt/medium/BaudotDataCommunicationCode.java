package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 01/12/20.
 */
public class BaudotDataCommunicationCode {

    private static String SHIFT_DOWN = "shift down";
    private static String SHIFT_UP = "shift up";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String downShiftCharacters = scanner.nextLine();
        String upShiftCharacters = scanner.nextLine();

        Map<String, String> downShiftEncoder = getMapping(downShiftCharacters);
        Map<String, String> upShiftEncoder = getMapping(upShiftCharacters);

        while (scanner.hasNext()) {
            String message = scanner.next();
            StringBuilder translatedMessage = new StringBuilder();
            StringBuilder bits = new StringBuilder();
            boolean isDownShift = true;

            for (int i = 0; i < message.length(); i++) {
                bits.append(message.charAt(i));

                if ((i + 1) % 5 == 0) {
                    String value;

                    if (isDownShift) {
                        value = downShiftEncoder.get(bits.toString());
                    } else {
                        value = upShiftEncoder.get(bits.toString());
                    }

                    if (value.equals(SHIFT_DOWN)) {
                        isDownShift = true;
                    } else if (value.equals(SHIFT_UP)) {
                        isDownShift = false;
                    } else {
                        translatedMessage.append(value);
                    }

                    bits = new StringBuilder();
                }
            }
            System.out.println(translatedMessage);
        }
    }

    private static Map<String, String> getMapping(String characterSet) {
        Map<String, String> mapping = new HashMap<>();

        int bitsDecimal = 0;
        for (char character : characterSet.toCharArray()) {
            String bits = appendZeroBits(Integer.toBinaryString(bitsDecimal));

            switch (bits) {
                case "11011":
                    mapping.put(bits, SHIFT_DOWN);
                    break;
                case "11111":
                    mapping.put(bits, SHIFT_UP);
                    break;
                default:
                    mapping.put(bits, String.valueOf(character));
                    break;
            }
            bitsDecimal++;
        }
        return mapping;
    }

    private static String appendZeroBits(String bits) {
        while (bits.length() != 5) {
            bits = "0" + bits;
        }
        return bits;
    }
}
