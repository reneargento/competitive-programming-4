package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/06/2026.
 */
public class BinaryMultiplication {

    private static class Bit {
        int value;
        boolean isOffset;

        public Bit(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            if (isOffset) {
                return "";
            } else {
                return String.valueOf(value);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String bits1 = FastReader.next();
        String bits2 = FastReader.next();
        int testCase = 0;
        while (!bits1.equals("0") || !bits2.equals("0")) {
            if (testCase > 0) {
                outputWriter.printLine();
            }
            multiply(bits1, bits2, outputWriter);

            testCase++;
            bits1 = FastReader.next();
            bits2 = FastReader.next();
        }
        outputWriter.flush();
    }

    private static void multiply(String bits1, String bits2, OutputWriter outputWriter) {
        char[] bits1Array = bits1.toCharArray();
        char[] bits2Array = bits2.toCharArray();
        List<List<Bit>> intermediateBits = new ArrayList<>();
        int maxLength = Math.max(bits1Array.length, bits2Array.length);

        int offset = 0;
        for (int i = bits2Array.length - 1; i >= 0; i--) {
            List<Bit> bits = new ArrayList<>();
            for (int k = 0; k < offset; k++) {
                Bit bit = new Bit(0);
                bit.isOffset = true;
                bits.add(bit);
            }
            int bitValue = Character.getNumericValue(bits2Array[i]);

            for (int j = bits1Array.length - 1; j >= 0; j--) {
                int result = bitValue * Character.getNumericValue(bits1Array[j]);
                bits = addFirst(bits, new Bit(result));
            }
            maxLength = Math.max(maxLength, bits.size());
            intermediateBits.add(bits);
            offset++;
        }

        List<Bit> finalResult = new ArrayList<>();
        int bitsToSum = maxLength;
        int carry = 0;
        for (int i = 0; i < bitsToSum; i++) {
            int sum = 0;

            for (int j = 0; j < intermediateBits.size(); j++) {
                List<Bit> bits = intermediateBits.get(j);
                int index;
                if (i < bits.size()) {
                    index = bits.size() - 1 - i;
                    Bit bit = bits.get(index);
                    sum += bit.value;
                }
            }

            int totalBits = sum + carry;
            String totalBitsValue = decimalToBinary(totalBits);
            String bitsToCarry = totalBitsValue.substring(0, totalBitsValue.length() - 1);

            if (totalBits > 1) {
                if (totalBits % 2 == 0) {
                    totalBits = 0;
                } else {
                    totalBits = 1;
                }
                carry = Integer.parseInt(bitsToCarry, 2);
            } else {
                carry = 0;
            }
            finalResult = addFirst(finalResult, new Bit(totalBits));
        }

        while (carry > 0) {
            carry--;
            finalResult = addFirst(finalResult, new Bit(1));
        }

        maxLength = Math.max(maxLength, finalResult.size());
        printOperation(bits1, bits2, intermediateBits, finalResult, maxLength, outputWriter);
    }

    private static List<Bit> addFirst(List<Bit> bits, Bit bitToAdd) {
        List<Bit> newBits = new ArrayList<>();
        newBits.add(bitToAdd);
        newBits.addAll(bits);
        return newBits;
    }

    private static String decimalToBinary(int decimalValue) {
        if (decimalValue == 0) {
            return "0";
        }
        StringBuilder binaryString = new StringBuilder();

        while (decimalValue > 0) {
            long remaining = decimalValue % 2;
            binaryString.insert(0, remaining);
            decimalValue /= 2;
        }
        return binaryString.toString();
    }

    private static void printOperation(String bits1, String bits2, List<List<Bit>> intermediateBits,
                                       List<Bit> finalResult, int maxLength, OutputWriter outputWriter) {
        String format = "%" + maxLength + "s";
        outputWriter.printLine(String.format(format, bits1));
        outputWriter.printLine(String.format(format, bits2));

        int maxDotsSize = Math.max(bits1.length(), bits2.length());
        String topSeparator = getSeparator(maxDotsSize);
        outputWriter.printLine(String.format(format, topSeparator));

        for (int i = 0; i < intermediateBits.size(); i++) {
            List<Bit> bits = intermediateBits.get(i);
            int offset = maxLength - bits.size();
            for (int j = 0; j < offset; j++) {
                outputWriter.print(" ");
            }
            for (Bit bit : bits) {
                outputWriter.print(bit);
            }
            outputWriter.printLine();
        }

        String bottomSeparator = getSeparator(maxLength);
        outputWriter.printLine(String.format(format, bottomSeparator));
        for (Bit bit : finalResult) {
            outputWriter.print(bit.value);
        }
        outputWriter.printLine();
    }

    private static String getSeparator(int length) {
        StringBuilder separator = new  StringBuilder();
        for (int i = 0; i < length; i++) {
            separator.append("-");
        }
        return separator.toString();
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