package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/09/25.
 */
public class ProductOfDigits {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            long q = getQ(number);
            outputWriter.printLine(q);
        }
        outputWriter.flush();
    }

    private static long getQ(int number) {
        if (number == 0) {
            return 0;
        }
        if (number == 1) {
            return 1;
        }
        StringBuilder qBuilder = new StringBuilder();

        int frequency2 = 0;
        int frequency3 = 0;
        boolean addedMultiples = false;

        for (long i = 2; i * i <= number; i++) {
            int frequency = 0;

            while (number % i == 0) {
                if (i > 7) {
                    return -1;
                }
                frequency++;
                number /= i;
            }

            if (frequency > 0) {
                if (i == 2) {
                    frequency2 = frequency;

                    if (number % 3 != 0) {
                        add2And3Multiples(qBuilder, frequency2, frequency3);
                        addedMultiples = true;
                    }
                } else if (i == 3) {
                    frequency3 = frequency;
                    add2And3Multiples(qBuilder, frequency2, frequency3);
                    addedMultiples = true;
                } else {
                    addDigits(qBuilder, i, frequency);
                }
            }
        }

        if (number > 1) {
            if (number > 7) {
                return -1;
            }

            if (number == 2
                    || number == 3
                    && !addedMultiples) {
                if (number == 2) {
                    frequency2++;
                } else {
                    frequency3++;
                }
                add2And3Multiples(qBuilder, frequency2, frequency3);
            } else {
                qBuilder.append(number);
            }
        }

        char[] characters = qBuilder.toString().toCharArray();
        Arrays.sort(characters);
        String digitsString = new String(characters);
        return Long.parseLong(digitsString);
    }

    private static void add2And3Multiples(StringBuilder qBuilder, int frequency2, int frequency3) {
        int group9 = frequency3 / 2;
        boolean has3 = frequency3 % 2 == 1;

        int group8 = frequency2 / 3;
        frequency2 %= 3;

        int group6 = 0;
        if (frequency2 > 0 && has3) {
            group6 = 1;
            frequency2--;
            has3 = false;
        }

        int group3 = has3 ? 1 : 0;

        int group4 = frequency2 / 2;
        frequency2 %= 2;
        int group2 = frequency2;

        addDigits(qBuilder, 2, group2);
        addDigits(qBuilder, 3, group3);
        addDigits(qBuilder, 4, group4);
        addDigits(qBuilder, 6, group6);
        addDigits(qBuilder, 8, group8);
        addDigits(qBuilder, 9, group9);
    }

    private static void addDigits(StringBuilder qBuilder, long digit, int frequency) {
        for (int i = 0; i < frequency; i++) {
            qBuilder.append(digit);
        }
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
