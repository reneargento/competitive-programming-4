package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 30/11/20.
 */
public class NumeralHieroglyphs {

    private enum HieroglyphParser {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
        ERROR
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        Map<Character, Integer> hieroglyphMap = createHieroglyphMap();

        for (int t = 0; t < tests; t++) {
            String hieroglyphs = scanner.next();
            HieroglyphParser hieroglyphParser = getParseDirection(hieroglyphs, hieroglyphMap);

            if (hieroglyphParser.equals(HieroglyphParser.ERROR)) {
                System.out.println("error");
            } else {
                System.out.println(convertHieroglyphToNumber(hieroglyphs, hieroglyphMap, hieroglyphParser));
            }
        }
    }

    private static HieroglyphParser getParseDirection(String hieroglyphs, Map<Character, Integer> hieroglyphMap) {
        Map<Character, Integer> hieroglyphCount = new HashMap<>();
        int previousValueLeftToRight = Integer.MAX_VALUE;
        boolean validLeftToRight = true;
        int previousValueRightToLeft = Integer.MAX_VALUE;
        boolean validRightToLeft = true;

        for (char hieroglyph : hieroglyphs.toCharArray()) {
            int count = hieroglyphCount.getOrDefault(hieroglyph, 0);
            count++;

            if (count > 9) {
                return HieroglyphParser.ERROR;
            }
            hieroglyphCount.put(hieroglyph, count);

            int value = hieroglyphMap.get(hieroglyph);
            if (value > previousValueLeftToRight && validLeftToRight) {
                validLeftToRight = false;
            } else {
                previousValueLeftToRight = value;
            }
        }

        if (validLeftToRight) {
            return HieroglyphParser.LEFT_TO_RIGHT;
        }

        for (int i = hieroglyphs.length() - 1; i >= 0; i--) {
            int value = hieroglyphMap.get(hieroglyphs.charAt(i));
            if (value > previousValueRightToLeft && validRightToLeft) {
                validRightToLeft = false;
            } else {
                previousValueRightToLeft = value;
            }
        }

        if (validRightToLeft) {
            return HieroglyphParser.RIGHT_TO_LEFT;
        }
        return HieroglyphParser.ERROR;
    }

    private static int convertHieroglyphToNumber(String hieroglyphs, Map<Character, Integer> hieroglyphMap,
                                                 HieroglyphParser hieroglyphParser) {
        int value = 0;
        boolean isLeftToRight = hieroglyphParser == HieroglyphParser.LEFT_TO_RIGHT;
        int start = isLeftToRight ? 0 : hieroglyphs.length() - 1;
        int end = isLeftToRight ? hieroglyphs.length() : -1;
        int increment = isLeftToRight ? 1 : -1;

        for (int i = start; i != end; i += increment) {
            value += hieroglyphMap.get(hieroglyphs.charAt(i));
        }

        return value;
    }

    private static Map<Character, Integer> createHieroglyphMap() {
        Map<Character, Integer> hieroglyphMap = new HashMap<>();
        hieroglyphMap.put('B', 1);
        hieroglyphMap.put('U', 10);
        hieroglyphMap.put('S', 100);
        hieroglyphMap.put('P', 1000);
        hieroglyphMap.put('F', 10000);
        hieroglyphMap.put('T', 100000);
        hieroglyphMap.put('M', 1000000);
        return hieroglyphMap;
    }
}
