package chapter6.section7.palindrome.generating;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/10/2026.
 */
public class EvilStrawWartsLive {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.getLine();
            int neededSwaps = countNeededSwaps(string);
            if (neededSwaps == -1) {
                outputWriter.printLine("Impossible");
            } else {
                outputWriter.printLine(neededSwaps);
            }
        }
        outputWriter.flush();
    }

    private static int countNeededSwaps(String string) {
        char[] stringChars = string.toCharArray();

        if (!isPossible(stringChars)) {
            return -1;
        }
        return swapsNeeded(stringChars, 0, string.length() - 1);
    }

    private static int swapsNeeded(char[] string, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return 0;
        }

        if (string[startIndex] == string[endIndex]) {
            return swapsNeeded(string, startIndex + 1, endIndex - 1);
        } else {
            char leftChar = string[startIndex];
            int matchIndex = endIndex;
            for (int i = endIndex; i >= startIndex; i--) {
                if (leftChar == string[i]) {
                    matchIndex = i;
                    break;
                }
            }

            if (matchIndex == startIndex) {
                swapElements(string, startIndex, startIndex + 1);
                return 1 + swapsNeeded(string, startIndex, endIndex);
            } else {
                int swapsNeeded = endIndex - matchIndex;
                for (int i = 0; i < swapsNeeded; i++) {
                    swapElements(string, matchIndex + i, matchIndex + i + 1);
                }
                return swapsNeeded + swapsNeeded(string, startIndex + 1, endIndex - 1);
            }
        }
    }

    private static void swapElements(char[] string, int position1, int position2) {
        char aux = string[position1];
        string[position1] = string[position2];
        string[position2] = aux;
    }

    private static boolean isPossible(char[] string) {
        int[] frequencies = new int[26];
        for (char character : string) {
            frequencies[character - 'a']++;
        }

        int oddFrequencies = 0;
        for (int frequency : frequencies) {
            if (frequency % 2 == 1) {
                oddFrequencies++;
            }
        }
        return oddFrequencies <= 1;
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

        public void flush() {
            writer.flush();
        }
    }
}