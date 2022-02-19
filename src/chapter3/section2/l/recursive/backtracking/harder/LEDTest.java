package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/02/22.
 */
public class LEDTest {

    private enum Result {
        MATCHES, UNKNOWN
    }

    private static final char[][] numberLEDs = {
            "YYYYYYN".toCharArray(),
            "NYYNNNN".toCharArray(),
            "YYNYYNY".toCharArray(),
            "YYYYNNY".toCharArray(),
            "NYYNNYY".toCharArray(),
            "YNYYNYY".toCharArray(),
            "YNYYYYY".toCharArray(),
            "YYYNNNN".toCharArray(),
            "YYYYYYY".toCharArray(),
            "YYYYNYY".toCharArray(),
    };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int lines = FastReader.nextInt();

        while (lines != 0) {
            char[][] leds = new char[lines][7];
            for (int i = 0; i < lines; i++) {
                leds[i] = FastReader.getLine().toCharArray();
            }

            boolean[][] visited = new boolean[128][lines];
            if (iterateAndCheckCountdown(leds, 0, 0, visited)) {
                outputWriter.printLine("MATCH");
            } else {
                outputWriter.printLine("MISMATCH");
            }
            lines = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean iterateAndCheckCountdown(char[][] leds, int mask, int index, boolean[][] visited) {
        if (index == leds[0].length) {
            return matchesCountdown(leds, mask, 0, null, visited);
        }

        int maskIndex = leds[0].length - index - 1;
        int maskWithLedOn = mask | (1 << maskIndex);
        return iterateAndCheckCountdown(leds, mask, index + 1, visited)
                || iterateAndCheckCountdown(leds, maskWithLedOn, index + 1, visited);
    }

    private static boolean matchesCountdown(char[][] leds, int mask, int startIndex,
                                            boolean[] previouslyFilteredNumbers, boolean[][] visited) {
        if (visited[mask][startIndex]) {
            return false;
        }

        boolean[] filteredNumbers = new boolean[11];
        if (previouslyFilteredNumbers != null) {
            for (int i = 0; i < previouslyFilteredNumbers.length; i++) {
                if (previouslyFilteredNumbers[i]) {
                    filteredNumbers[i] = true;
                }
            }
        }

        for (int i = startIndex; i < leds.length; i++) {
            // Check new lights broken
            Result result = checkNumbersWithBrokenLights(leds[i], mask, i, leds, filteredNumbers, visited);
            if (result.equals(Result.MATCHES)) {
                return true;
            }

            boolean[] numbersWithoutBrokenLights = getPossibleNumbers(leds[i], mask);
            if (i == 0) {
                filteredNumbers = numbersWithoutBrokenLights;
            } else {
                filteredNumbers = filterNumbers(filteredNumbers, numbersWithoutBrokenLights);
            }
        }

        for (boolean filteredNumber : filteredNumbers) {
            if (filteredNumber) {
                return true;
            }
        }
        visited[mask][startIndex] = true;
        return false;
    }

    private static boolean[] getPossibleNumbers(char[] led, int mask) {
        boolean[] numbers = new boolean[11];
        for (int i = 0; i < numberLEDs.length; i++) {
            if (canBeNumber(led, numberLEDs[i], mask)) {
                numbers[i] = true;
            }
        }
        return numbers;
    }

    private static boolean canBeNumber(char[] currentLED, char[] numberLED, int mask) {
        for (int i = 0; i < currentLED.length; i++) {
            int maskIndex = currentLED.length - i - 1;
            boolean isLedOn = (mask & (1 << maskIndex)) > 0;

            if ((currentLED[i] == 'N' && numberLED[i] == 'Y' && isLedOn)
                    || (currentLED[i] == 'Y' && numberLED[i] == 'N')
                    || (currentLED[i] == 'Y' && !isLedOn)) {
                return false;
            }
        }
        return true;
    }

    private static Result checkNumbersWithBrokenLights(char[] led, int mask, int index, char[][] leds,
                                                       boolean[] filteredNumbers, boolean[][] visited) {
        for (int i = 0; i < numberLEDs.length; i++) {
            if ((index == 0 || filteredNumbers[i + 1])) {
                char[] numberLED = numberLEDs[i];
                Result result = checkNumberWithBrokenLights(led, numberLED, mask, index, leds,
                        filteredNumbers, visited);
                if (result.equals(Result.MATCHES)) {
                    return result;
                }
            }
        }
        return Result.UNKNOWN;
    }

    private static Result checkNumberWithBrokenLights(char[] currentLED, char[] numberLED, int mask,
                                                      int index, char[][] leds, boolean[] filteredNumbers,
                                                      boolean[][] visited) {
        boolean[] brokenLights = new boolean[7];
        boolean hasBrokenLights = false;

        for (int i = 0; i < currentLED.length; i++) {
            int maskIndex = currentLED.length - i - 1;
            boolean isLedOn = (mask & (1 << maskIndex)) > 0;

            if (currentLED[i] == 'N' && numberLED[i] == 'Y' && isLedOn) {
                brokenLights[maskIndex] = true;
                hasBrokenLights = true;
            } else if (currentLED[i] != numberLED[i] && isLedOn) {
                return Result.UNKNOWN;
            }
        }

        if (hasBrokenLights) {
            int maskWithBrokenLights = updateMaskWithBrokenLights(mask, brokenLights);
            boolean matchesWithNewBrokenLights = matchesCountdown(leds, maskWithBrokenLights, index,
                    filteredNumbers, visited);
            if (matchesWithNewBrokenLights) {
                return Result.MATCHES;
            }
        }
        return Result.UNKNOWN;
    }

    private static boolean[] filterNumbers(boolean[] previousNumbers, boolean[] numbers) {
        boolean[] filteredNumbers = new boolean[11];
        for (int i = 0; i < filteredNumbers.length - 1; i++) {
            if (previousNumbers[i + 1] && numbers[i]) {
                filteredNumbers[i] = true;
            }
        }
        return filteredNumbers;
    }

    private static int updateMaskWithBrokenLights(int mask, boolean[] newBrokenLights) {
        for (int i = 0; i < newBrokenLights.length; i++) {
            if (newBrokenLights[i]) {
                mask = mask & ~(1 << i);
            }
        }
        return mask;
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
