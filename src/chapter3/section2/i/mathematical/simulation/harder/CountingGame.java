package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/01/22.
 */
public class CountingGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int persons = FastReader.nextInt();
        int targetPerson = FastReader.nextInt();
        int clapTimes = FastReader.nextInt();

        while (persons != 0 || targetPerson != 0 || clapTimes != 0) {
            int numberCounted = getNumberCounted(persons, targetPerson, clapTimes);
            outputWriter.printLine(numberCounted);

            persons = FastReader.nextInt();
            targetPerson = FastReader.nextInt();
            clapTimes = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int getNumberCounted(int persons, int targetPerson, int clapTimes) {
        int number = 1;
        int person = 1;
        int targetPersonClaps = 0;
        boolean increment = true;

        while (true) {
            if (number % 7 == 0 || hasDigit7(number)) {
                if (person == targetPerson) {
                    targetPersonClaps++;
                }
                if (targetPersonClaps == clapTimes) {
                    return number;
                }
            }

            number++;
            if (person == persons) {
                increment = false;
            }
            if (person == 1) {
                increment = true;
            }

            if (increment) {
                person++;
            } else {
                person--;
            }
        }
    }

    private static boolean hasDigit7(int number) {
        while (number > 0) {
            int digit = number % 10;
            if (digit == 7) {
                return true;
            }
            number /= 10;
        }
        return false;
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
