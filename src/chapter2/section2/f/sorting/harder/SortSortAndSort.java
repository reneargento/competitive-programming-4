package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/02/21.
 */
public class SortSortAndSort {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numbers = FastReader.nextInt();
        int mod = FastReader.nextInt();

        while (numbers != 0 || mod != 0) {
            Integer[] values = new Integer[numbers];
            for (int i = 0; i < values.length; i++) {
                values[i] = FastReader.nextInt();
            }

            Arrays.sort(values, getModComparator(mod));
            outputWriter.printLine(numbers + " " + mod);

            for (int value : values) {
                outputWriter.printLine(value);
            }

            numbers = FastReader.nextInt();
            mod = FastReader.nextInt();
        }
        outputWriter.printLine("0 0");
        outputWriter.flush();
        outputWriter.close();
    }

    private static Comparator<Integer> getModComparator(int mod) {
        return new Comparator<Integer>() {
            @Override
            public int compare(Integer number1, Integer number2) {
                if (number1.equals(number2)) {
                    return 0;
                }

                int mod1 = number1 % mod;
                int mod2 = number2 % mod;

                if (mod1 != mod2) {
                    if (mod1 < mod2) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                if (number1 % 2 != 0 && number2 % 2 == 0) {
                    return -1;
                }
                if (number1 % 2 == 0 && number2 % 2 != 0) {
                    return 1;
                }

                if (number1 % 2 != 0 && number2 % 2 != 0) {
                    if (number1 > number2) {
                        return -1;
                    } else {
                        return 1;
                    }
                }

                if (number1 > number2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
