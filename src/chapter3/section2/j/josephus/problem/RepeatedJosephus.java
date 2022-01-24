package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/01/22.
 */
public class RepeatedJosephus {

    private static class Solution {
        int repetitions;
        int lastSurvivorPosition;

        public Solution(int repetitions, int lastSurvivorPosition) {
            this.repetitions = repetitions;
            this.lastSurvivorPosition = lastSurvivorPosition;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int people = FastReader.nextInt();
            Solution solution = computeRepetitions(people);
            outputWriter.printLine(String.format("Case %d: %d %d", t, solution.repetitions,
                    solution.lastSurvivorPosition));
        }
        outputWriter.flush();
    }

    private static Solution computeRepetitions(int people) {
        int repetitions = 0;
        int survivor = josephusSkip2(people);

        while (survivor != people) {
            repetitions++;
            people = survivor;
            survivor = josephusSkip2(people);
        }
        return new Solution(repetitions, survivor);
    }

    private static int josephusSkip2(int people) {
        String bits = toBinary(people);
        bits = bits.substring(1) + bits.charAt(0);
        return Integer.parseInt(bits, 2);
    }

    private static String toBinary(int number) {
        StringBuilder bits = new StringBuilder();
        while (number > 0) {
            int bit = number % 2;
            bits.append(bit);
            number /= 2;
        }
        return bits.reverse().toString();
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
