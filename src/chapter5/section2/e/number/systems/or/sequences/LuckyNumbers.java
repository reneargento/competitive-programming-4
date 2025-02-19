package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/02/25.
 */
public class LuckyNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int number = FastReader.nextInt();
            List<Long> luckyNumbers = computeLuckyNumbers(number);

            outputWriter.print("Case " + t + ":");
            for (int i = luckyNumbers.size() - 1; i >= 0; i--) {
                outputWriter.print(" " + luckyNumbers.get(i));
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static List<Long> computeLuckyNumbers(int number) {
        List<Long> luckyNumbers = new ArrayList<>();
        int numberSqrt = (int) Math.ceil(Math.sqrt(number));

        for (long i = 1; i <= numberSqrt; i++) {
            long square = i * i;
            if (square >= number) {
                break;
            }
            long luckyCandidate = number - square;

            double result = luckyCandidate / Math.sqrt(number - luckyCandidate);
            if (result == (int) result) {
                luckyNumbers.add(luckyCandidate);
            }
        }
        return luckyNumbers;
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
