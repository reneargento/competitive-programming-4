package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/02/22.
 */
public class BankNotQuiteOCR {

    private static class Result {
        int[] digits;
        String result;

        public Result(int[] digits, String result) {
            this.digits = digits;
            this.result = result;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] bitmaskToDigitMap = createBitmaskToDigitMap();

        for (int t = 0; t < tests; t++) {
            char[] line1 = FastReader.getLine().toCharArray();
            char[] line2 = FastReader.getLine().toCharArray();
            char[] line3 = FastReader.getLine().toCharArray();

            int[] segmentMasks = new int[9];
            for (int i = 0; i < segmentMasks.length; i++) {
                int startIndex = 3 * i;
                segmentMasks[i] = getSegments(line1, line2, line3, startIndex);
            }

            Result result = computeAccount(segmentMasks, bitmaskToDigitMap);
            if (result.digits != null) {
                int[] digits = result.digits;
                for (int digit : digits) {
                    outputWriter.print(digit);
                }
                outputWriter.printLine();
            } else {
                outputWriter.printLine(result.result);
            }
        }
        outputWriter.flush();
    }

    private static Result computeAccount(int[] segmentMasks, int[] bitmaskToDigitMap) {
        int defectiveDigitIndex = -1;
        int[] digits = new int[9];

        for (int i = 0; i < segmentMasks.length; i++) {
            int digit = bitmaskToDigitMap[segmentMasks[i]];
            if (digit == -1) {
                defectiveDigitIndex = i;
            }
            digits[i] = digit;
        }

        List<Integer> defectiveDigitIndexes = new ArrayList<>();
        if (defectiveDigitIndex != -1) {
            defectiveDigitIndexes.add(defectiveDigitIndex);
        } else if (isValidAccount(digits)) {
            return new Result(digits, null);
        } else {
            for (int i = 0; i < 9; i++) {
                defectiveDigitIndexes.add(i);
            }
        }
        return computeAccount(segmentMasks, bitmaskToDigitMap, digits, defectiveDigitIndexes);
    }

    private static Result computeAccount(int[] segmentMasks, int[] bitmaskToDigitMap, int[] digits,
                                         List<Integer> defectiveDigitIndexes) {
        int accountsFound = 0;
        int[] firstAccountsFound = null;

        for (int defectiveDigitIndex : defectiveDigitIndexes) {
            Set<Integer> possibleDigits = new HashSet<>();
            getPossibleDigits(bitmaskToDigitMap, possibleDigits, 0, segmentMasks[defectiveDigitIndex]);

            if (!possibleDigits.isEmpty()) {
                int currentDigit = digits[defectiveDigitIndex];
                for (int possibleDigit : possibleDigits) {
                    digits[defectiveDigitIndex] = possibleDigit;
                    if (isValidAccount(digits)) {
                        if (accountsFound == 0) {
                            firstAccountsFound = new int[digits.length];
                            System.arraycopy(digits, 0, firstAccountsFound, 0, digits.length);
                        }
                        accountsFound++;
                    }
                }
                digits[defectiveDigitIndex] = currentDigit;
            }
        }

        if (accountsFound == 1) {
            return new Result(firstAccountsFound, null);
        } else if (accountsFound == 0) {
            return new Result(null, "failure");
        } else {
            return new Result(null, "ambiguous");
        }
    }

    private static void getPossibleDigits(int[] bitmaskToDigitMap, Set<Integer> possibleDigits, int index,
                                          int mask) {
        if (bitmaskToDigitMap[mask] != -1) {
            possibleDigits.add(bitmaskToDigitMap[mask]);
        }

        if (index == 7) {
            return;
        }

        if ((mask & (1 << index)) == 0) {
            int maskWithDigit = mask | (1 << index);
            getPossibleDigits(bitmaskToDigitMap, possibleDigits, index + 1, maskWithDigit);
        }
        getPossibleDigits(bitmaskToDigitMap, possibleDigits, index + 1, mask);
    }

    private static int getSegments(char[] line1, char[] line2, char[] line3, int startIndex) {
        int segmentsMask = 0;
        if (line1[startIndex + 1] == '_') {
            // Set bit 0
            segmentsMask = 1;
        }
        if (line2[startIndex + 1] == '_') {
            segmentsMask |= (1 << 1);
        }
        if (line3[startIndex + 1] == '_') {
            segmentsMask |= (1 << 2);
        }

        if (line2[startIndex] == '|') {
            segmentsMask |= (1 << 3);
        }
        if (line2[startIndex + 2] == '|') {
            segmentsMask |= (1 << 4);
        }

        if (line3[startIndex] == '|') {
            segmentsMask |= (1 << 5);
        }
        if (line3[startIndex + 2] == '|') {
            segmentsMask |= (1 << 6);
        }
        return segmentsMask;
    }

    private static boolean isValidAccount(int[] digits) {
        long sum = 0;
        for (int i = 0; i < digits.length; i++) {
            int multiplier = 9 - i;
            sum += digits[i] * multiplier;
        }
        return sum % 11 == 0;
    }

    private static int[] createBitmaskToDigitMap() {
        // Considering horizontal segments as 0, 1, 2 and vertical segments as 3, 4, 5, 6
        int[][] digitSegments = {
                { 0, 2, 3, 4, 5, 6 },
                { 4, 6 },
                { 0, 1, 2, 4, 5 },
                { 0, 1, 2, 4, 6 },
                { 1, 3, 4, 6 },
                { 0, 1, 2, 3, 6 },
                { 0, 1, 2, 3, 5, 6 },
                { 0, 4, 6 },
                { 0, 1, 2, 3, 4, 5, 6 },
                { 0, 1, 2, 3, 4, 6 }
        };

        int[] bitmaskToDigitMap = new int[1 << 8];
        Arrays.fill(bitmaskToDigitMap, -1);

        for (int i = 0; i < digitSegments.length; i++) {
            int segmentMask = 0;
            for (int segment : digitSegments[i]) {
                segmentMask |= (1 << segment);
            }
            bitmaskToDigitMap[segmentMask] = i;
        }
        return bitmaskToDigitMap;
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

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static String getLine() throws IOException {
            return reader.readLine();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
