package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/12/24.
 */
// Based on https://github.com/morris821028/UVa/blob/master/volume110/11038%20-%20How%20Many%20O's.cpp
public class HowManyOs {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long lowerBound = FastReader.nextLong();
        long higherBound = FastReader.nextLong();

        while (lowerBound >= 0) {
            long zeroes = countZeroesFrom1ToN(higherBound) - countZeroesFrom1ToN(lowerBound - 1);
            outputWriter.printLine(zeroes);

            lowerBound = FastReader.nextLong();
            higherBound = FastReader.nextLong();
        }
        outputWriter.flush();
    }

    private static long countZeroesFrom1ToN(long number) {
        if (number < 0) {
            return 0;
        }
        long count = 1;
        long power10 = 1;
        long nTail = 0;

        while (number > 0) {
            if (number % 10 != 0) {
                count += number / 10 * power10;
            } else {
                count += (number / 10 - 1) * power10 + (nTail + 1);
            }
            nTail += number % 10 * power10;
            number /= 10;
            power10 *= 10;
        }
        return count;
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
