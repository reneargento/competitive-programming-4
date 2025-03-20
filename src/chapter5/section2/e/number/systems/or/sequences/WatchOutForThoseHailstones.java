package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/03/25.
 */
public class WatchOutForThoseHailstones {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long number = FastReader.nextLong();
        BigInteger sum = computeSequenceSum(number);
        outputWriter.printLine(sum.toString());

        outputWriter.flush();
    }

    private static BigInteger computeSequenceSum(long number) {
        List<Long> sequence = new ArrayList<>();
        computeSequence(sequence, number);

        BigInteger sum = BigInteger.ZERO;
        for (Long value : sequence) {
            sum = sum.add(BigInteger.valueOf(value));
        }
        return sum;
    }

    private static void computeSequence(List<Long> sequence, long number) {
        sequence.add(number);
        if (number == 1) {
            return;
        }

        if (number % 2 == 0) {
            computeSequence(sequence, number / 2);
        } else {
            computeSequence(sequence, number * 3 + 1);
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
