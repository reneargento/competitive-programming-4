package chapter5.section2.c.base.number.conversion;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/01/25.
 */
public class ArithmeticKattis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String numberOctal = FastReader.next();
        String numberHexadecimal = octalToHexadecimal(numberOctal);
        outputWriter.printLine(numberHexadecimal);
        outputWriter.flush();
    }

    private static String octalToHexadecimal(String numberOctal) {
        String numberBinary = octalToBinary(numberOctal);
        return binaryToHexadecimal(numberBinary);
    }

    private static String octalToBinary(String numberOctal) {
        StringBuilder numberBinary = new StringBuilder();
        for (char bit : numberOctal.toCharArray()) {
            switch (bit) {
                case '0': numberBinary.append("000"); break;
                case '1': numberBinary.append("001"); break;
                case '2': numberBinary.append("010"); break;
                case '3': numberBinary.append("011"); break;
                case '4': numberBinary.append("100"); break;
                case '5': numberBinary.append("101"); break;
                case '6': numberBinary.append("110"); break;
                default: numberBinary.append("111"); break;
            }
        }

        numberBinary.reverse();
        while (numberBinary.length() % 4 != 0) {
            numberBinary.append("0");
        }
        return numberBinary.reverse().toString();
    }

    private static String binaryToHexadecimal(String numberBinary) {
        StringBuilder numberHexadecimal = new StringBuilder();

        for (int i = 0; i < numberBinary.length(); i += 4) {
            String bits = numberBinary.substring(i, i + 4);

            if (bits.equals("0000")) {
                numberHexadecimal.append("0");
            } else if (bits.equals("0001")) {
                numberHexadecimal.append("1");
            } else if (bits.equals("0010")) {
                numberHexadecimal.append("2");
            } else if (bits.equals("0011")) {
                numberHexadecimal.append("3");
            } else if (bits.equals("0100")) {
                numberHexadecimal.append("4");
            } else if (bits.equals("0101")) {
                numberHexadecimal.append("5");
            } else if (bits.equals("0110")) {
                numberHexadecimal.append("6");
            } else if (bits.equals("0111")) {
                numberHexadecimal.append("7");
            } else if (bits.equals("1000")) {
                numberHexadecimal.append("8");
            } else if (bits.equals("1001")) {
                numberHexadecimal.append("9");
            } else if (bits.equals("1010")) {
                numberHexadecimal.append("A");
            } else if (bits.equals("1011")) {
                numberHexadecimal.append("B");
            } else if (bits.equals("1100")) {
                numberHexadecimal.append("C");
            } else if (bits.equals("1101")) {
                numberHexadecimal.append("D");
            } else if (bits.equals("1110")) {
                numberHexadecimal.append("E");
            } else if (bits.equals("1111")) {
                numberHexadecimal.append("F");
            }
        }

        int firstNonZeroValue = -1;
        for (int i = 0; i < numberHexadecimal.length(); i++) {
            if (numberHexadecimal.charAt(i) != '0') {
                firstNonZeroValue = i;
                break;
            }
        }

        if (firstNonZeroValue == -1) {
            return "0";
        }
        return numberHexadecimal.substring(firstNonZeroValue);
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

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}
