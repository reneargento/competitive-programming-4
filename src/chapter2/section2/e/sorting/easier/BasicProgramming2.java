package chapter2.section2.e.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/02/21.
 */
public class BasicProgramming2 {

    public static void main(String[] args) throws IOException  {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int integersNumber = FastReader.nextInt();
        int task = FastReader.nextInt();

        int[] integers = new int[integersNumber];
        Map<Integer, Integer> frequencies = new HashMap<>();

        int majorityElement = -1;
        boolean unique = true;
        int integersBetween100And999Count = 0;

        for (int i = 0; i < integersNumber; i++) {
            int integer = FastReader.nextInt();
            integers[i] = integer;
            int frequency = frequencies.getOrDefault(integer, 0);
            int newFrequency = frequency + 1;

            if (newFrequency > integersNumber / 2) {
                majorityElement = integer;
            }
            if (newFrequency > 1) {
                unique = false;
            }
            frequencies.put(integer, newFrequency);

            if (100 <= integer && integer <= 999) {
                integersBetween100And999Count++;
            }
        }

        if (task == 1) {
            outputWriter.printLine(sumTo7777(frequencies) ? "Yes" : "No");
        } else if (task == 2) {
            outputWriter.printLine(unique ? "Unique" : "Contains duplicate");
        } else if (task == 3) {
            outputWriter.printLine(majorityElement);
        } else {
            Arrays.sort(integers);
            if (task == 4) {
                printMedian(integers, outputWriter);
            } else {
                printIntegersBetween100And999(integers, integersBetween100And999Count, outputWriter);
            }
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static boolean sumTo7777(Map<Integer, Integer> frequencies) {
        for (int integer1 : frequencies.keySet()) {
            int integer2 = 7777 - integer1;
            if (frequencies.containsKey(integer2)) {
                return true;
            }
        }
        return false;
    }

    private static void printMedian(int[] integers, OutputWriter outputWriter) {
        int middleIndex = integers.length / 2;
        if (integers.length % 2 == 1) {
            outputWriter.printLine(integers[middleIndex]);
        } else {
            int integer1 = integers[middleIndex- 1];
            int integer2 = integers[middleIndex];
            outputWriter.printLine(integer1 + " " + integer2);
        }
    }

    private static void printIntegersBetween100And999(int[] integers, int integersBetween100And999Count,
                                                      OutputWriter outputWriter) {
        int printed = 0;

        for (int integer : integers) {
            if (100 <= integer && integer <= 999) {
                outputWriter.print(integer);
                printed++;

                if (printed != integersBetween100And999Count) {
                    outputWriter.print(" ");
                } else {
                    outputWriter.printLine();
                    break;
                }
            }
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
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
