package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 15/02/25.
 */
public class CollatzConjecture {

    private static class Result {
        int stepsValue1;
        int stepsValue2;
        long meetingValue;

        public Result(int stepsValue1, int stepsValue2, long meetingValue) {
            this.stepsValue1 = stepsValue1;
            this.stepsValue2 = stepsValue2;
            this.meetingValue = meetingValue;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int value1 = FastReader.nextInt();
        int value2 = FastReader.nextInt();

        while (value1 != 0 || value2 != 0) {
            Result result = computeMeetingValue(value1, value2);
            outputWriter.printLine(value1 + " needs " + result.stepsValue1 + " steps, " + value2 +
                    " needs " + result.stepsValue2 + " steps, they meet at " + result.meetingValue);

            value1 = FastReader.nextInt();
            value2 = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeMeetingValue(int value1, int value2) {
        Deque<Long> pathValue1 = computePath(value1);
        Deque<Long> pathValue2 = computePath(value2);
        long meetingValue = 1;

        while (!pathValue1.isEmpty() &&
                !pathValue2.isEmpty() &&
                pathValue1.peek().equals(pathValue2.peek())) {
            meetingValue = pathValue1.peek();
            pathValue1.pop();
            pathValue2.pop();
        }
        return new Result(pathValue1.size(), pathValue2.size(), meetingValue);
    }

    private static Deque<Long> computePath(long value) {
        Deque<Long> path = new ArrayDeque<>();
        path.push(value);

        while (value > 1) {
            if (value % 2 == 0) {
                value /= 2;
            } else {
                value = 3 * value + 1;
            }
            path.push(value);
        }
        return path;
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
