package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/03/21.
 */
public class NumericallySpeaking {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String element = FastReader.next();
        BigInteger[] powersOf26 = getPowersOf26();

        while (!element.equals("*")) {
            String word;
            BigInteger number;

            if (Character.isDigit(element.charAt(0))) {
                number = new BigInteger(element);
                word = mapNumberToWord(number);
            } else {
                word = element;
                number = mapWordToNumber(element, powersOf26);
            }

            String formattedNumber = formatNumber(number.toString());
            String output = String.format("%-22s%s", word, formattedNumber);
            System.out.println(output);

            element = FastReader.next();
        }
    }

    private static BigInteger mapWordToNumber(String word, BigInteger[] powersOf26) {
        BigInteger number = BigInteger.ZERO;

        for (int i = 0; i < word.length(); i++) {
            int charValue = word.charAt(i) - 'a' + 1;

            int power = word.length() - 1 - i;
            BigInteger powerValue = powersOf26[power];
            BigInteger letterValue = BigInteger.valueOf(charValue).multiply(powerValue);
            number = number.add(letterValue);
        }
        return number;
    }

    private static String mapNumberToWord(BigInteger number) {
        StringBuilder word = new StringBuilder();
        BigInteger bigInteger26 = BigInteger.valueOf(26);

        while (number.compareTo(BigInteger.ZERO) > 0) {
            char letter = (char) ('a' + number.mod(bigInteger26).intValue() - 1);
            word.insert(0, letter);
            number = number.divide(bigInteger26);
        }
        return word.toString();
    }

    private static BigInteger[] getPowersOf26() {
        BigInteger[] powers = new BigInteger[20];
        powers[0] = BigInteger.ONE;
        BigInteger bigInteger26 = BigInteger.valueOf(26);

        for (int i = 1; i < powers.length; i++) {
            powers[i] = powers[i - 1].multiply(bigInteger26);
        }
        return powers;
    }

    private static String formatNumber(String number) {
        StringBuilder formattedNumber = new StringBuilder();

        for (int i = 0; i < number.length(); i++) {
            if (i != 0 && i % 3 == 0) {
                formattedNumber.insert(0, ',');
            }

            int digitIndex = number.length() - 1 - i;
            char digit = number.charAt(digitIndex);
            formattedNumber.insert(0, digit);
        }

        return formattedNumber.toString();
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }
    }
}
